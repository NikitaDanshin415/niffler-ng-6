package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.CollectionCondition.empty;
import static com.codeborne.selenide.CollectionCondition.itemWithText;
import static com.codeborne.selenide.Selenide.$x;

public class FriendsPage {
    SelenideElement friendsTable = $x("//tbody[@id='friends']");
    SelenideElement friendsRequestTable = $x("//tbody[@id='requests']");

    public FriendsPage friendTableShouldHaveRow(String username) {
        friendsTable
                .$$x(".//div[contains(@class,'MuiBox-root')]/p[contains(@class,'MuiTypography-body1')]")
                .shouldHave(itemWithText(username));

        return this;
    }

    public FriendsPage friendTableShouldBeEmpty() {
        friendsTable
                .$$x(".//div[contains(@class,'MuiBox-root')]/p[contains(@class,'MuiTypography-body1')]")
                .shouldBe(empty);

        return this;
    }

    public FriendsPage requestFriendTableShouldHaveRow(String username) {
        friendsRequestTable
                .$$x(".//div[contains(@class,'MuiBox-root')]/p[contains(@class,'MuiTypography-body1')]")
                .shouldHave(itemWithText(username));

        return this;
    }

}
