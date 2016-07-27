package pl.droidsononroids.coolloginbutton.api;

import android.os.Handler;

public class LoginManager {

    private LoginListener mLoginListener;
    private static final String CORRECT_LOGIN= "correct";
    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    public void setLoginListener(final LoginListener loginListener) {
        mLoginListener = loginListener;
    }

    public void performLogin(final String email) {
        if (email.equals(CORRECT_LOGIN)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginListener.loginSuccess();
                }
            }, 2000);
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLoginListener.loginFailure();
                }
            }, 2000);
        }
    }

    public interface LoginListener {
        void loginSuccess();
        void loginFailure();
    }

}
