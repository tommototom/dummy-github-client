package com.github.app;

import android.app.Application;
import android.util.Log;
import com.github.app.db.RealmDao;
import com.github.app.db.RealmDaoImpl;
import com.github.app.model.Commit;
import com.github.app.networking.GithubApiService;
import com.github.app.util.CommitCustomGsonDeserializer;
import com.github.app.util.Constants;
import com.github.app.util.net.PersistentCookieStore;
import com.google.gson.*;
import com.squareup.okhttp.OkHttpClient;
import io.realm.Realm;
import io.realm.RealmObject;
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
    private static Realm sRealmInstance;
    private static RealmDaoImpl sDaoImpl;


    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initHttpClientWithCookieManager();

        initRestAdapter();

        initRealmObj();
    }

    /**
     *  makes http client use cookie manager based on SharedPreferences to store cookies after application gets killed
     */
    private void initHttpClientWithCookieManager() {
        sClient = new OkHttpClient().setConnectionPool(new com.squareup.okhttp.ConnectionPool(3, 30 * 1000));

        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(this), CookiePolicy.ACCEPT_ALL);
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
        sClient.setCookieHandler(cookieManager);

        final List<HttpCookie> cookies = ((CookieManager) sClient.getCookieHandler()).getCookieStore().getCookies();
        Log.e("Cookies", "saved cookies' store size is: " + cookies.size());
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
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES) // provides camel case recognization
                .registerTypeAdapter(Commit.class, new CommitCustomGsonDeserializer())
                .setExclusionStrategies(new ExclusionStrategy() { // solves gson's recursion which otherwise causes stackoverflowerror
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }
                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();

        sRestAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.GITHUB_BASE_URL)
                .setClient(new OkClient(sClient))
                .setRequestInterceptor(requestInterceptor)
                .setConverter(new GsonConverter(gson))
                .build();
    }


    private void initRealmObj() {
        sRealmInstance = Realm.getInstance(this);
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


    public static Realm getRealmInstance() {
        return sRealmInstance;
    }

    public static RealmDao getDaoInstance() {
        if (sDaoImpl == null) sDaoImpl = new RealmDaoImpl();
        return sDaoImpl;
    }
}
