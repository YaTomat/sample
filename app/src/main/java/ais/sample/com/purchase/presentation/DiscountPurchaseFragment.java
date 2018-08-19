package ais.sample.com.purchase.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.purchase.DaggerPurchaseComponent;
import ais.sample.com.dagger.purchase.PurchaseComponent;
import ais.sample.com.purchase.data.DiscountApplyResponse;
import ais.sample.com.purchase.data.TransactionResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 29.08.2017.
 */

public class DiscountPurchaseFragment extends BaseTitleFragment implements DiscountPurchaseView {

    public static final String ARG_TRANSACTION_RESPONSE = "ARG_TRANSACTION_RESPONSE";

    PurchaseComponent injectionComponent;
    private TransactionResponse transactionResponse;
    @Inject
    DiscountPurchasePresenter presenter;
    @BindView(R.id.tv_added_bonuses)
    TextView tvAddedBonuses;
    @BindView(R.id.tv_amount_purchase)
    TextView tvAmountPurchase;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_max_discount)
    TextView tvMaxDiscount;
    @BindView(R.id.et_max_discount)
    EditText etMaxDiscount;
    @BindView(R.id.btn_pay_bonuses)
    Button btnPayBonuses;
    private FullscreenProgressDialogFragment dialogFragment;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;

    public static DiscountPurchaseFragment newInstance(TransactionResponse transactionResponse) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_TRANSACTION_RESPONSE, transactionResponse);
        DiscountPurchaseFragment fragment = new DiscountPurchaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
        this.transactionResponse = getArguments().getParcelable(ARG_TRANSACTION_RESPONSE);
        presenter.setTransactionResponse(this.transactionResponse);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_discount_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onCreated(this, savedInstanceState);
        tvAddedBonuses.setText(String.format(Locale.getDefault(), "%.2f", transactionResponse.bonusIncome));
        tvAmountPurchase.setText(String.format(Locale.getDefault(), "%.2f", transactionResponse.saleAmount));
        tvCardNumber.setText(transactionResponse.cardNumber);
        tvMaxDiscount.setText(String.format(Locale.getDefault(), "%.2f", transactionResponse.bonusesForPayment));
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.discount_purchase);
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
        statelessPresenterMap.put(DiscountPurchasePresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroyed();
        super.onDestroy();
    }

    @Override
    public void handleError() {

    }

    @Override
    public void handleError(@StringRes int errorMessage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(errorMessage);
        builder.setCancelable(true);
        builder.setPositiveButton(
                android.R.string.ok,
                (dialog, id) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void handleError(String errorMessage) {

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
    public Observable<String> onWritingOffBonusesChanged() {
        return RxTextView.textChanges(etMaxDiscount).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<Object> onPayBonusesClick() {
        return RxView.clicks(btnPayBonuses);
    }

    @Override
    public void finishWritingOffDiscount(DiscountApplyResponse discountApplyResponse) {
        Toast.makeText(getContext(), R.string.discount_is_recorded, Toast.LENGTH_LONG).show();
        FragmentActivity activity = getActivity();
        activity.getSupportFragmentManager().beginTransaction().remove(this).commit();
        activity.getSupportFragmentManager().popBackStack();
        activity.getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(FinishPurchaseFragment.class.getSimpleName())
                .replace(R.id.main_container, FinishPurchaseFragment.newInstance(discountApplyResponse), FinishPurchaseFragment.class.getSimpleName())
                .commit();
    }

    @Override
    public void showMaxDiscountAlert(String writingOffBonuses) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(getString(R.string.max_discount_alert, writingOffBonuses));
        builder.setCancelable(true);
        builder.setPositiveButton(
                android.R.string.ok,
                (dialog, id) -> dialog.cancel());
        builder.show();
    }

    @Override
    public void setWritingOffDiscountAmount(String value) {
        etMaxDiscount.setText(value);
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(DiscountPurchasePresenter.class.getSimpleName());
    }

    private PurchaseComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerPurchaseComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }
}
