package guru.qa.niffler.test.web;

import guru.qa.niffler.jupiter.browser.WebTest;
import guru.qa.niffler.jupiter.usersQueue.StaticUser;
import guru.qa.niffler.jupiter.usersQueue.UserType;
import guru.qa.niffler.jupiter.usersQueue.UsersQueueExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.jupiter.usersQueue.UserType.Type.*;

@WebTest
@ExtendWith(UsersQueueExtension.class)
public class FriendsTest {

    @Test
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {

    }

    @Test
    void friendTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {

    }

    @Test
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {

    }

    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {

    }

}
