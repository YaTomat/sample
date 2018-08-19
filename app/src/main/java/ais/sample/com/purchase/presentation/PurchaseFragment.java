package ais.sample.com.purchase.presentation;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxAdapterView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.FragmentUtils;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.purchase.DaggerPurchaseComponent;
import ais.sample.com.dagger.purchase.PurchaseComponent;
import ais.sample.com.purchase.data.TransactionResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 22.06.2017.
 */

public class PurchaseFragment extends BaseTitleFragment implements PurchaseView {

    @Inject
    PurchasePresenter presenter;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;

    PurchaseComponent injectionComponent;

    @BindView(R.id.spinner)
    Spinner spActions;
    @BindView(R.id.iv_camera)
    ImageView ivCamera;
    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.et_amount_purchase)
    EditText etAmountPurchase;
    @BindView(R.id.btn_perform_transaction)
    Button btnPerformTransaction;
    private FullscreenProgressDialogFragment dialogFragment;

    public static PurchaseFragment newInstance() {

        Bundle args = new Bundle();

        PurchaseFragment fragment = new PurchaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setUpInjectionComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_purchase, container, false);
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
        return getString(R.string.purchase_title);
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
        statelessPresenterMap.put(PurchaseFragment.class.getSimpleName(), presenter);
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
    public Observable<String> onPurchaseAmountChanged() {
        return RxTextView.textChanges(etAmountPurchase).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> onCardNumberChanged() {
        return RxTextView.textChanges(etCardNumber).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> onPhoneNumberChanged() {
        return RxTextView.textChanges(etPhoneNumber).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<Integer> onStockChanged() {
        return RxAdapterView.itemSelections(spActions);
    }

    @Override
    public Observable<Object> onPerformTransactionPress() {
        return RxView.clicks(btnPerformTransaction).doOnNext((obj) -> {
            FragmentUtils.hideKeyboard(getActivity());
        });
    }

    @Override
    public void updateStocks(List<String> stocks) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_dropdown_item, stocks);
        spActions.setAdapter(adapter);
    }

    @Override
    public void updateCardNumber(String code) {
        etCardNumber.setText(code, TextView.BufferType.EDITABLE);
    }

    @Override
    public void onTransactionFinished(TransactionResponse transactionResponse) {
        Toast.makeText(getContext(), R.string.successful_purchase, Toast.LENGTH_LONG).show();
        etCardNumber.setText("");
        etPhoneNumber.setText("");
        etAmountPurchase.setText("");
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(DiscountPurchaseFragment.class.getSimpleName())
                .replace(R.id.main_container, DiscountPurchaseFragment.newInstance(transactionResponse), DiscountPurchaseFragment.class.getSimpleName())
                .commit();
    }

    @OnClick(R.id.iv_camera)
    public void onCameraClick() {
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                presenter.onCameraResult(result.getContents());
            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private PurchaseComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerPurchaseComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(PurchasePresenter.class.getSimpleName());
    }
}
