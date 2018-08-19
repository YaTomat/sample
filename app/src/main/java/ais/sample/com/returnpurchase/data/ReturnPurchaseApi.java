package ais.sample.com.returnpurchase.data;

import ais.sample.com.statistic.data.StatisticItem;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YaTomat on 12.09.2017.
 */

public interface ReturnPurchaseApi {

    @GET("api/sale")
    Observable<StatisticItem[]> findPurchases(@Query("AccessToken") String accessToken,
                                              @Query("CardNumber") String cardNumber,
                                              @Query("Phone") String phone,
                                              @Query("SaleDate") String saleDate,
                                              @Query("SaleAmount") String saleAmount);

    @GET("api/sale/return")
    Observable<ResponseBody> returnPurchase(@Query("AccessToken") String accessToken,
                                            @Query("SaleId") int saleId);
}
