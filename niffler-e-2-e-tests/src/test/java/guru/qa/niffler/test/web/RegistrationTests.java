package guru.qa.niffler.test.web;


import com.github.javafaker.Faker;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.RegisterPage;
import guru.qa.niffler.utils.RandomDataUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@WebTest
public class RegistrationTests {
    private String username;
    private String password;

    @BeforeEach
    void prepareData() {
        username = RandomDataUtils.randomName();
        password = "123";
    }

    @Test
    void shouldRegisterNewUser() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .createNewAccountClick();

        step("Прохождение регистрации", () ->{
            new RegisterPage()
                    .setUsername(username)
                    .setPassword(password)
                    .setPasswordSubmit(password)
                    .submitRegistration()
                    .clickSignInBtn();
        });

        step("Проверка успешной авторизации с новой учетной записью", () ->{
            new LoginPage()
                    .login(username, password);

            new MainPage()
                    .statisticShouldBeVisible();
        });
    }

    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .createNewAccountClick();

        new RegisterPage()
                .setUsername("test")
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration()
                .errorShouldHaveText("Username `test` already exists");
    }

    @Test
    void shouldShowErrorIfPasswordAndConfirmPasswordAreNotEqual() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .createNewAccountClick();

        new RegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit("12354")
                .submitRegistration()
                .errorShouldHaveText("Passwords should be equal");
    }
}
