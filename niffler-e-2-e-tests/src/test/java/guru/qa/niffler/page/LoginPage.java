package guru.qa.niffler.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
  private final SelenideElement usernameInput = $("input[name='username']");
  private final SelenideElement passwordInput = $("input[name='password']");
  private final SelenideElement submitButton = $("button[type='submit']");
  private final SelenideElement createNewAccountBtn = $("a[href='/register']");
  private final SelenideElement error = $("*[class='form__error']");

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
