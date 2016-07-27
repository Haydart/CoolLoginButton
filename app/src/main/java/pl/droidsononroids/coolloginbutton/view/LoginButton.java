package pl.droidsononroids.coolloginbutton.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewAnimationUtils;
import android.widget.FrameLayout;
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
        calculateButtonCenter();
        successFL = (FrameLayout) findViewById(R.id.successFrameLayout);
        failureFL = (FrameLayout) findViewById(R.id.failureFrameLayout);
    }

    public void displaySuccessOrFailureAnimation(final int flag) {
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
    }

    public void calculateButtonCenter() {
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    public void swapTextForProgressBar() {
        findViewById(R.id.textView).setVisibility(INVISIBLE);
        findViewById(R.id.progressBar).setVisibility(VISIBLE);
    }

    public void swapProgressBarForText(){
        findViewById(R.id.textView).setVisibility(VISIBLE);
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
