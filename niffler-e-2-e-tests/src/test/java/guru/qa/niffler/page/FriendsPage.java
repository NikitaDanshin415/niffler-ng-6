package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class FriendsPage {
    SelenideElement friendsTable = $x("//tbody[@id='friends']");
    SelenideElement friendsRequestTable = $x("//tbody[@id='requests']");

    public FriendsPage friendTableShouldHaveRow(String username) {
        friendsTable
                .$$x(".//td")
                .find(text(username))
                .shouldBe(visible);

        return this;
    }

    public FriendsPage friendTableShouldBeEmpty() {
        friendsTable
                .$$x(".//td")
                .shouldBe(empty);

        return this;
    }

    public FriendsPage requestFriendTableShouldHaveRow(String username) {
        friendsRequestTable
                .$$x(".//td")
                .shouldHave(itemWithText(username));

        return this;
    }

}
