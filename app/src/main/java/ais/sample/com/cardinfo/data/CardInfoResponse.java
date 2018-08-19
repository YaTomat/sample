package ais.sample.com.cardinfo.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by YaTomat on 10.09.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardInfoResponse {

    @JsonProperty(value = "FirstName")
    public String name;
    @JsonProperty(value = "CardNumber")
    public String cardNumber;
    @JsonProperty(value = "SecondName")
    public String secondName;
    @JsonProperty(value = "LastName")
    public String lastName;
    @JsonProperty(value = "IsVipCard ")
    public boolean isVip;
    @JsonProperty(value = "TotalBonuses ")
    public float totalBonuses;
    @JsonProperty(value = "IsVirtualCard  ")
    public boolean isVirtual;
}
