package ais.sample.com.login.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YaTomat on 10.06.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse implements Parcelable {
    @JsonProperty("AccessToken")
    public String accessToken;
    @JsonProperty("Actions")
    public List<Action> actionList;

    @Override
    public String toString() {
        return "LoginResponse{" +
                "accessToken='" + accessToken + '\'' +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.accessToken);
    }

    public LoginResponse() {
    }

    protected LoginResponse(Parcel in) {
        this.accessToken = in.readString();
    }

    public static final Parcelable.Creator<LoginResponse> CREATOR = new Parcelable.Creator<LoginResponse>() {
        @Override
        public LoginResponse createFromParcel(Parcel source) {
            return new LoginResponse(source);
        }

        @Override
        public LoginResponse[] newArray(int size) {
            return new LoginResponse[size];
        }
    };
}
