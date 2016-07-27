package pl.droidsononroids.coolloginbutton.screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsononroids.coolloginbutton.R;
import pl.droidsononroids.coolloginbutton.api.LoginManager;
import pl.droidsononroids.coolloginbutton.view.LoginButton;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.email_edit_text) EditText mEmailEditText;
    @BindView(R.id.loginButton) LoginButton loginButton;
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
                Toast.makeText(MainActivity.this, "SUCCESSFUL LOGIN", Toast.LENGTH_SHORT).show();
                loginButton.displaySuccessAnimation();
            }

            @Override
            public void loginFailure() {
                Toast.makeText(MainActivity.this, "LOGIN FAILURE", Toast.LENGTH_SHORT).show();
                loginButton.displayFailureAnimation();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginManager.performLogin(mEmailEditText.getText().toString().trim());
            }
        });
    }
}