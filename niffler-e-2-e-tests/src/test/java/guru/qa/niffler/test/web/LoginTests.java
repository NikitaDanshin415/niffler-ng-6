package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.meta.WebTest;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@WebTest
public class LoginTests {
    private String username;
    private String password;

    @BeforeEach
    void prepareData() {
        username = "test";
        password = "123";
    }

    @Test
    void mainPageShouldBeDisplayedAfterSuccessLogin() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(username, password);

        new MainPage()
                .categoriesShouldBeVisible()
                .statisticShouldBeVisible();
    }

    @Test
    void userShouldStayOnLoginPageAfterLoginWithBadCredentials() {
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login(username, "12354")
                .errorShouldHaveText("Неверные учетные данные пользователя");
    }
}
