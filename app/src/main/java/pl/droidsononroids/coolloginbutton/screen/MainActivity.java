package pl.droidsononroids.coolloginbutton.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsononroids.coolloginbutton.R;
import pl.droidsononroids.coolloginbutton.api.LoginManager;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.email_edit_text) EditText mEmailEditText;
    private LoginManager mLoginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLoginManager = new LoginManager();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        mLoginManager.setLoginListener(new LoginManager.LoginListener() {
            @Override
            public void loginSuccess() {

            }

            @Override
            public void loginFailure() {
                //no-op
            }
        });
    }
}