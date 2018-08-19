package ais.sample.com.purchase.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

import ais.sample.com.R;
import ais.sample.com.common.BaseTitleFragment;
import ais.sample.com.purchase.data.DiscountApplyResponse;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YaTomat on 05.07.2017.
 */

public class FinishPurchaseFragment extends BaseTitleFragment {

    public static final String ARG_DISCOUNT_RESPONSE = "ARG_DISCOUNT_RESPONSE";
    private DiscountApplyResponse discountResponse;
    @BindView(R.id.tv_added_bonuses)
    TextView tvAddedBonuses;
    @BindView(R.id.tv_amount_purchase)
    TextView tvAmountPurchase;
    @BindView(R.id.tv_card_number)
    TextView tvCardNumber;
    @BindView(R.id.tv_amount_for_paying)
    TextView tvAmountForPaying;
    @BindView(R.id.tv_percent_discount)
    TextView tvDiscountPercent;
    @BindView(R.id.tv_amount_discount)
    TextView tvDiscountAmount;
    @BindView(R.id.tv_current_amount_bonuses)
    TextView tvCurrentAmount;
    @BindView(R.id.tv_date_time)
    TextView tvDateTime;

    public static FinishPurchaseFragment newInstance(DiscountApplyResponse discountApplyResponse) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_DISCOUNT_RESPONSE, discountApplyResponse);
        FinishPurchaseFragment fragment = new FinishPurchaseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.discountResponse = getArguments().getParcelable(ARG_DISCOUNT_RESPONSE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_finish_purchase, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        tvDateTime.setText(discountResponse.dateTime);
        tvDiscountAmount.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.saleBonusPayed));
        tvAddedBonuses.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.bonusIncome));
        tvAmountForPaying.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.payingAmount));
        tvAmountPurchase.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.saleAmount));
        tvDiscountPercent.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.discountPercent));
        tvCurrentAmount.setText(String.format(Locale.getDefault(), "%.2f", discountResponse.totalBonusesInCard));
        tvCardNumber.setText(discountResponse.cardNumber);
    }

    @OnClick(R.id.btn_new_purchase)
    public void onNewPurchaseClick() {
        getActivity()
                .getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, PurchaseFragment.newInstance())
                .commit();
    }

    @Nullable
    @Override
    protected CharSequence provideTitle() {
        return getString(R.string.purchase_info_title);
    }
}
