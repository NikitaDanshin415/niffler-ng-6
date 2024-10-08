package guru.qa.niffler.jupiter.usersQueue;


import io.qameta.allure.Allure;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

import static guru.qa.niffler.jupiter.usersQueue.UserType.Type.EMPTY;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    private static final Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedDeque<>();
    private static final Queue<StaticUser> USER_WITH_FRIEND = new ConcurrentLinkedDeque<>();
    private static final Queue<StaticUser> USER_WITH_INCOME_REQUEST = new ConcurrentLinkedDeque<>();
    private static final Queue<StaticUser> USER_WITH_OUTCOME_REQUEST = new ConcurrentLinkedDeque<>();


    static {
        USER_WITH_FRIEND.add(new StaticUser("test", "123", "test3", null, null));
        EMPTY_USERS.add(new StaticUser("test2", "123", null, null, null));
        USER_WITH_INCOME_REQUEST.add(new StaticUser("test3", "123", null, "test4", null));
        USER_WITH_OUTCOME_REQUEST.add(new StaticUser("test4", "123", null, null, "test3"));
    }


    @Override
    public void beforeEach(ExtensionContext context) {
        Arrays.stream(context.getRequiredTestMethod().getParameters())

                //Получаем только те параметры, которые отмеченны аннотацией
                .filter(p -> AnnotationSupport.isAnnotated(p, UserType.class) && p.getType().isAssignableFrom(StaticUser.class))
                //Каждый параметр приводим к типу UserType

                //Достаем нужного пользователя, в соответствии каждому параметру
                .forEach(p -> {
                    UserType ut = p.getAnnotation(UserType.class);
                    Optional<StaticUser> user = Optional.empty();
                    StopWatch sw = StopWatch.createStarted();

                    // Достаем его из нужной очереди
                    while (user.isEmpty() && sw.getTime(TimeUnit.SECONDS) < 30) {
                        Queue<StaticUser> queue = getQueue(ut.value());
                        user = Optional.ofNullable(queue.poll());
                    }

                    //Добавляем время начала теста, чтобы в отчете все отбражалось корректно
                    Allure.getLifecycle().updateTestCase(testCase -> {
                        testCase.setStart(new Date().getTime());
                    });

                    // Когда передвал 3 параметра(true, false, false) значения перезаписывались
                    // Чтобы значения в мапе не перезаписывались, нужно уникальное значение.
                    // Например индекс параметра в методе
                    int parameterIndex = Arrays.asList(context.getRequiredTestMethod().getParameters()).indexOf(p);

                    // Добавляем пользователя в мапу пользователей для теста или создаем эту мапу при первом вхождении цикл
                    user.ifPresentOrElse(
                            u -> {
                                ((Map<Integer, UserQueueRecord>) context.getStore(NAMESPACE).getOrComputeIfAbsent(
                                        context.getUniqueId(),
                                        key -> new HashMap<>()
                                )).put(parameterIndex, new UserQueueRecord(u, ut));
                            },
                            // если какого то пользователя не нашли за 30 сек, то кидаем исключение
                            () -> new IllegalStateException("Can't find user after 30 sec")
                    );
                });
    }

    @Override
    public void afterEach(ExtensionContext context) {
        Map<Integer, UserQueueRecord> users = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);

        if (!Objects.isNull(users)) {
            for (Map.Entry<Integer, UserQueueRecord> user : users.entrySet()) {
                Queue<StaticUser> queue = getQueue(user.getValue().userType.value());
                queue.add(user.getValue().staticUser);
            }
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(StaticUser.class)
                && AnnotationSupport.isAnnotated(parameterContext.getParameter(), UserType.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        Map<Integer, UserQueueRecord> usersMap = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        return usersMap.get(parameterContext.getIndex()).staticUser;
    }

    private Queue<StaticUser> getQueue(UserType.Type type) {
        return switch (type) {
            case EMPTY -> EMPTY_USERS;
            case WITH_FRIEND -> USER_WITH_FRIEND;
            case WITH_INCOME_REQUEST -> USER_WITH_INCOME_REQUEST;
            case WITH_OUTCOME_REQUEST -> USER_WITH_OUTCOME_REQUEST;
        };
    }
}
