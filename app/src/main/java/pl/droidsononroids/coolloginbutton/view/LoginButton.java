package pl.droidsononroids.coolloginbutton.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import pl.droidsononroids.coolloginbutton.R;

public class LoginButton extends FrameLayout{
    public LoginButton(Context context) {
        this(context, null);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_button_layout, this, true);
    }

    public void displaySuccessAnimation(){

    }

    public void displayFailureAnimation(){

    }
}
