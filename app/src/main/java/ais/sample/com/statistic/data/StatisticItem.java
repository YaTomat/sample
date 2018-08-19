package ais.sample.com.statistic.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by YaTomat on 07.09.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StatisticItem implements Parcelable {
    public static final Parcelable.Creator<StatisticItem> CREATOR = new Parcelable.Creator<StatisticItem>() {
        @Override
        public StatisticItem createFromParcel(Parcel source) {
            return new StatisticItem(source);
        }

        @Override
        public StatisticItem[] newArray(int size) {
            return new StatisticItem[size];
        }
    };
    @JsonProperty(value = "DateTime")
    public String dateTime;
    @JsonProperty(value = "CardNumber")
    public String cardNumber;
    @JsonProperty(value = "SaleId")
    public int saleId;
    @JsonProperty(value = "SaleAmount")
    public float saleAmount;
    @JsonProperty(value = "SaleBonusPayed")
    public float saleBonusPayed;
    @JsonProperty(value = "DiscountProcent")
    public float discountPercent;
    @JsonProperty(value = "ToPayAmount")
    public float payingAmount;
    @JsonProperty(value = "BonusIncome")
    public float bonusIncome;
    @JsonProperty(value = "TotalBonusesInCard")
    public float totalBonusesInCard;

    public StatisticItem() {
    }

    protected StatisticItem(Parcel in) {
        this.dateTime = in.readString();
        this.cardNumber = in.readString();
        this.saleId = in.readInt();
        this.saleAmount = in.readFloat();
        this.saleBonusPayed = in.readFloat();
        this.discountPercent = in.readFloat();
        this.payingAmount = in.readFloat();
        this.bonusIncome = in.readFloat();
        this.totalBonusesInCard = in.readFloat();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dateTime);
        dest.writeString(this.cardNumber);
        dest.writeInt(this.saleId);
        dest.writeFloat(this.saleAmount);
        dest.writeFloat(this.saleBonusPayed);
        dest.writeFloat(this.discountPercent);
        dest.writeFloat(this.payingAmount);
        dest.writeFloat(this.bonusIncome);
        dest.writeFloat(this.totalBonusesInCard);
    }
}
