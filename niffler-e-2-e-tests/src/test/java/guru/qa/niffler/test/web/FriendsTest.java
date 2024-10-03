package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.meta.WebTest;
import guru.qa.niffler.jupiter.usersQueue.StaticUser;
import guru.qa.niffler.jupiter.usersQueue.UserType;
import guru.qa.niffler.page.AllPeoplePage;
import guru.qa.niffler.page.FriendsPage;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static guru.qa.niffler.jupiter.usersQueue.UserType.Type.*;

@WebTest
public class FriendsTest {

    @Test
    void friendShouldBePresentInFriendsTable(@UserType(WITH_FRIEND) StaticUser user) {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(user.userName, user.password);

        new MainPage()
                .openFriendsList();

        new FriendsPage()
                .friendTableShouldHaveRow(user.friend);
    }

    @Test
    void friendTableShouldBeEmptyForNewUser(@UserType(EMPTY) StaticUser user) {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(user.userName, user.password);

        new MainPage()
                .openFriendsList();

        new FriendsPage()
                .friendTableShouldBeEmpty();
    }

    @Test
    void incomeInvitationBePresentInFriendsTable(@UserType(WITH_INCOME_REQUEST) StaticUser user) {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(user.userName, user.password);

        new MainPage()
                .openFriendsList();

        new FriendsPage()
                .requestFriendTableShouldHaveRow(user.income);
    }

    @Test
    void outcomeInvitationBePresentInAllPeoplesTable(@UserType(WITH_OUTCOME_REQUEST) StaticUser user) {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(user.userName, user.password);

        new MainPage()
                .openAllPeopleList();

        new AllPeoplePage()
                .shouldHaveOutcomeRequest(user.outcome);
    }
}