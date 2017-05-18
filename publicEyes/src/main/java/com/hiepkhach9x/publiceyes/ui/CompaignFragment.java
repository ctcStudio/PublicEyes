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
            listNews = dummyNews();
        }
        newsAdapter = new NewsAdapter(getContext(),listNews);
        list.setAdapter(newsAdapter);
    }

    String htmlContent = "<h2>Welcome to the HTML editor!</h2>\n" +
            "<p>Just type the HTML and it will be shown below.</p>\n" +
            "\n" +
            "<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.\n" +
            "\n" +
            "<h2>Heading in h2, som more sample text</h2>\n" +
            "\n" +
            "<p>Phasellus sem odio, varius quis, cursus at, ullamcorper eget, turpis. Maecenas a mi.</p>\n" +
            "\n" +
            "<ul>\n" +
            "\t<li>Nulla facilisi.</li>\n" +
            "\t<li>Pellentesque habitant morbi</li>\n" +
            "\t<li>Quisque vel justo.</li>\n" +
            "\t<li>Nullam posuere purus sed arcu.</li>\n" +
            "</ul>";

    private ArrayList<News> dummyNews() {
        ArrayList<News> newses = new ArrayList<>();
        for (int i = 0; i <= 5; i++) {
            News news = new News();
            if (i == 3) {
                news.setBanner("");
                news.setTitle("Title " + i);
            } else {
                news.setBanner("http://wass.edu.vn/wp-content/uploads/2015/09/Banner-WAPS-huong-ung-thang-an-toan-giao-thong-T.Viet-Copy.jpg");
                news.setTitle("Title " + i);
            }
            news.setFromDate("20160405143640");
            news.setToDate("20160406143640");
            news.setHtmlContent(htmlContent);
            newses.add(news);
        }
        return newses;
    }
}
