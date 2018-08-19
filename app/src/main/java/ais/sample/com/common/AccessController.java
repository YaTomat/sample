package ais.sample.com.common;

import ais.sample.com.login.data.LoginResponse;

/**
 * Created by YaTomat on 30.08.2017.
 */

public class AccessController {

    private LoginResponse loginResponse;

    public String getAccessToken() {
        return loginResponse.accessToken;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public void setLoginResponse(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public boolean isEmpty() {
        return loginResponse == null;
    }

    public void clear() {
        loginResponse = null;
    }
}
