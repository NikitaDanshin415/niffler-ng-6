package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Text;

import static com.codeborne.selenide.Selenide.$;

public class RegisterPage {
    private final SelenideElement usernameInput = $("input[name=username]");
    private final SelenideElement passwordInput = $("input[name=password]");
    private final SelenideElement passwordSubmitInput = $("input[id=passwordSubmit]");
    private final SelenideElement submitButton = $("button[type='submit']");
    private final SelenideElement error = $("span[class=form__error]");

    public RegisterPage setUsername(String username){
        usernameInput.setValue(username);
        return this;
    }

    public RegisterPage setPassword(String password){
        passwordInput.setValue(password);
        return this;
    }

    public RegisterPage setPasswordSubmit(String passwordSubmit){
        passwordSubmitInput.setValue(passwordSubmit);
        return this;
    }

    public RegisterPage submitRegistration(){
        submitButton.click();
        return this;
    }



    public SelenideElement errorShouldHaveText(String errorText) {
        return error.shouldHave(Condition.text(errorText));
    }
}
