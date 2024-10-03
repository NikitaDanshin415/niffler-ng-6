package guru.qa.niffler.jupiter.meta;

import guru.qa.niffler.jupiter.browser.BrowserExtension;
import guru.qa.niffler.jupiter.usersQueue.UsersQueueExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({
        BrowserExtension.class,
        UsersQueueExtension.class
})
public @interface WebTest {
}
