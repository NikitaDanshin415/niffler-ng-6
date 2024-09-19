package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class RegisterPage {
    private final SelenideElement usernameInput = $x("//input[@name='username']");
    private final SelenideElement passwordInput = $x("//input[@name='password']");
    private final SelenideElement passwordSubmitInput = $x("//input[@id='passwordSubmit']");
    private final SelenideElement submitButton = $x("//button[@type='submit']");
    private final SelenideElement error = $x("//span[@class='form__error']");
    private final SelenideElement signIn = $x("//a[@class='form_sign-in']");


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

    public RegisterPage clickSignInBtn(){
        signIn.click();
        return this;
    }

    public SelenideElement errorShouldHaveText(String errorText) {
        return error.shouldHave(Condition.text(errorText));
    }
}
