package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$x;

public class AllPeoplePage {
    SelenideElement allPeopleTable = $x("//tbody[@id='all']");

    public AllPeoplePage shouldHaveOutcomeRequest(String username) {
        allPeopleTable
                .$$x(".//tr")
                .find(text(username))
                .shouldHave(text("Waiting..."));

        return this;
    }
}
