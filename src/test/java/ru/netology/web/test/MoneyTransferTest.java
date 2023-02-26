package ru.netology.web.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {
  LoginPageV1 loginPage = new LoginPageV1();

  @BeforeEach
  public void setup() {
    var loginPage = open("http://localhost:9999", LoginPageV1.class);
  }


  @Test
  void shouldTransferMoneyBetweenFirstCards() {
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    var firstCardInfo = DataHelper.getFirstCardInfo();
    var secondCardInfo = DataHelper.getSecondCardInfo();
    var firstCardBalance = dashboardPage.getBalanceCard(firstCardInfo);
    var secondCardBalance = dashboardPage.getBalanceCard(secondCardInfo);
    var amount = DataHelper.generateValidAmount(firstCardBalance);
    var expectedBalanceFirstCards = firstCardBalance - amount;
    var expectedBalanceSecondCard = secondCardBalance + amount;
    var transferPage = dashboardPage.selectCardToTransfer(secondCardInfo);
    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), firstCardInfo);
    var actualBalanceFirstCard = dashboardPage.getBalanceCard(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getBalanceCard(secondCardInfo);
    assertEquals(expectedBalanceFirstCards, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }

  @Test
  void shouldTransferMoneyBetweenSecondCards() {
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    var firstCardInfo = DataHelper.getFirstCardInfo();
    var secondCardInfo = DataHelper.getSecondCardInfo();
    var firstCardBalance = dashboardPage.getBalanceCard(firstCardInfo);
    var secondCardBalance = dashboardPage.getBalanceCard(secondCardInfo);
    var amount = DataHelper.generateValidAmount(secondCardBalance);
    var expectedBalanceFirstCards = firstCardBalance + amount;
    var expectedBalanceSecondCard = secondCardBalance - amount;
    var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
    dashboardPage = transferPage.makeValidTransfer(String.valueOf(amount), secondCardInfo);
    var actualBalanceFirstCard = dashboardPage.getBalanceCard(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getBalanceCard(secondCardInfo);
    assertEquals(expectedBalanceFirstCards, actualBalanceFirstCard);
    assertEquals(expectedBalanceSecondCard, actualBalanceSecondCard);
  }
  @Test
  void shouldErrorMessageWhenAmountMoreBalance() {
    var authInfo = DataHelper.getAuthInfo();
    var verificationPage = loginPage.validLogin(authInfo);
    var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
    var dashboardPage = verificationPage.validVerify(verificationCode);
    var firstCardInfo = DataHelper.getFirstCardInfo();
    var secondCardInfo = DataHelper.getSecondCardInfo();
    var firstCardBalance = dashboardPage.getBalanceCard(firstCardInfo);
    var secondCardBalance = dashboardPage.getBalanceCard(secondCardInfo);
    var amount = DataHelper.generateInvalidAmount(secondCardBalance);
    var transferPage = dashboardPage.selectCardToTransfer(firstCardInfo);
    transferPage.makeTransfer(String.valueOf(amount), secondCardInfo);
    transferPage.findErrorMessage("Ошибка! Недостаточно средств");
    var actualBalanceFirstCard = dashboardPage.getBalanceCard(firstCardInfo);
    var actualBalanceSecondCard = dashboardPage.getBalanceCard(secondCardInfo);
    assertEquals(firstCardBalance, actualBalanceFirstCard);
    assertEquals(secondCardBalance, actualBalanceSecondCard);
  }
}




