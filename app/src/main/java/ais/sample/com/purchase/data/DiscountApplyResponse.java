package ais.sample.com.purchase.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

/**
 * Created by YaTomat on 29.08.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiscountApplyResponse implements Parcelable {
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

    public DiscountApplyResponse() {
    }

    protected DiscountApplyResponse(Parcel in) {
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

    public static final Parcelable.Creator<DiscountApplyResponse> CREATOR = new Parcelable.Creator<DiscountApplyResponse>() {
        @Override
        public DiscountApplyResponse createFromParcel(Parcel source) {
            return new DiscountApplyResponse(source);
        }

        @Override
        public DiscountApplyResponse[] newArray(int size) {
            return new DiscountApplyResponse[size];
        }
    };
}
