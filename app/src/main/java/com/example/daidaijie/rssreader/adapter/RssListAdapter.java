package com.example.daidaijie.rssreader.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.daidaijie.rssreader.R;
import com.example.daidaijie.rssreader.bean.RssSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by daidaijie on 2016/9/8.
 */
public class RssListAdapter extends RecyclerView.Adapter<RssListAdapter.ViewHolder> {


    private Activity mActivity;
    private List<RssSource> mRssSources;

    public RssListAdapter(Activity activity, List<RssSource> RSSItems) {
        mActivity = activity;
        mRssSources = RSSItems;
    }

    public List<RssSource> getRssSources() {
        return mRssSources;
    }

    public void setRssSources(List<RssSource> rssSources) {
        mRssSources = rssSources;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_rss_list, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RssSource rssSource = mRssSources.get(position);
        holder.mRssTitleTextView.setText(rssSource.getType_name());
    }

    @Override
    public int getItemCount() {
        if (mRssSources == null) {
            return 0;
        }
        return mRssSources.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.rssTitleTextView)
        TextView mRssTitleTextView;
        @BindView(R.id.rssButton)
        Button mRssButton;
        @BindView(R.id.rssCardItem)
        CardView mRssCardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
