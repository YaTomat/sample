package ais.sample.com.purchase.data;

/**
 * Created by YaTomat on 29.08.2017.
 */

public class DiscountApplyRequest {

    public String amountBonuses;
    public int saleId;

    public DiscountApplyRequest(String amountBonuses, int saleId) {
        this.amountBonuses = amountBonuses;
        this.saleId = saleId;
    }
}
