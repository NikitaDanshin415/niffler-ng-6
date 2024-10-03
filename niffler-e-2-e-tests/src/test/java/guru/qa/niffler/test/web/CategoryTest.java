package guru.qa.niffler.test.web;

import guru.qa.niffler.config.Config;
import guru.qa.niffler.jupiter.category.Category;
import guru.qa.niffler.jupiter.meta.WebTest;
import guru.qa.niffler.model.CategoryJson;
import guru.qa.niffler.page.LoginPage;
import guru.qa.niffler.page.MainPage;
import guru.qa.niffler.page.UserProfilePage;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.open;

@WebTest
public class CategoryTest {

    @Test
    @Category(
            username = "test",
            archived = true
    )
    public void archivedCategoryShouldPresentInCategoriesList(CategoryJson categoryJson){
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login("test", "123");

        new MainPage()
                .openProfilePage();

        new UserProfilePage()
                .clickShowArchivedCategory()
                .shouldContainCategory(categoryJson.name());
    }


    @Test
    @Category(
            username = "test",
            archived = false
    )
    public void activeCategoryShouldPresentInCategoriesList(CategoryJson categoryJson){
        open(Config.getInstance().frontUrl(), LoginPage.class)
                .login("test", "123");

        new MainPage()
                .openProfilePage();

        new UserProfilePage()
                .shouldContainCategory(categoryJson.name());
    }
}
