package guru.qa.niffler.test.web;

import com.codeborne.selenide.Selenide;
import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.category.Category;
import guru.qa.niffler.jupiter.meta.WebTest;
import guru.qa.niffler.jupiter.spending.Spending;
import guru.qa.niffler.jupiter.user.User;
import guru.qa.niffler.model.SpendJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@WebTest
public class SpendingWebTest {

    private static final Config CFG = Config.getInstance();


    @User(
            username = "test",
            categories = @Category(
                    title = "Проверка работы User",
                    archived = true
            ),
            spendings = @Spending(
                    category = "123",
                    description = "Проверка работы User",
                    amount = 79990
            )
    )
    @Test
    void categoryDescriptionShouldBeChangedFromTable(SpendJson spend) {
        final String newDescription = "Обучение Niffler Next Generation";
        Selenide.open(CFG.frontUrl());

        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login("test", "123");

        new MainPage()
                .editSpending(spend.description())
                .setNewSpendingDescription(newDescription)
                .save();

        new MainPage().checkThatTableContainsSpending(newDescription);
    }
}
