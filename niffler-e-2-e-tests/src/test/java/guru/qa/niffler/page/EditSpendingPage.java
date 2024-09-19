package guru.qa.niffler.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class EditSpendingPage {
  private final SelenideElement descriptionInput = $x("//input[@name='description']");
  private final SelenideElement saveBtn = $x("//button[@id='save']");

  public EditSpendingPage setNewSpendingDescription(String description) {
    descriptionInput.clear();
    descriptionInput.setValue(description);
    return this;
  }

  public void save() {
    saveBtn.click();
  }
}
