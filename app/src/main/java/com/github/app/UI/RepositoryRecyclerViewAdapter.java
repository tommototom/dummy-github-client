package com.github.app.UI;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.github.app.R;
import com.github.app.model.Repository;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RepositoryRecyclerViewAdapter extends RecyclerView.Adapter<RepositoryRecyclerViewAdapter.RepoViewHolder> {

    private List<Repository> mRepositories;
    private Context mContext;

    public RepositoryRecyclerViewAdapter(List<Repository> mRepositories, Context c) {
        this.mRepositories = mRepositories;
        this.mContext = c;
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.repository_list_raw, viewGroup, false);
        return new RepoViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mRepositories.size();
    }

    @Override
    public void onBindViewHolder(RepoViewHolder repoViewHolder, int i) {
        Repository repository = mRepositories.get(i);
        repoViewHolder.fillViews(repository);
    }

    public List<Repository> getmRepositories() {
        return mRepositories;
    }

    public void attachLoadedData(List<Repository> result) {
        mRepositories.addAll(result);
        notifyDataSetChanged();
    }


    class RepoViewHolder extends RecyclerView.ViewHolder{
        @InjectView(R.id.repo_name_text_view)
        TextView repoNameTv;
        @InjectView(R.id.repo_author_image_view)
        ImageView authorImage;
        @InjectView(R.id.repo_author_name)
        TextView authorNameTv;
        @InjectView(R.id.repo_description)
        TextView descriptionTv;
        @InjectView(R.id.forks_count_text_view)
        TextView forksCountTv;
        @InjectView(R.id.watches_count_text_view)
        TextView watchesCountTv;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        void fillViews(Repository r) {
            repoNameTv.setText(r.getName());
            authorNameTv.setText(r.getOwner().getLogin());
            descriptionTv.setText(r.getDescription());
            forksCountTv.setText(String.valueOf(r.getForksCount()));
            watchesCountTv.setText(String.valueOf(r.getWatchersCount()));

            Picasso.with(mContext)
                    .load(r.getOwner().getAvatarUrl())
//                    .placeholder(R.drawable.ic_launcher)
                    .fit().into(authorImage);
        }
    }
}

