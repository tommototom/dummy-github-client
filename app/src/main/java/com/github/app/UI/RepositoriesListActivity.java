package com.github.app.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.app.App;
import com.github.app.R;
import com.github.app.model.Repository;
import com.github.app.networking.*;
import com.github.app.util.Constants;
import com.github.app.util.EndlessRecyclerOnScrollListener;

import java.util.List;

public class RepositoriesListActivity extends AppCompatActivity implements LoaderCallback<List<Repository>> {

    @InjectView(R.id.repositories_recycler_view)
    RecyclerView mRecyclerView;

    private RepositoryRecyclerViewAdapter mRecyclerAdapter;
    private int mCurrentpage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
        ButterKnife.inject(this);

        runNewLoadDataTask();
    }

    private void runNewLoadDataTask() {
        RepositoriesLoader loader = new RepositoriesLoader(mCurrentpage++, this, App.getApiService());
        RetrofitLoaderManager.init(getLoaderManager(), mCurrentpage, loader, this); // provide loader id the same as page number
        // todo check if repo ids for pages interferes commit's loaders ids
    }

    @Override
    public void onLoadFailure(Exception ex) {
        Log.e("LOAD", ex.getMessage());
        List page = App.getDaoInstance().findReposAtPage(mCurrentpage - 1);
        attachDataToAdapter(page);
    }

    @Override
    public void onLoadSuccess(List<Repository> result) {
        attachDataToAdapter(result);
    }

    void attachDataToAdapter(List<Repository> result) {
        if (mRecyclerAdapter == null) {
            initRecyclerView(result);
        } else {
            mRecyclerAdapter.attachLoadedData(result);
        }
    }
    private void initRecyclerView(List<Repository> repositories) {
        mRecyclerAdapter = new RepositoryRecyclerViewAdapter(repositories, this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.setOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int current_page) {
                runNewLoadDataTask();
            }
        });

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
    }


    private static class RepositoriesLoader extends RetrofitLoader<List<Repository>, GithubApiService> {
        private int page;
        private Context context;

        public RepositoriesLoader(int page, Context context, GithubApiService service) {
            super(context, service);
            this.page = page;
            this.context = context.getApplicationContext();
        }

        @Override
        public List<Repository> call(GithubApiService service) {
            String accessToken = context
                    .getSharedPreferences(Constants.APP_PREFS, Context.MODE_PRIVATE)
                    .getString(Constants.ACCESS_TOKEN_KEY, null);
            return service.getUserRepositoriesList(accessToken, page);
        }

        @Override
        public Class getEntityClass() {
            return Repository.class;
        }

        @Override
        public Integer getPageNumber() {
            return page;
        }
    }
}
