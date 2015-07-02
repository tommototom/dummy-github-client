package com.github.app.UI;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.github.app.App;
import com.github.app.model.Commit;
import com.github.app.networking.LoaderCallback;
import com.github.app.networking.GithubApiService;
import com.github.app.networking.RetrofitLoader;
import com.github.app.networking.RetrofitLoaderManager;

import java.util.List;

public class RepositoriesListActivity extends Activity implements LoaderCallback<List<Commit>> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RepositoriesLoader loader = new RepositoriesLoader(this, App.getApiService());

        RetrofitLoaderManager.init(getLoaderManager(), 0, loader, this);
    }

    @Override
    public void onLoadFailure(Exception ex) {

    }

    @Override
    public void onLoadSuccess(List<Commit> result) {

    }


    private static class RepositoriesLoader extends RetrofitLoader<List<Commit>, GithubApiService> {
        public RepositoriesLoader(Context context, GithubApiService service) {
            super(context, service);
        }

        @Override
        public List<Commit> call(GithubApiService service) {
            return service.getRepositoryCommit("jakewharton", "actionbarsherlock");
        }
    }
}
