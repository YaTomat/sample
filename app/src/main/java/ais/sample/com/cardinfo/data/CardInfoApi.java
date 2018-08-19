package ais.sample.com.cardinfo.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by YaTomat on 10.09.2017.
 */


public interface CardInfoApi {

    @GET("api/card")
    Observable<CardInfoResponse> getCardInfo(@Query("AccessToken") String accessToken,
                                             @Query("CardNumber") String cardNumber,
                                             @Query("Phone") String phone);
}
