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
import com.github.app.model.Commit;
import com.github.app.networking.GithubApiService;
import com.github.app.networking.LoaderCallback;
import com.github.app.networking.RetrofitLoader;
import com.github.app.networking.RetrofitLoaderManager;
import com.github.app.util.EndlessRecyclerOnScrollListener;

import java.util.List;

import static com.github.app.util.Constants.*;

public class CommitsListActivity extends AppCompatActivity implements LoaderCallback<List<Commit>> {

    @InjectView(R.id.commits_recycler_view)
    RecyclerView mRecyclerView;

    private CommitsRecyclerViewAdapter mRecyclerAdapter;
    private int mCurrentPage = 1;
    private String mRepoName;
    private String mRepoOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits_list);
        ButterKnife.inject(this);

        mRepoName = getIntent().getStringExtra(REPOSITORY_NAME_EXTRA);
        mRepoOwner = getIntent().getStringExtra(REPO_OWNER_EXTRA);
        getSupportActionBar().setTitle(mRepoName);
        runNewLoadDataTask();
    }

    private void runNewLoadDataTask() {
        CommitsLoader loader = new CommitsLoader(this, App.getApiService(), mRepoOwner, mCurrentPage++, mRepoName);
        RetrofitLoaderManager.init(getLoaderManager(), mCurrentPage, loader, this); // provide loader id the same as page number
        // todo check if repo ids for pages interferes commit's loaders ids
    }

    @Override
    public void onLoadFailure(Exception ex) {
        Log.e("LOAD", ex.getMessage());
        List page = App.getDaoInstance().findCommitsAtPage(mCurrentPage - 1, mRepoName);
        attachDataToAdapter(page);
    }

    @Override
    public void onLoadSuccess(List<Commit> result) {
        attachDataToAdapter(result);
    }

    private void attachDataToAdapter(List<Commit> result) {
        if (mRecyclerAdapter == null) {
            initRecyclerView(result);
        } else {
            mRecyclerAdapter.attachData(result);
        }
    }

    private void initRecyclerView(List<Commit> commits) {
        mRecyclerAdapter = new CommitsRecyclerViewAdapter(commits, this);
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


    private static class CommitsLoader extends RetrofitLoader<List<Commit>, GithubApiService> {
        private int page;
        private String accessToken;
        private String owner;
        private String repoName;

        public CommitsLoader(Context context, GithubApiService service, String owner, int page, String repoName) {
            super(context, service);
            this.owner = owner;
            this.accessToken = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
                    .getString(ACCESS_TOKEN_KEY, "");
            this.page = page;
            this.repoName = repoName;
        }

        @Override
        public List<Commit> call(GithubApiService service) {
            return service.getRepositoryCommit(owner, repoName, accessToken, page);
        }

        @Override
        public Class getEntityClass() {
            return Commit.class;
        }

        @Override
        public Integer getPageNumber() {
            return page;
        }
    }
}
