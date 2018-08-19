package ais.sample.com.purchase.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YaTomat on 01.07.2017.
 */

public interface TransactionApi {

    @GET("api/sale")
    Observable<TransactionResponse> makePurchase(@Query("AccessToken") String accessToken,
                                                 @Query("CardNumber") String cardNumber,
                                                 @Query("Phone") String phone,
                                                 @Query("Summ") String summ,
                                                 @Query("ActionId") String actionId);

    @GET("api/sale/pay")
    Observable<DiscountApplyResponse> payByBonuses(@Query("AccessToken") String accessToken,
                                                   @Query("SaleId") int saleId,
                                                   @Query("BonusesForDebiting") String bonusesForDebiting);
}
