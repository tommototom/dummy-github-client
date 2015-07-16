package com.github.app.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.github.app.App;
import com.github.app.R;

import static com.github.app.util.Constants.*;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (!recentAuthExists()) {
            tryAuth();
        } else {
            Intent intent = new Intent(this, RepositoriesListActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private boolean recentAuthExists() {
        return getSharedPreferences(APP_PREFS, MODE_PRIVATE).contains(ACCESS_TOKEN_KEY);
    }

    private void tryAuth() {
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(GITHUB_AUTH_URL + "?client_id=" + GITHUB_CLIENT_ID + "&redirect_uri=" + AUTH_REDIRECT_URI));
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_FROM_BACKGROUND);
        startActivity(intent);

        Toast.makeText(App.get(), "Redirecting to browser", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Toast.makeText(App.get(), "Back in app", Toast.LENGTH_SHORT).show();

        final Uri uri = intent.getData();
        if (uri != null) {
            System.out.println(uri);
        }
    }


}
