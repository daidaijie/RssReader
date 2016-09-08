package com.example.daidaijie.rssreader.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.daidaijie.rssreader.FavDetailActivity;
import com.example.daidaijie.rssreader.R;
import com.example.daidaijie.rssreader.RssDetailActivity;
import com.example.daidaijie.rssreader.bean.SimpleRssItem;

import org.mcsoxford.rss.RSSItem;

import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by daidaijie on 2016/9/8.
 */
public class FavRssAdapter extends RecyclerView.Adapter<FavRssAdapter.ViewHolder> {


    private Activity mActivity;
    private List<SimpleRssItem> mRSSItems;

    public FavRssAdapter(Activity activity, List<SimpleRssItem> RSSItems) {
        mActivity = activity;
        mRSSItems = RSSItems;
    }

    public interface OnLongClickListener {
        void onLongClick(int position);
    }

    private OnLongClickListener mOnLongClickListener;

    public OnLongClickListener getOnLongClickListener() {
        return mOnLongClickListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        mOnLongClickListener = onLongClickListener;
    }

    public List<SimpleRssItem> getRSSItems() {
        return mRSSItems;
    }

    public void setRSSItems(List<SimpleRssItem> RSSItems) {
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
        final SimpleRssItem rssItem = mRSSItems.get(position);
        holder.mRssTitleTextView.setText(rssItem.getTilte().trim());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");

        holder.mRssTimeTextView.setText(formatter.format(rssItem.getPubDate()));

        holder.mRssCardItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = FavDetailActivity.getIntent(mActivity, rssItem);
                mActivity.startActivity(intent);
            }
        });
        holder.mRssCardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnLongClickListener.onLongClick(position);
                return true;
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
