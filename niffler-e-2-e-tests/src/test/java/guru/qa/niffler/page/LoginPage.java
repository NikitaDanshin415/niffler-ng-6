package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
  private final SelenideElement usernameInput = $x("//input[@name='username']");
  private final SelenideElement passwordInput = $x("//input[@name='password']");
  private final SelenideElement submitButton = $x("//button[@type='submit']");
  private final SelenideElement createNewAccountBtn = $x("//a[@class='form__register']");
  private final SelenideElement error = $x("//p[@class='form__error']");

  public LoginPage login(String username, String password) {
    usernameInput.setValue(username);
    passwordInput.setValue(password);
    submitButton.click();

    return this;
  }

  public LoginPage errorShouldHaveText(String errorText) {
    error.shouldHave(Condition.text(errorText));
    return this;
  }

  public LoginPage createNewAccountClick(){
    createNewAccountBtn.click();
    return this;
  }
}
