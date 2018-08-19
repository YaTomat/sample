package ais.sample.com.dagger.cardinfo;

import java.util.Map;

import javax.inject.Named;

import ais.sample.com.cardinfo.data.CardInfoApi;
import ais.sample.com.cardinfo.domain.GetCardInfoUseCase;
import ais.sample.com.cardinfo.presentation.CardInfoPresenter;
import ais.sample.com.common.AccessController;
import ais.sample.com.common.BaseStatelessPresenter;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import retrofit2.Retrofit;

/**
 * Created by YaTomat on 10.09.2017.
 */
@Module
public class CardInfoModule {

    @Provides
    public CardInfoApi provideCardInfoApi(Retrofit retrofit) {
        return retrofit.create(CardInfoApi.class);
    }

    @Provides
    public GetCardInfoUseCase provideGetCardInfoUseCase(CardInfoApi cardInfoApi,
                                                        @Named("ui") Scheduler ui, @Named("work") Scheduler work,
                                                        AccessController accessController) {
        return new GetCardInfoUseCase(ui, work, accessController, cardInfoApi);
    }

    @Provides
    public CardInfoPresenter provideCardInfoPresenter(GetCardInfoUseCase getCardInfoUseCase,
                                                      Map<String, BaseStatelessPresenter> statelessPresenterMap) {
        BaseStatelessPresenter baseStatelessPresenter = statelessPresenterMap.get(CardInfoPresenter.class.getSimpleName());
        if (baseStatelessPresenter == null) {
            return new CardInfoPresenter(getCardInfoUseCase);
        } else {
            return (CardInfoPresenter) baseStatelessPresenter;
        }
    }
}
