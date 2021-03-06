package com.github.app.UI;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.afollestad.materialdialogs.MaterialDialog;
import com.github.app.R;
import com.github.app.model.Commit;
import com.github.app.model.CommitAdapterItem;
import com.github.app.model.CommitsTitle;
import com.github.app.util.Utils;

import java.text.SimpleDateFormat;
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


    public CommitsRecyclerViewAdapter(List<Commit> commitItems, Activity activity) {
        mCommits = Commit.toAdapterItems(commitItems);
        mContext = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case COMMIT_VIEW_TYPE:
                View v = LayoutInflater.from(mContext).inflate(R.layout.commits_list_raw, parent, false);
                return new CommitViewHolder(v);
            case DATE_TITLE_VIEW_TYPE:
                View view = LayoutInflater.from(mContext).inflate(R.layout.commits_date_title, parent, false);
                return new DateViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid holder and object bind");
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int pos) {
        if (holder instanceof CommitViewHolder) {
            ((CommitViewHolder) holder).fromCommit((Commit) mCommits.get(pos));
        } else if (holder instanceof DateViewHolder) {
            ((DateViewHolder) holder).fill((CommitsTitle) mCommits.get(pos));
        } else {
            throw new IllegalArgumentException("Invalid holder and object bind");
        }
    }

    @Override
    public int getItemCount() {
        return mCommits.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mCommits.get(position) instanceof Commit ? COMMIT_VIEW_TYPE : DATE_TITLE_VIEW_TYPE;
    }

    public void attachData(List<Commit> items) {
        if (items == null || items.isEmpty()) return;

        List<CommitAdapterItem> adapterItems = Commit.toAdapterItems(items);
        addExcludingSameTitles(mCommits, adapterItems);
        notifyDataSetChanged();
    }

    private void addExcludingSameTitles(List<CommitAdapterItem> mCommits, List<CommitAdapterItem> items) {
        for (CommitAdapterItem item : items) {
            if (!(item instanceof CommitsTitle && mCommits.contains(item))) {
                mCommits.add(item);
            }
        }
    }

    static class CommitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        @InjectView(R.id.commit_message_tv)
        TextView messageTv;
        @InjectView(R.id.commit_author_tv)
        TextView authorTv;
        @InjectView(R.id.commit_time_tv)
        TextView timeTv;

        private Commit mCommit;

        public CommitViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
            itemView.setOnClickListener(this);
        }

        public void fromCommit(Commit commit) {
            messageTv.setText(commit.getMessage());
            authorTv.setText(commit.getAuthor());
            timeTv.setText(sdf.format(commit.getDate()));
            mCommit = commit;
        }

        @Override
        public void onClick(View view) {
            final Context context = messageTv.getContext();
            new MaterialDialog.Builder(context)
                    .title("SHA")
                    .content(mCommit.getSha())
                    .positiveText("Visit webpage")
                    .negativeText("Copy SHA")
                    .callback(new MaterialDialog.ButtonCallback() {
                        @Override
                        public void onPositive(MaterialDialog dialog) {
                            Intent intent = new Intent(Intent.ACTION_VIEW)
                                    .setData(Uri.parse(mCommit.getHtmlUrl()));
                            context.startActivity(intent);
                        }

                        @Override
                        public void onNegative(MaterialDialog dialog) {
                            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                            clipboard.setPrimaryClip(ClipData.newPlainText("asdsad", mCommit.getSha()));
                            Utils.notifyWithMessage(context, "Copied: " + mCommit.getSha());
                        }
                    })
                    .show();
        }
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        private static SimpleDateFormat sdf = new SimpleDateFormat("d MMMM");
        @InjectView(R.id.commits_title_tv)
        TextView titleTv;

        public DateViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void fill(CommitsTitle title) {
            titleTv.setText(sdf.format(title.getDateTitle()));
        }
    }


}
