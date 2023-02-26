package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

  private ElementsCollection cards = $$(".list__item div");
  private final String balanceBeginning = "баланс: ";
  private final String balanceEnd = " р.";

  public DashboardPage() {
  }

  public int getBalanceCard(DataHelper.CardInfo cardInfo){
    var text = cards.findBy(Condition.text(cardInfo.getCardNumber().substring(15))).getText();
    return extractBalance(text);
  }
  private int extractBalance(String text) {
    var start = text.indexOf(balanceBeginning);
    var finish = text.indexOf(balanceEnd);
    var value = text.substring(start + balanceBeginning.length(), finish);
    return Integer.parseInt(value);
  }
  public TransferPage selectCardToTransfer(DataHelper.CardInfo cardInfo) {
    cards.findBy(attribute("data-test-id", cardInfo.getTestId())).$("button").click();
    return new TransferPage();
  }

}
