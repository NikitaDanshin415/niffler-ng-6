package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.browser.WebTest;
import guru.qa.niffler.jupiter.usersQueue.UserType;
import guru.qa.niffler.jupiter.usersQueue.UsersQueueExtension;
import guru.qa.niffler.jupiter.usersQueue.UsersQueueExtension.StaticUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@WebTest
@ExtendWith(UsersQueueExtension.class)
public class ProfileTest {


    @Test
    void testWithEmptyUser(
            @UserType(empty = true) StaticUser user1,
            @UserType(empty = false) StaticUser user2

    ) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user1);
        System.out.println(user2);
    }


    @Test
    void testWithEmptyUser1(@UserType(empty = false) StaticUser user) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user);
    }
}
