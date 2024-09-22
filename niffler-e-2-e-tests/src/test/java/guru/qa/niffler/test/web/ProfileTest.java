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
            @UserType(value = true) StaticUser user1,
            @UserType(value = false) StaticUser user2,
            @UserType(value = false) StaticUser user3

    ) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Пользователь с данными:" + user2);
        System.out.println("Пользователь с данными:" + user3);
        System.out.println("Пользователь без данных:" + user1);
    }


    @Test
    void testWithEmptyUser1(@UserType(value = false) StaticUser user) throws InterruptedException {
        Thread.sleep(1000);
        System.out.println(user);
    }
}
