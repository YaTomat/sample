package ais.sample.com.cardinfo.presentation;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.cardinfo.data.CardInfoResponse;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.cardinfo.CardInfoComponent;
import ais.sample.com.dagger.cardinfo.DaggerCardInfoComponent;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 10.09.2017.
 */

public class CardInfoFragment extends BaseTitleFragment implements CardInfoView {

    @Inject
    CardInfoPresenter presenter;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_surname)
    TextView tvSurname;
    @BindView(R.id.tv_third_name)
    TextView tvThirdName;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.tv_current_amount_bonuses)
    TextView tvCurrentBonuses;
    @BindView(R.id.btn_get_card_info)
    Button btnGetCardInfo;
    @BindView(R.id.tv_virtual_card)
    TextView tvIsVirtual;
    @BindView(R.id.tv_is_vip)
    TextView tvIsVip;

    private CardInfoComponent injectionComponent;
    private FullscreenProgressDialogFragment dialogFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_card_info, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onCreated(this, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.purchase_info);
    }

    @Override
    public void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        presenter.onSavedInstance();
        statelessPresenterMap.put(CardInfoPresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroyed();
        super.onDestroy();
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(CardInfoPresenter.class.getSimpleName());
    }

    @Override
    public void handleError() {

    }

    @Override
    public void handleError(@StringRes int errorMessage) {
        handleError(getString(errorMessage));
    }

    @Override
    public void handleError(String errorMessage) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(getContext());
        }
        builder.setTitle(getString(R.string.login_error_title))
                .setMessage(errorMessage)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                })
                .show();
    }


    @Override
    public void showProgress() {
        dialogFragment = FullscreenProgressDialogFragment.newInstance();
        show(getActivity().getSupportFragmentManager(), dialogFragment);
    }

    @Override
    public void showProgress(@StringRes int message) {

    }

    @Override
    public void stopProgress() {
        if (dialogFragment != null && !getActivity().getSupportFragmentManager().isDestroyed()) {
            dialogFragment.dismissAllowingStateLoss();
        }
    }

    @Override
    public void updateView(CardInfoResponse cardInfoResponse) {
        tvCurrentBonuses.setText(String.format(Locale.getDefault(), "%.2f", cardInfoResponse.totalBonuses));
        tvName.setText(cardInfoResponse.name);
        tvSurname.setText(cardInfoResponse.lastName);
        tvThirdName.setText(cardInfoResponse.secondName);
        tvIsVip.setText(cardInfoResponse.isVip ? android.R.string.yes : android.R.string.no);
        tvIsVirtual.setText(cardInfoResponse.isVirtual ? android.R.string.yes : android.R.string.no);
    }

    @Override
    public Observable<String> onCardNumberChanged() {
        return RxTextView.textChanges(etCardNumber).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<Object> onGetInfoClick() {
        return RxView.clicks(btnGetCardInfo);
    }

    private CardInfoComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerCardInfoComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }
}
