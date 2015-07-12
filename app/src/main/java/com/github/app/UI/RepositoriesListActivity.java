package com.github.app.UI;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.app.App;
import com.github.app.R;
import com.github.app.model.Repository;
import com.github.app.networking.LoaderCallback;
import com.github.app.networking.GithubApiService;
import com.github.app.networking.RetrofitLoader;
import com.github.app.networking.RetrofitLoaderManager;

import java.util.List;

public class RepositoriesListActivity extends AppCompatActivity implements LoaderCallback<List<Repository>> {

    @InjectView(R.id.repositories_recycler_view)
    RecyclerView mRecyclerView;

    private RepositoryRecyclerViewAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repositories_list);
        ButterKnife.inject(this);

//        initRecyclerView();

        RepositoriesLoader loader = new RepositoriesLoader(this, App.getApiService());
        RetrofitLoaderManager.init(getLoaderManager(), 0, loader, this);
    }

    private void initRecyclerView(List<Repository> repositories) {
        mRecyclerAdapter = new RepositoryRecyclerViewAdapter(repositories, this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadFailure(Exception ex) {

    }

    @Override
    public void onLoadSuccess(List<Repository> result) {
        initRecyclerView(result);
    }


    private static class RepositoriesLoader extends RetrofitLoader<List<Repository>, GithubApiService> {
        public RepositoriesLoader(Context context, GithubApiService service) {
            super(context, service);
        }

        @Override
        public List<Repository> call(GithubApiService service) {
            return service.getRepositoriesList("jakewharton");
        }
    }
}
