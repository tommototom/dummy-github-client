package com.github.app.UI;

import android.content.Intent;
import android.net.Uri;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.github.app.App;
import android.widget.Button;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.github.app.R;

import static android.content.Intent.*;
import static com.github.app.util.Constants.*;


public class LoginActivity extends AppCompatActivity {

    @InjectView(R.id.login_submit_btn)
    Button mLoginBtn;

    @OnClick(R.id.login_submit_btn)
    public void onLoginClick() {
        tryAuth();
    }
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!recentAuthExists()) {
            setContentView(R.layout.activity_login);
            ButterKnife.inject(this);
            if (getSupportActionBar() != null) getSupportActionBar().hide();
        } else {
            Intent intent = new Intent(this, RepositoriesListActivity.class)
                    .setFlags(FLAG_ACTIVITY_CLEAR_TASK | FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private boolean recentAuthExists() {
        return getSharedPreferences(APP_PREFS, MODE_PRIVATE).contains(ACCESS_TOKEN_KEY);
    }

    private void tryAuth() {
        Intent intent = new Intent(ACTION_VIEW,
                Uri.parse(GITHUB_AUTH_URL + "?client_id=" + GITHUB_CLIENT_ID + "&redirect_uri=" + AUTH_REDIRECT_URI));
        intent.setFlags(FLAG_ACTIVITY_NO_HISTORY | FLAG_FROM_BACKGROUND);
        startActivity(intent);

        Toast.makeText(App.get(), "Redirecting to github", Toast.LENGTH_LONG).show();
    }

}
