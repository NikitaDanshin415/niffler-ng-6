package guru.qa.niffler.test.web;


import guru.qa.niffler.config.Config;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.RegisterPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static com.codeborne.selenide.Selenide.open;

public class RegistrationTests {
    private String username;
    private String password;

    @BeforeEach
    void prepareData() {
        username = "test" + new Random(100).nextLong();
        password = "12345";
    }

    @Test
    void shouldRegisterNewUser() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .createNewAccountClick();

        new RegisterPage()
                .setUsername(username)
                .setPassword(password)
                .setPasswordSubmit(password)
                .submitRegistration();
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
