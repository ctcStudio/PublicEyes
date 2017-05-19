package com.hiepkhach9x.publiceyes.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiepkhach9x.base.ImageUtil;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.News;
import com.hiepkhach9x.publiceyes.view.AutoFitTextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsAdapter extends BaseAdapter {

    private ArrayList<News> mNewsList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private Set<Integer> mDetailList;

    public NewsAdapter(Context context, ArrayList<News> newsList) {
        this.mContext = context;
        this.mNewsList = newsList;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDetailList = new HashSet<>();
    }

    public void addAll(List<News> newsList) {
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void refresh(List<News> newsList) {
        mNewsList.clear();
        mDetailList.clear();
        mNewsList.addAll(newsList);
        notifyDataSetChanged();
    }

    public void clearAll() {
        mNewsList.clear();
        mDetailList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mNewsList == null ? 0 : mNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return mNewsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        final News news = mNewsList.get(position);
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_news, parent, false);
            holder = new ViewHolder(convertView);
            // setting webview
            holder.webView.getSettings().setJavaScriptEnabled(true);
            holder.webView.getSettings().setBuiltInZoomControls(true);
            holder.webView.getSettings().setDisplayZoomControls(false);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (news.hasBanner()) {
            holder.banner.setVisibility(View.VISIBLE);
            holder.newsLayoutTitle.setVisibility(View.VISIBLE);
        } else {
            holder.banner.setVisibility(View.GONE);
            holder.newsLayoutTitle.setVisibility(View.VISIBLE);
        }

        holder.title.setText(news.getTitle());
        holder.deadLine.setText(news.getDeadline());

        holder.webView.loadData(news.getHtmlContent(), "text/html; charset=utf-8", null);

        if (mDetailList.contains(position)) {
            holder.setExpandContent();
        } else {
            holder.setCollapseContent();
        }
        ImageUtil.loadBannerImage(mContext,news.getBanner(),holder.banner);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDetailList.contains(position)) {
                    holder.setCollapseContent();
                    if (news.hasBanner()) {
                        holder.banner.setVisibility(View.VISIBLE);
                        holder.newsLayoutTitle.setVisibility(View.VISIBLE);
                    } else {
                        holder.banner.setVisibility(View.GONE);
                        holder.newsLayoutTitle.setVisibility(View.VISIBLE);
                    }
                    mDetailList.remove(position);
                } else {
                    holder.setExpandContent();
                    mDetailList.add(position);
                }
            }
        });
        return convertView;
    }


    static class ViewHolder {
        ImageView banner;
        TextView title;
        LinearLayout newsLayoutTitle;
        AutoFitTextView deadLine;
        WebView webView;
        TextView detail;

        public ViewHolder(View itemView) {
            banner = (ImageView) itemView.findViewById(R.id.news_banner);
            newsLayoutTitle = (LinearLayout) itemView.findViewById(R.id.news_layout_title);
            title = (TextView) itemView.findViewById(R.id.news_title);
            deadLine = (AutoFitTextView) itemView.findViewById(R.id.news_date);
            webView = (WebView) itemView.findViewById(R.id.news_webview);
            detail = (TextView) itemView.findViewById(R.id.news_detail);
        }

        public void setExpandContent() {
            webView.setVisibility(View.VISIBLE);
            newsLayoutTitle.setVisibility(View.VISIBLE);
            detail.setText(R.string.new_close_up);
        }

        public void setCollapseContent() {
            webView.setVisibility(View.GONE);
            detail.setText(R.string.new_detail);
        }
    }

}

