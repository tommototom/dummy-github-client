package com.github.app.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.app.App;
import com.github.app.R;
import com.github.app.model.Repository;
import com.github.app.networking.*;
import com.github.app.util.Constants;
import com.github.app.util.EndlessRecyclerOnScrollListener;
import com.github.app.util.Utils;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

import static com.github.app.util.Utils.*;

public class RepositoriesListActivity extends BaseActivity implements LoaderCallback<List<Repository>> {

    @InjectView(R.id.progress_wheel)
    ProgressWheel mProgressWheel;
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
        RetrofitLoaderManager.init(getLoaderManager(), mCurrentpage, loader, this, dao()); // provide loader id the same as page number
    }

    @Override
    public void onLoadFailure(Exception ex) {
        notifyNetworkIssues(this, ex);
        List page = dao().findReposAtPage(mCurrentpage - 1);
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
        if (repositories.isEmpty()) {
            notifyWithMessage(this, "No repositories found");
            mProgressWheel.setVisibility(View.INVISIBLE);
            return;
        }
        mRecyclerAdapter = new RepositoryRecyclerViewAdapter(repositories, this);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(lm) {
            @Override
            public void onLoadMore(int current_page) {
                runNewLoadDataTask();
            }
        });

        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
        mRecyclerView.setVisibility(View.VISIBLE);
        mProgressWheel.setVisibility(View.GONE);
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
