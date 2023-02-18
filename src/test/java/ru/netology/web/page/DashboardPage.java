package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import lombok.val;

import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.page;

public class DashboardPage {
  private ElementsCollection youCard = $$(".list__item div");
  private final String balanceBeginning = "баланс: ";
  private final String balanceEnd = " р.";

  public DashboardPage() {
  }

  public int getBalanceCard(String id){
    val text = youCard.findBy(Condition.text(id)).text();
    return extractBalance(text);
  }
  private int extractBalance(String text) {
    val start = text.indexOf(balanceBeginning);
    val finish = text.indexOf(balanceEnd);
    val value = text.substring(start + balanceBeginning.length(), finish);
    return Integer.parseInt(value);
  }
  public TransferPage chooseCard(String id) {

    val button = youCard.findBy(Condition.text(id));
    button.$("button[data-test-id='action-deposit']").click();
    return page(TransferPage.class);
  }

}
