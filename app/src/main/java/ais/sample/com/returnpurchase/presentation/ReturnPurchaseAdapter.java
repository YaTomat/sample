package ais.sample.com.returnpurchase.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.List;
import java.util.Locale;

import ais.sample.com.R;
import ais.sample.com.statistic.data.StatisticItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YaTomat on 13.09.2017.
 */

public class ReturnPurchaseAdapter extends RecyclerSwipeAdapter<RecyclerView.ViewHolder> {

    private List<StatisticItem> statisticItems;
    private OnReturnClickListener onReturnClickListener;

    public ReturnPurchaseAdapter(OnReturnClickListener onReturnClickListener) {
        this.onReturnClickListener = onReturnClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_return_purchase, parent, false);
        return new StatisticItemHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StatisticItemHolder statisticItemHolder = (StatisticItemHolder) holder;
        StatisticItem statisticItem = statisticItems.get(position);
        statisticItemHolder.tvAmountPurchase.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.saleAmount));
        statisticItemHolder.tvBonusIncome.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.bonusIncome));
        statisticItemHolder.tvCardNumber.setText(statisticItem.cardNumber);
        statisticItemHolder.tvDateTime.setText(statisticItem.dateTime);
        statisticItemHolder.tvPaidBonuses.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.saleBonusPayed));
        statisticItemHolder.tvToPay.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.payingAmount));
        statisticItemHolder.rlInfo.setOnClickListener((view) -> {
            statisticItemHolder.swipeLayout.open();
        });
        statisticItemHolder.btnReturn.setOnClickListener((view -> {
            onReturnClickListener.onReturnClick(statisticItem.saleId);
        }));
    }

    @Override
    public int getItemCount() {
        return statisticItems == null ? 0 : statisticItems.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    public void setOnReturnClickListener(OnReturnClickListener onReturnClickListener) {
        this.onReturnClickListener = onReturnClickListener;
    }

    public void setStatisticItems(List<StatisticItem> statisticItems) {
        this.statisticItems = statisticItems;
    }

    interface OnReturnClickListener {
        void onReturnClick(int idSale);
    }

    public static class StatisticItemHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_card_number)
        TextView tvCardNumber;
        @BindView(R.id.tv_date_purchase)
        TextView tvDateTime;
        @BindView(R.id.tv_amount_purchase)
        TextView tvAmountPurchase;
        @BindView(R.id.tv_paid_bonuses)
        TextView tvPaidBonuses;
        @BindView(R.id.tv_bonus_income)
        TextView tvBonusIncome;
        @BindView(R.id.tv_to_pay)
        TextView tvToPay;
        @BindView(R.id.btn_return)
        Button btnReturn;
        @BindView(R.id.swipe)
        SwipeLayout swipeLayout;
        @BindView(R.id.rl_info_purchase)
        RelativeLayout rlInfo;

        public StatisticItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}