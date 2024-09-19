package guru.qa.niffler.jupiter.category;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.SpendApiClient;
import guru.qa.niffler.model.CategoryJson;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();
    private Faker faker = new Faker();

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), Category.class)
                .ifPresent(anno -> {
                    String title = anno.title().equals("") ? faker.funnyName().toString() : anno.title();

                    CategoryJson createdCategory = spendApiClient.addCategory(new CategoryJson(
                            null,
                            title,
                            anno.username(),
                            false
                    ));

                    if (anno.archived()) {
                        createdCategory = spendApiClient.editCategory(new CategoryJson(
                                createdCategory.id(),
                                createdCategory.name(),
                                createdCategory.username(),
                                true
                        ));
                    }

                    context.getStore(NAMESPACE).put(
                            context.getUniqueId(),
                            createdCategory
                    );
                });
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(extensionContext.getUniqueId(), CategoryJson.class);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        CategoryJson category = context.getStore(CategoryExtension.NAMESPACE).get(context.getUniqueId(),
                CategoryJson.class);

        if (!category.archived()) {
            spendApiClient.editCategory(
                    new CategoryJson(
                            category.id(),
                            category.name(),
                            category.username(),
                            true
                    )
            );
        }
    }
}
