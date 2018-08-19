package ais.sample.com.dagger.returnpurchase;

import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.dagger.common.ApplicationComponent;
import ais.sample.com.returnpurchase.presentation.FindPurchaseFragment;
import ais.sample.com.returnpurchase.presentation.ReturnPurchaseFragment;
import dagger.Component;

/**
 * Created by YaTomat on 12.09.2017.
 */
@Component(
        modules = ReturnPurhcaseModule.class,
        dependencies = {ApplicationComponent.class})
@FragmentScope
public interface ReturnPurchaseComponent {

    void inject(FindPurchaseFragment findPurchaseFragment);

    void inject(ReturnPurchaseFragment returnPurchaseFragment);
}
