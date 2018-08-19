package ais.sample.com.purchase.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by YaTomat on 01.07.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionResponse implements Parcelable {

    @JsonProperty("CardNumber")
    public String cardNumber;
    @JsonProperty("SaleAmount")
    public float saleAmount;
    @JsonProperty("BonusIncome")
    public float bonusIncome;
    @JsonProperty("BonusesForPayment")
    public float bonusesForPayment;
    @JsonProperty("SaleId")
    public int saleId;
    @JsonProperty("AditionalInfo")
    public String additionalInfo;

    @Override
    public String toString() {
        return "TransactionResponse{" +
                "cardNumber='" + cardNumber + '\'' +
                ", saleAmount=" + saleAmount +
                ", bonusIncome=" + bonusIncome +
                ", bonusesForPayment=" + bonusesForPayment +
                ", saleId=" + saleId +
                ", additionalInfo='" + additionalInfo + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.cardNumber);
        dest.writeFloat(this.saleAmount);
        dest.writeFloat(this.bonusIncome);
        dest.writeFloat(this.bonusesForPayment);
        dest.writeLong(this.saleId);
        dest.writeString(this.additionalInfo);
    }

    public TransactionResponse() {
    }

    protected TransactionResponse(Parcel in) {
        this.cardNumber = in.readString();
        this.saleAmount = in.readFloat();
        this.bonusIncome = in.readFloat();
        this.bonusesForPayment = in.readFloat();
        this.saleId = in.readInt();
        this.additionalInfo = in.readString();
    }

    public static final Parcelable.Creator<TransactionResponse> CREATOR = new Parcelable.Creator<TransactionResponse>() {
        @Override
        public TransactionResponse createFromParcel(Parcel source) {
            return new TransactionResponse(source);
        }

        @Override
        public TransactionResponse[] newArray(int size) {
            return new TransactionResponse[size];
        }
    };
}
