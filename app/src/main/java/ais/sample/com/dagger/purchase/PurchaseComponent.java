package ais.sample.com.dagger.purchase;

import ais.sample.com.dagger.FragmentScope;
import ais.sample.com.dagger.common.ApplicationComponent;
import ais.sample.com.purchase.presentation.DiscountPurchaseFragment;
import ais.sample.com.purchase.presentation.PurchaseFragment;
import dagger.Component;

/**
 * Created by YaTomat on 27.08.2017.
 */
@Component(
        modules = PurchaseModule.class,
        dependencies = {ApplicationComponent.class})
@FragmentScope
public interface PurchaseComponent {

    void inject(PurchaseFragment fragment);

    void inject(DiscountPurchaseFragment fragment);

}
