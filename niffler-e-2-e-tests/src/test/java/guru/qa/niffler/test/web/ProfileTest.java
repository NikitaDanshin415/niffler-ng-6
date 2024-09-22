package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.browser.WebTest;
import guru.qa.niffler.jupiter.usersQueue.StaticUser;
import guru.qa.niffler.jupiter.usersQueue.UserType;
import guru.qa.niffler.jupiter.usersQueue.UsersQueueExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static guru.qa.niffler.jupiter.usersQueue.UserType.Type.*;

@WebTest
@ExtendWith(UsersQueueExtension.class)
public class ProfileTest {

    @Test
    void testWithEmptyUser1234(
            @UserType(value = EMPTY) StaticUser user1,
            @UserType(value = WITH_FRIEND) StaticUser user2,
            @UserType(value = WITH_INCOME_REQUEST) StaticUser user3,
            @UserType(value = WITH_OUTCOME_REQUEST) StaticUser user4

    ) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Пользователь с данными:" + user2);
        System.out.println("Пользователь с данными:" + user3);
        System.out.println("Пользователь без данных:" + user1);
        System.out.println("Пользователь без данных:" + user4);
    }


    @Test
    void testWithEmptyUser12(
            @UserType(value = EMPTY) StaticUser user1,
            @UserType(value = WITH_FRIEND) StaticUser user2
            ) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Пользователь с данными:" + user2);
        System.out.println("Пользователь без данных:" + user1);
    }

    @Test
    void testWithEmptyUser34(
            @UserType(value = WITH_INCOME_REQUEST) StaticUser user3,
            @UserType(value = WITH_OUTCOME_REQUEST) StaticUser user4
    ) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Пользователь без данных:" + user3);
        System.out.println("Пользователь без данных:" + user4);
    }


    @Test
    void testWithEmptyUser23(
            @UserType(value = WITH_FRIEND) StaticUser user2,
            @UserType(value = WITH_INCOME_REQUEST) StaticUser user3
    ) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Пользователь без данных:" + user2);
        System.out.println("Пользователь без данных:" + user3);
    }

    @Test
    void testWithEmptyUser22(
            @UserType(value = WITH_FRIEND) StaticUser user2,
            @UserType(value = WITH_FRIEND) StaticUser user22
    ) throws InterruptedException {
        Thread.sleep(5000);
        System.out.println("Пользователь без данных:" + user2);
        System.out.println("Пользователь без данных:" + user22);
    }


}
