package com.github.app.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.github.app.App;
import com.github.app.util.Utils;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import static com.github.app.util.Constants.*;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) { //todo figure out why onCreate triggered instead of onNewIntent
        super.onCreate(savedInstanceState);

        if (wasOauthAndSucceed()) {
            SharedPreferences sp = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
            String requestCode = Utils.splitUriQuery(getIntent().getData()).get("code");;

            authorizeWithRequestCode(requestCode);
        }
    }

    private void authorizeWithRequestCode(final String requestCode) {
        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                final FormEncodingBuilder builder = new FormEncodingBuilder()
                        .add("client_id", GITHUB_CLIENT_ID)
                        .add("client_secret", GITHUB_CLIENT_SECRET)
                        .add("code", requestCode);

                Request request = new Request.Builder()
                        .url("https://github.com/login/oauth/access_token")
                        .post(builder.build())
                        .build();

                try {
                    Response response = App.getHttpClient().newCall(request).execute();

                    String query = response.body().string();
                    String token = Utils.splitUriQuery(query).get(ACCESS_TOKEN_KEY);
                    getSharedPreferences(APP_PREFS, MODE_PRIVATE)
                            .edit().
                            putString(ACCESS_TOKEN_KEY, token);
                    return true;
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                }
            }

            @Override
            protected void onPostExecute(Boolean wasSuccessful) {
                if (wasSuccessful) {
                    Intent intent = new Intent(SplashScreenActivity.this, RepositoriesListActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    Toast.makeText(App.get(), "Auth failed", Toast.LENGTH_SHORT);
                    finish();
                }
            }
        }.execute();
    }


    private boolean wasOauthAndSucceed() {
        Uri uri = getIntent().getData();
        return uri.getScheme().equals(AUTH_REDIRECT_SCHEME);
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
