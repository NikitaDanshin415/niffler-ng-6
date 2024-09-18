package guru.qa.niffler.page;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import jaxb.userdata.User;

import static com.codeborne.selenide.Selenide.*;

public class UserProfilePage {
    private final SelenideElement usernameInput = $x("//input[@id='username']");
    private final SelenideElement nameInput = $x("//input[@id='name']");
    private final SelenideElement categoryInput = $x("//input[@id='category']");
    private final SelenideElement saveChangesBtn = $x(".//button[text()='Save changes']");
    private final ElementsCollection categories = $$x(".//div[contains(@class,'MuiChip-root')]");

    public UserProfilePage shouldContainCategory(String categoryName){
        categories
                .shouldHave(CollectionCondition.itemWithText(categoryName));

        return this;
    }

}
