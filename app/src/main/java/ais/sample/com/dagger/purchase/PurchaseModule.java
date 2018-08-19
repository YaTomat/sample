package ais.sample.com.dagger.purchase;

import android.content.Context;

import java.util.Map;

import javax.inject.Named;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.purchase.data.TransactionApi;
import ais.sample.com.purchase.domain.DiscountApplyUseCase;
import ais.sample.com.purchase.domain.GetStocksUseCase;
import ais.sample.com.purchase.domain.TransactionUseCase;
import ais.sample.com.purchase.presentation.DiscountPurchasePresenter;
import ais.sample.com.purchase.presentation.PurchasePresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 27.08.2017.
 */
@Module
public class PurchaseModule {

    @Provides
    TransactionApi provideTransactionApi(Retrofit retrofit) {
        return retrofit.create(TransactionApi.class);
    }

    @Provides
    GetStocksUseCase provideGetStocksUseCase(@Named("ui") Scheduler ui, @Named("work") Scheduler work, Context context) {
        return new GetStocksUseCase(ui, work, context);
    }

    @Provides
    @FragmentScope
    TransactionUseCase provideTransactionUseCase(@Named("ui") Scheduler ui, @Named("work") Scheduler work, TransactionApi transactionApi,
                                                 AccessController accessController) {
        return new TransactionUseCase(ui, work, transactionApi, accessController);
    }

    @Provides
    @FragmentScope
    PurchasePresenter providePurchasePresenter(GetStocksUseCase getStocksUseCase, TransactionUseCase transactionUseCase, Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        PurchasePresenter purchasePresenter = (PurchasePresenter) statelessPresenterMap.get(PurchasePresenter.class.getSimpleName());
        if (purchasePresenter == null) {
            return new PurchasePresenter(getStocksUseCase, transactionUseCase);
        } else {
            return purchasePresenter;
        }
    }

    @Provides
    @FragmentScope
    DiscountApplyUseCase provideDiscountApplyUseCase(TransactionApi transactionApi, @Named("ui") Scheduler ui, @Named("work") Scheduler work,
                                                     AccessController accessController) {
        return new DiscountApplyUseCase(ui, work, transactionApi, accessController);
    }

    @Provides
    @FragmentScope
    DiscountPurchasePresenter provideDiscountPurchasePresenter(DiscountApplyUseCase discountApplyUseCase, Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        DiscountPurchasePresenter purchasePresenter = (DiscountPurchasePresenter) statelessPresenterMap.get(DiscountPurchasePresenter.class.getSimpleName());
        if (purchasePresenter == null) {
            return new DiscountPurchasePresenter(discountApplyUseCase);
        } else {
            return purchasePresenter;
        }
    }
}
