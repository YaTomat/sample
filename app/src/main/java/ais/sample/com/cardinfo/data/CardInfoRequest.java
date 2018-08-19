package ais.sample.com.cardinfo.data;

/**
 * Created by YaTomat on 10.09.2017.
 */

public class CardInfoRequest {

    public String cardNumber;
    public String phoneNumber;

    public CardInfoRequest(String cardNumber, String phoneNumber) {
        this.cardNumber = cardNumber;
        this.phoneNumber = phoneNumber;
    }
}
