package ais.sample.com.returnpurchase.data;

/**
 * Created by YaTomat on 12.09.2017.
 */

public class FindPurchaseRequest {
    public String cardNumber;
    public String phoneNumber;
    public String amount;
    public String date;

    public FindPurchaseRequest(String cardNumber, String phoneNumber, String amount, String date) {
        this.cardNumber = cardNumber == null ? "null" : cardNumber;
        this.phoneNumber = phoneNumber == null ? "null" : phoneNumber;
        this.amount = amount == null ? "null" : amount;
        this.date = date == null ? "null" : date;
    }
}
