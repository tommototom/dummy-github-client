package com.github.app;

import android.app.Application;
import android.util.Log;
import com.github.app.model.Commit;
import com.github.app.networking.GithubApiService;
import com.github.app.util.CommitCustomGsonDeserializer;
import com.github.app.util.Constants;
import com.github.app.util.net.PersistentCookieStore;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpCookie;
import java.util.List;

public class App extends Application {

    private static App sInstance;

    private static OkHttpClient sClient;
    private static RestAdapter sRestAdapter;
    private static GithubApiService sApiService;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initHttpCookieManager();

        initRestAdapter();
    }


    /**
     * creates and accepts custom Gson and request interceptors for Retrofit adapter
     */
    private void initRestAdapter() {
        RequestInterceptor requestInterceptor = new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                request.addHeader("Accept:", "application/vnd.github.v3+json");
            }
        };

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Commit.class, new CommitCustomGsonDeserializer())
                .create();

        sRestAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.GITHUB_BASE_URL)
                .setClient(new OkClient(sClient))
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
    }


    /**
     *  makes http client use cookie manager based on SharedPreferences to store cookies after application gets killed
     */
    private void initHttpCookieManager() {
        sClient = new OkHttpClient().setConnectionPool(new com.squareup.okhttp.ConnectionPool(3, 30 * 1000));

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(this), CookiePolicy.ACCEPT_ALL);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        sClient.setCookieHandler(cookieManager);

        final List<HttpCookie> cookies = ((CookieManager) sClient.getCookieHandler()).getCookieStore().getCookies();
        Log.e("Cookies", "saved cookies' store size is: " + cookies.size());
    }


    /**
     * returns link to application context
     */
    public static App get() {
        return sInstance;
    }


    /**
     * returns lazy initialized retrofit's service
     */
    public static GithubApiService getApiService() {
        if (sApiService == null) {
            sApiService = sRestAdapter.create(GithubApiService.class);
        }
        return sApiService;
    }
}
