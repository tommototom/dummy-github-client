package com.github.app.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import butterknife.InjectView;
import com.github.app.App;
import com.github.app.R;
import com.github.app.model.Commit;
import com.github.app.model.CommitAdapterItem;
import com.github.app.networking.GithubApiService;
import com.github.app.networking.LoaderCallback;
import com.github.app.networking.RetrofitLoader;
import com.github.app.networking.RetrofitLoaderManager;
import com.github.app.util.EndlessRecyclerOnScrollListener;

import java.util.List;

public class CommitsListActivity extends AppCompatActivity implements LoaderCallback<List<Commit>> {

    @InjectView(R.id.commits_recycler_view)
    RecyclerView mRecyclerView;

    private int mCurrentPage = 1;
    private CommitsRecyclerViewAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commits_list);
    }

    private void runNewLoadDataTask() {
        CommitsLoader loader = new CommitsLoader(mCurrentPage++, this, App.getApiService());
        RetrofitLoaderManager.init(getLoaderManager(), mCurrentPage, loader, this); // provide loader id the same as page number
        // todo check if repo ids for pages interferes commit's loaders ids
    }

    @Override
    public void onLoadFailure(Exception ex) {
        Log.e("LOAD", ex.getMessage());
//        Toast.makeText()
        List page = App.getDaoInstance().findPage(Commit.class, mCurrentPage - 1);
        attachDataToAdapter(page);
    }

    @Override
    public void onLoadSuccess(List<Commit> result) {
        attachDataToAdapter(result);
    }

    private void attachDataToAdapter(List<Commit> result) {

    }

    private void initRecyclerView(List<Commit> commits) {
        List<CommitAdapterItem> items = Commit.toAdapterItems(commits);
        mRecyclerAdapter = new CommitsRecyclerViewAdapter(items, this);
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

        public CommitsLoader(int page, Context context, GithubApiService service) {
            super(context, service);
            this.page = page;
        }

        @Override
        public List<Commit> call(GithubApiService service) {
            return service.getRepositoryCommit("jakewharton", "kotterknife", page);
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
