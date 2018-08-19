package ais.sample.com.login.presentation;

/**
 * Created by YaTomat on 06.08.2017.
 */

public interface LoginTerminalView extends LoginView {
    void onInputFinished(String login, String password);
}
