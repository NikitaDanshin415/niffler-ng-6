package guru.qa.niffler.jupiter.usersQueue;


import io.qameta.allure.Allure;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.*;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

public class UsersQueueExtension implements
        BeforeEachCallback,
        AfterEachCallback,
        ParameterResolver {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(UsersQueueExtension.class);

    public record StaticUser(String userName, String password, boolean empty) {
    }

    private static final Queue<StaticUser> EMPTY_USERS = new ConcurrentLinkedDeque<>();
    private static final Queue<StaticUser> NOT_EMPTY_USERS = new ConcurrentLinkedDeque<>();


    static {
        EMPTY_USERS.add(new StaticUser("test2", "123", true));
        NOT_EMPTY_USERS.add(new StaticUser("test", "123", false));
        NOT_EMPTY_USERS.add(new StaticUser("test3", "123", false));
    }


    @Override
    public void beforeEach(ExtensionContext context) {
        Arrays.stream(context.getRequiredTestMethod().getParameters())

                //Получаем только те параметры, которые отмеченны аннотацией
                .filter(p -> AnnotationSupport.isAnnotated(p, UserType.class))
                //Каждый параметр приводим к типу UserType

                //Достаем нужного пользователя, в соответствии каждому параметру
                .forEach(p -> {
                    UserType ut = p.getAnnotation(UserType.class);
                    Optional<StaticUser> user = Optional.empty();
                    StopWatch sw = StopWatch.createStarted();

                    //Вот тут достаем его из нужной очереди
                    while (user.isEmpty() && sw.getTime(TimeUnit.SECONDS) < 30) {
                        user = ut.value()
                                ? Optional.ofNullable(EMPTY_USERS.poll())
                                : Optional.ofNullable(NOT_EMPTY_USERS.poll());
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
                                ((Map<Integer, StaticUser>) context.getStore(NAMESPACE).getOrComputeIfAbsent(
                                        context.getUniqueId(),
                                        key -> new HashMap<>()
                                )).put(parameterIndex, u);
                            },
                            // если какого то пользователя не нашли за 30 сек, то кидаем исключение
                            () -> new IllegalStateException("Can't find user after 30 sec")
                    );
                });
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        Map<Integer, StaticUser> users = context.getStore(NAMESPACE).get(context.getUniqueId(), Map.class);

        for (Map.Entry<Integer, StaticUser> user : users.entrySet()){
            if (user.getValue().empty) {
                EMPTY_USERS.add(user.getValue());
            } else {
                NOT_EMPTY_USERS.add(user.getValue());
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
        Map<Integer, StaticUser> usersMap = extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), Map.class);
        return usersMap.get(parameterContext.getIndex());
    }
}
