package ais.sample.com.login.data;

/**
 * Created by YaTomat on 18.08.2017.
 */

public class LoginRequest {

    public String terminalPassword;
    public String sellerLogin;
    public String sellerPassword;
    public String terminalLogin;

    public LoginRequest(String terminalLogin, String terminalPassword, String sellerLogin, String sellerPassword) {
        this.terminalLogin = terminalLogin;
        this.terminalPassword = terminalPassword;
        this.sellerLogin = sellerLogin;
        this.sellerPassword = sellerPassword;
    }
}
