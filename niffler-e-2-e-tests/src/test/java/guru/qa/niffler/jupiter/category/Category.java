package guru.qa.niffler.jupiter.category;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@ExtendWith(CategoryExtension.class)
public @interface Category {
    String title() default "";

    String username();

    boolean archived();
}
