package com.github.app.UI;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import com.github.app.R;
import com.github.app.model.Commit;
import com.github.app.model.CommitAdapterItem;

import java.util.List;

public class CommitsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int COMMIT_VIEW_TYPE = 0;
    private static final int DATE_TITLE_VIEW_TYPE = 1;


    /**
     * items displayed in adapter.
     * CommitAdapterItem hides Commit which stands for view with commit message and CommitsTitle with commits' date title representation
     */
    private List<CommitAdapterItem> mCommits;


    public CommitsRecyclerViewAdapter(List<CommitAdapterItem> commitItems, Activity activity) {
        mCommits = commitItems;
        mContext = activity;
    }

    @Override
    public CommitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMMIT_VIEW_TYPE:
                View v = LayoutInflater.from(mContext).inflate(R.layout.commits_list_raw, parent, false);
                return new CommitViewHolder(v);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    public void attachData(List<CommitAdapterItem> items) {
        mCommits.addAll(items);
        //todo clear from duplicates
    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCommits.get(position) instanceof Commit ? COMMIT_VIEW_TYPE : DATE_TITLE_VIEW_TYPE;
    }

    class CommitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public CommitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {

        }
    }

    class DateViewHolder extends RecyclerView.ViewHolder {

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }


}
