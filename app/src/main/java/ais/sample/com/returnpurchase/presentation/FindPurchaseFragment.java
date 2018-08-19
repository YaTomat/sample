package ais.sample.com.returnpurchase.presentation;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.inject.Inject;

import ais.sample.com.MyApplication;
import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.common.BaseStatelessPresenter;
import ais.sample.com.common.FullscreenProgressDialogFragment;
import ais.sample.com.dagger.returnpurchase.DaggerReturnPurchaseComponent;
import ais.sample.com.dagger.returnpurchase.ReturnPurchaseComponent;
import ais.sample.com.returnpurchase.data.FindPurchaseResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;

import static ais.sample.com.common.FragmentUtils.show;

/**
 * Created by YaTomat on 12.09.2017.
 */

public class FindPurchaseFragment extends BaseTitleFragment implements FindPurchaseView {

    @Inject
    FindPurchasePresenter presenter;
    @Inject
    Map<String, BaseStatelessPresenter> statelessPresenterMap;

    @BindView(R.id.et_card_number)
    EditText etCardNumber;
    @BindView(R.id.et_phone_number)
    EditText etPhoneNumber;
    @BindView(R.id.aux_tv_phone_number)
    TextView auxTvPhoneNumber;
    @BindView(R.id.ll_card_layout)
    LinearLayout llCardLayout;
    @BindView(R.id.aux_tv_card_number)
    TextView auxTvCardNumber;
    @BindView(R.id.et_amount_purchase)
    EditText etAmountPurchase;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.et_date)
    EditText etDate;

    private FullscreenProgressDialogFragment dialogFragment;
    private ReturnPurchaseComponent injectionComponent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpInjectionComponent().inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_find_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.onCreated(this, savedInstanceState);
        etDate.setFocusable(false);
        Calendar calendarInstance = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDate = (view1, year, monthOfYear, dayOfMonth) -> {
            calendarInstance.set(Calendar.YEAR, year);
            calendarInstance.set(Calendar.MONTH, monthOfYear);
            calendarInstance.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateDate(calendarInstance.getTime(), etDate);
        };
        etDate.setOnClickListener(v -> new DatePickerDialog(getContext(), startDate, calendarInstance
                .get(Calendar.YEAR), calendarInstance.get(Calendar.MONTH),
                calendarInstance.get(Calendar.DAY_OF_MONTH)).show());


    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.find_purchase_title);
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
        statelessPresenterMap.put(FindPurchasePresenter.class.getSimpleName(), presenter);
    }

    @Override
    public void onDestroy() {
        presenter.onDestroyed();
        super.onDestroy();
    }

    @Override
    public void removePresenter() {
        statelessPresenterMap.remove(FindPurchasePresenter.class.getSimpleName());
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
    public Observable<String> getPurchaseDate() {
        return RxTextView.textChanges(etDate).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> getCardNumber() {
        return RxTextView.textChanges(etCardNumber).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> getPhoneNumber() {
        return RxTextView.textChanges(etPhoneNumber).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<String> getAmountPurchase() {
        return RxTextView.textChanges(etAmountPurchase).map((text) -> text.length() > 0 ? text.toString() : "");
    }

    @Override
    public Observable<Object> onFindClick() {
        return RxView.clicks(btnSearch);
    }

    @Override
    public void updateView(FindPurchaseResponse findPurchaseResponse) {
        if (findPurchaseResponse.list.size() > 0) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, ReturnPurchaseFragment.newInstance(new ArrayList<>(findPurchaseResponse.list)))
                    .addToBackStack(null)
                    .commit();
        } else {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getContext());
            }
            builder.setMessage(R.string.purchases_not_found)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {

                    })
                    .show();
        }
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
                etCardNumber.setText(result.getContents());
            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private ReturnPurchaseComponent setUpInjectionComponent() {
        if (injectionComponent == null) {
            injectionComponent = DaggerReturnPurchaseComponent.builder()
                    .applicationComponent(MyApplication.getComponent())
                    .build();
        }
        return injectionComponent;
    }

    private void updateDate(Date date, EditText editText) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        editText.setText(simpleDateFormat.format(date));
    }

}
