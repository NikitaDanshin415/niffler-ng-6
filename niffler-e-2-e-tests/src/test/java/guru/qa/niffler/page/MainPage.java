package guru.qa.niffler.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement statistic = $x("//div[@id='legend-container']");
    private final SelenideElement table = $x("//div[@id='spendings']//tbody");
    private final SelenideElement userAvatar = $x("//div[contains(@class,'MuiAvatar-root')]");
    private final SelenideElement profileLink = $x("//a[@href='/profile']");
    private final ElementsCollection tableRows = table.$$x(".//tr");

    public EditSpendingPage editSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).$$x(".//td").get(5).click();
        return new EditSpendingPage();
    }

    public MainPage checkThatTableContainsSpending(String spendingDescription) {
        tableRows.find(text(spendingDescription)).should(visible);
        return this;
    }

    public MainPage categoriesShouldBeVisible() {
        table.shouldBe(visible);
        return this;
    }

    public MainPage statisticShouldBeVisible() {
        statistic.shouldBe(visible);
        return this;
    }

    public MainPage openProfilePage(){
        userAvatar.click();
        profileLink.click();

        return this;
    }
}
