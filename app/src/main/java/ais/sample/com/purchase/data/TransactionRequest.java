package ais.sample.com.purchase.data;

/**
 * Created by YaTomat on 01.07.2017.
 */

public class TransactionRequest {
    public final String phoneNumber;
    public String cardNumber;
    public String summ;
    public String actionId;

    public TransactionRequest(String cardNumber, String summ, String actionId, String phoneNumber) {
        this.cardNumber = cardNumber;
        this.summ = summ;
        this.actionId = actionId;
        this.phoneNumber = phoneNumber;
    }
}


