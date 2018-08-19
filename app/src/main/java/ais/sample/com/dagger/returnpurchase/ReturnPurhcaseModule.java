package ais.sample.com.dagger.returnpurchase;

import java.util.Map;

import javax.inject.Named;

import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.returnpurchase.data.ReturnPurchaseApi;
import ais.sample.com.returnpurchase.domain.FindPurchaseUseCase;
import ais.sample.com.returnpurchase.domain.ReturnPurchaseUseCase;
import ais.sample.com.returnpurchase.presentation.FindPurchasePresenter;
import ais.sample.com.returnpurchase.presentation.ReturnPurchasePresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 12.09.2017.
 */
@Module
public class ReturnPurhcaseModule {

    @Provides
    public ReturnPurchaseApi provideReturnPurchaseApi(Retrofit retrofit) {
        return retrofit.create(ReturnPurchaseApi.class);
    }

    @Provides
    public FindPurchaseUseCase provideFindPurchaseUseCase(ReturnPurchaseApi returnPurchaseApi,
                                                          @Named("ui") Scheduler ui, @Named("work") Scheduler work,
                                                          AccessController accessController) {
        return new FindPurchaseUseCase(ui, work, returnPurchaseApi, accessController);
    }

    @Provides
    public ReturnPurchaseUseCase provideReturnPurchaseUseCase(ReturnPurchaseApi returnPurchaseApi,
                                                              @Named("ui") Scheduler ui, @Named("work") Scheduler work,
                                                              AccessController accessController) {
        return new ReturnPurchaseUseCase(ui, work, returnPurchaseApi, accessController);
    }

    @Provides
    public FindPurchasePresenter providePurchasePresenter(FindPurchaseUseCase findPurchaseUseCase,
                                                          Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        BaseStatelessPresenter baseStatelessPresenter = statelessPresenterMap.get(FindPurchasePresenter.class.getSimpleName());
        if (baseStatelessPresenter == null) {
            return new FindPurchasePresenter(findPurchaseUseCase);
        } else {
            return (FindPurchasePresenter) baseStatelessPresenter;
        }
    }

    @Provides
    public ReturnPurchasePresenter provideReturnPurchasePresenter(ReturnPurchaseUseCase returnPurchaseUseCase,
                                                                  Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        BaseStatelessPresenter baseStatelessPresenter = statelessPresenterMap.get(ReturnPurchasePresenter.class.getSimpleName());
        if (baseStatelessPresenter == null) {
            return new ReturnPurchasePresenter(returnPurchaseUseCase);
        } else {
            return (ReturnPurchasePresenter) baseStatelessPresenter;
        }
    }
}
