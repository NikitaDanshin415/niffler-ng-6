package guru.qa.niffler.jupiter.user;

import guru.qa.niffler.jupiter.category.Category;
import guru.qa.niffler.jupiter.category.CategoryExtension;
import guru.qa.niffler.jupiter.spending.SpendingExtension;
import guru.qa.niffler.jupiter.spending.Spending;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith({CategoryExtension.class, SpendingExtension.class})
public @interface User {
    String user();
    Category[] categories() default {};
    Spending[] spendings() default {};
}
