package com.example.daidaijie.rssreader.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.rssreader.R;
import com.example.daidaijie.rssreader.RssDetailActivity;

import org.mcsoxford.rss.RSSItem;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by daidaijie on 2016/9/8.
 */
public class RssAdapter extends RecyclerView.Adapter<RssAdapter.ViewHolder> {


    private Activity mActivity;
    private List<RSSItem> mRSSItems;

    public RssAdapter(Activity activity, List<RSSItem> RSSItems) {
        mActivity = activity;
        mRSSItems = RSSItems;
    }

    public List<RSSItem> getRSSItems() {
        return mRSSItems;
    }

    public void setRSSItems(List<RSSItem> RSSItems) {
        mRSSItems = RSSItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        View view = inflater.inflate(R.layout.item_rss, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final RSSItem rssItem = mRSSItems.get(position);
        holder.mRssTitleTextView.setText(rssItem.getTitle().trim());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");

        holder.mRssTimeTextView.setText(formatter.format(rssItem.getPubDate()));

        holder.mRssCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RssDetailActivity.getIntent(mActivity, position);
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mRSSItems == null) {
            return 0;
        }
        return mRSSItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rssTitleTextView)
        TextView mRssTitleTextView;
        @BindView(R.id.rssTimeTextView)
        TextView mRssTimeTextView;
        @BindView(R.id.rssCardItem)
        CardView mRssCardItem;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
