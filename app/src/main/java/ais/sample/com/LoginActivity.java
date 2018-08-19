package ais.sample.com;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import ais.sample.com.common.Constants;
import ais.sample.com.login.presentation.LoginSellerFragment;
import ais.sample.com.login.presentation.LoginTerminalFragment;

/**
 * Created by YaTomat on 10.06.2017.
 */

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            String terminalPassword = getSharedPreferences(Constants.PREFERENCES_TERMINAL, MODE_PRIVATE)
                    .getString(Constants.PREFERENCES_TERMINAL_PASSWORD, null);
            if (terminalPassword == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_login_container, LoginTerminalFragment.newInstance())
                        .commit();
            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.fl_login_container, LoginSellerFragment.newInstance())
                        .commit();
            }
        }
    }
}
