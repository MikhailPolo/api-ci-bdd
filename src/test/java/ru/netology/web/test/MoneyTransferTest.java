package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

  @BeforeEach
  public void setup() {
    var loginPage = open("http://localhost:9999", LoginPageV1.class);
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    verificationPage.validVerify(verificationCode);
    Configuration.holdBrowserOpen = true;
  }


    @Test
    void shouldTransferMoneyBetweenFirstCards() {
    val amount = 5000;
    var dashBoardPage = new DashboardPage();
    val balanceCardOne = new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    val balanceCardTwo = new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());

    var card = dashBoardPage.chooseCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());
    var dashboard = card.transfer(String.valueOf(amount), DataHelper.getCardNumberOne().getCardNumber());
    $(withText("Ваши карты")).shouldBe(visible);

    val newBalanceCardOne = balanceCardOne - amount;
    val newBalanceCardTwo = balanceCardTwo + amount;

    assertEquals(newBalanceCardOne, new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity()));
    assertEquals(newBalanceCardTwo, new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity()));
      
    }
  @Test
  void shouldTransferMoneyBetweenSecondCards() {
    val amount = 5000;
    var dashBoardPage = new DashboardPage();
    val balanceCardOne = new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    val balanceCardTwo = new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());

    var card = dashBoardPage.chooseCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    var dashboard = card.transfer(String.valueOf(amount), DataHelper.getCardNumberTwo().getCardNumber());
    $(withText("Ваши карты")).shouldBe(visible);

    val newBalanceCardOne = balanceCardOne + amount;
    val newBalanceCardTwo = balanceCardTwo - amount;

    assertEquals(newBalanceCardOne, new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity()));
    assertEquals(newBalanceCardTwo, new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity()));

  }
  @Test
  void shouldTransferMoneyElseSumUnderLimitCardOne() {
    val amount = 30000;
    var dashBoardPage = new DashboardPage();
    val balanceCardOne = new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    val balanceCardTwo = new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());

    var card = dashBoardPage.chooseCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());
    var dashboard = card.transfer(String.valueOf(amount), DataHelper.getCardNumberOne().getCardNumber());
    $(withText("Ваши карты")).shouldBe(visible);

    val newBalanceCardOne = balanceCardOne - amount;
    val newBalanceCardTwo = balanceCardTwo + amount;

    assertEquals(newBalanceCardOne, new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity()));
    assertEquals(newBalanceCardTwo, new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity()));
  }
  @Test
  void shouldTransferMoneyElseSumUnderLimitCardTwo() {
    val amount = 30000;
    var dashBoardPage = new DashboardPage();
    val balanceCardOne = new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    val balanceCardTwo = new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity());

    var card = dashBoardPage.chooseCard(DataHelper.getCardNumberOne().getCardNumberSecurity());
    var dashboard = card.transfer(String.valueOf(amount), DataHelper.getCardNumberTwo().getCardNumber());
    $(withText("Ваши карты")).shouldBe(visible);

    val newBalanceCardOne = balanceCardOne + amount;
    val newBalanceCardTwo = balanceCardTwo - amount;

    assertEquals(newBalanceCardOne, new DashboardPage().getBalanceCard(DataHelper.getCardNumberOne().getCardNumberSecurity()));
    assertEquals(newBalanceCardTwo, new DashboardPage().getBalanceCard(DataHelper.getCardNumberTwo().getCardNumberSecurity()));
  }
}

