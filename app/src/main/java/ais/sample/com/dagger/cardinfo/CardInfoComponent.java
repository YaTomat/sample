package ais.sample.com.dagger.cardinfo;

import ais.sample.com.cardinfo.presentation.CardInfoFragment;
import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.dagger.common.ApplicationComponent;
import dagger.Component;

/**
 * Created by YaTomat on 10.09.2017.
 */
@Component(
        modules = CardInfoModule.class,
        dependencies = {ApplicationComponent.class})
@FragmentScope
public interface CardInfoComponent {
    void inject(CardInfoFragment cardInfoFragment);
}
