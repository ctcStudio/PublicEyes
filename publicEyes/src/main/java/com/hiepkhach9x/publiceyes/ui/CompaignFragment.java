package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.NewsAdapter;
import com.hiepkhach9x.publiceyes.entities.News;
import com.hiepkhach9x.publiceyes.store.DummyData;

import java.util.ArrayList;

/**
 * Created by HungHN on 5/18/17.
 */

public class CompaignFragment extends BaseAppFragment implements ActionbarInfo {

    @Override
    public String getActionbarTitle() {
        return getString(R.string.compaign);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_compaign;
    }

    public static CompaignFragment newInstance() {
        
        Bundle args = new Bundle();
        
        CompaignFragment fragment = new CompaignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<News> listNews;
    private NewsAdapter newsAdapter;
    private ListView list;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        list = (ListView) view.findViewById(R.id.list);
        if(listNews == null) {
            listNews = DummyData.dummyNews();
        }
        newsAdapter = new NewsAdapter(getContext(),listNews);
        list.setAdapter(newsAdapter);
    }
}
