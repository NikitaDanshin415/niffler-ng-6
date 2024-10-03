package guru.qa.niffler.jupiter.category;

import com.github.javafaker.Faker;
import guru.qa.niffler.api.spend.SpendApiClient;
import guru.qa.niffler.jupiter.user.User;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.extension.*;
import org.junit.platform.commons.support.AnnotationSupport;

import java.util.Objects;

public class CategoryExtension implements BeforeEachCallback, ParameterResolver, AfterTestExecutionCallback {
    public static final ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace.create(CategoryExtension.class);
    private final SpendApiClient spendApiClient = new SpendApiClient();


    @Override
    public void beforeEach(ExtensionContext context) {
        AnnotationSupport.findAnnotation(context.getRequiredTestMethod(), User.class)
                .ifPresent(anno -> {
                    if (anno.categories().length != 0) {
                        Category category = anno.categories()[0];
                        String title = category.title().isEmpty() ? RandomDataUtils.randomCategoryName() : category.title();

                        CategoryJson createdCategory = spendApiClient.addCategory(new CategoryJson(
                                null,
                                title,
                                anno.username(),
                                false
                        ));

                        if (category.archived()) {
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
                    }
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

        if (!Objects.isNull(category)) {
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
}
