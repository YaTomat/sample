package ais.sample.com.statistic.presentation;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import ais.sample.com.R;
import ais.sample.com.statistic.data.StatisticItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by YaTomat on 05.09.2017.
 */

public class StatisticAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int CHOOSE_DATE = 1;
    public static final int EMPTY_LIST = 2;
    public static final int STATISTIC_ITEM = 3;
    private List<StatisticItem> statisticItems;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder vh = null;
        switch (viewType) {
            case CHOOSE_DATE: {
                TextView messageView = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_recycler_message, parent, false);
                vh = new TextViewHolder(messageView);
                break;
            }
            case EMPTY_LIST: {
                TextView messageView = (TextView) LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_recycler_message, parent, false);
                vh = new TextViewHolder(messageView);
                break;
            }
            case STATISTIC_ITEM: {
                View v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_statistic, parent, false);
                vh = new StatisticItemHolder(v);
                break;
            }
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == EMPTY_LIST || getItemViewType(position) == CHOOSE_DATE) {
            TextViewHolder textHolder = (TextViewHolder) holder;
            if (getItemViewType(position) == EMPTY_LIST) {
                textHolder.mTextView.setText(R.string.statistic_empty_list);
            } else {
                textHolder.mTextView.setText(R.string.statistic_choose_date);
            }
        } else {
            StatisticItemHolder statisticItemHolder = (StatisticItemHolder) holder;
            StatisticItem statisticItem = statisticItems.get(position);
            statisticItemHolder.tvAmountPurchase.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.saleAmount));
            statisticItemHolder.tvBonusIncome.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.bonusIncome));
            statisticItemHolder.tvCardNumber.setText(statisticItem.cardNumber);
            statisticItemHolder.tvDateTime.setText(statisticItem.dateTime);
            statisticItemHolder.tvPaidBonuses.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.saleBonusPayed));
            statisticItemHolder.tvToPay.setText(String.format(Locale.getDefault(), "%.2f", statisticItem.payingAmount));
        }
    }

    @Override
    public int getItemCount() {
        return statisticItems == null ? 1
                : statisticItems.size() == 0 ? 1 : statisticItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (statisticItems == null) {
            return CHOOSE_DATE;
        } else {
            if (statisticItems.size() == 0) {
                return EMPTY_LIST;
            } else {
                return STATISTIC_ITEM;
            }
        }
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

        public StatisticItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class TextViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public TextViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public void setStatisticItems(List<StatisticItem> statisticItems) {
        this.statisticItems = statisticItems;
    }
}
