package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.page;

public class TransferPage {
    @FindBy(css = "input[type='text']")
    private SelenideElement transferAmount;
    @FindBy(css = "input[type='tel']")
    private SelenideElement fieldCardFrom;
    @FindBy(css = "button[data-test-id=action-transfer]")
    private SelenideElement transferButton;

    public DashboardPage transfer (String amount, String id) {
        transferAmount.setValue(amount);
        fieldCardFrom.setValue(id);
        transferButton.click();
        return page(DashboardPage.class);

    }




}
