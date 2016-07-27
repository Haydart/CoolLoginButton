package pl.droidsononroids.coolloginbutton.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import pl.droidsononroids.coolloginbutton.R;
import pl.droidsononroids.coolloginbutton.api.LoginManager;

public class LoginButton extends FrameLayout {

    private int centerX, centerY;
    private FrameLayout successFL, failureFL;

    public LoginButton(Context context) {
        this(context, null);
    }

    public LoginButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.login_button_layout, this, true);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LoginButton);
        final String text = ta.getString(R.styleable.LoginButton_text);
        final float dimension = ta.getDimension(R.styleable.LoginButton_textsize, 2);
        int successColor = ta.getColor(R.styleable.LoginButton_success_color, Color.GREEN);
        int failureColor = ta.getColor(R.styleable.LoginButton_failure_color, Color.RED);
        int defaultButtonColor = ta.getColor(R.styleable.LoginButton_default_color, Color.BLUE);
        final Drawable successIcon = ta.getDrawable(R.styleable.LoginButton_success_icon);
        final Drawable failureIcon = ta.getDrawable(R.styleable.LoginButton_failure_icon);

        ta.recycle();

        successFL = (FrameLayout) findViewById(R.id.successFrameLayout);
        failureFL = (FrameLayout) findViewById(R.id.failureFrameLayout);

        if(text != null){
            TextView signUpTextView = (TextView)findViewById(R.id.signUpTextView);
            signUpTextView.setText(text);
            signUpTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,dimension);
        }
        successFL.setBackgroundColor(successColor);
        failureFL.setBackgroundColor(failureColor);
        ((ImageView)findViewById(R.id.successImageView)).setImageDrawable(successIcon);
        ((ImageView)findViewById(R.id.errorImageView)).setImageDrawable(failureIcon);

        this.setBackground(ContextCompat.getDrawable(context,R.drawable.button_click_effect));

        calculateButtonCenter();
    }

    public void displaySuccessOrFailureAnimation(final int flag) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setButtonClickability(false);
            final Animator delayAnimator = ViewAnimationUtils.createCircularReveal(
                    flag == LoginManager.SUCCESS ? successFL : failureFL,
                    centerX, centerY, 128, 512);
            delayAnimator.setDuration(500);

            delayAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);

                    hideSuccessOrFailure(flag);
                }

            });
            swapProgressBarForText();
            delayAnimator.start();
            (flag == LoginManager.SUCCESS ? successFL : failureFL).setVisibility(VISIBLE);
        }else{
            Toast.makeText(getContext(), "Too low android!", Toast.LENGTH_SHORT).show();
        }

    }

    public void calculateButtonCenter() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    public void swapTextForProgressBar() {
        findViewById(R.id.signUpTextView).setVisibility(INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(VISIBLE);
    }

    public void swapProgressBarForText(){
        findViewById(R.id.signUpTextView).setVisibility(VISIBLE);
        findViewById(R.id.progressBar).setVisibility(INVISIBLE);
    }

    private void setButtonClickability(boolean flag){
        this.setEnabled(flag);
        this.setClickable(flag);
    }

    private void hideSuccessOrFailure(final int flag) {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                final Animator closingAnimator = ViewAnimationUtils.createCircularReveal(flag == LoginManager.SUCCESS ? successFL : failureFL,
                        centerX, centerY, 512, 0).setDuration(250);
                closingAnimator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        (flag == LoginManager.SUCCESS ? successFL : failureFL).setVisibility(INVISIBLE);
                        setButtonClickability(true);
                    }
                });
                closingAnimator.start();
            }
        }, 1000);
    }
}
