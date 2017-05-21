package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ListView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.NewsAdapter;
import com.hiepkhach9x.publiceyes.api.request.GetCampaignsRequest;
import com.hiepkhach9x.publiceyes.api.request.GetListReportRequest;
import com.hiepkhach9x.publiceyes.api.response.GetCampaignsResponse;
import com.hiepkhach9x.publiceyes.entities.News;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import java.util.ArrayList;

/**
 * Created by HungHN on 5/18/17.
 */

public class CampaignFragment extends BaseAppFragment implements ActionbarInfo, ResponseListener {

    private static final int REQUEST_GET_CAMPAIGN = 108;
    private static final String ARGS_NEWS = "args.news";

    @Override
    public String getActionbarTitle() {
        return getString(R.string.compaign);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_compaign;
    }

    public static CampaignFragment newInstance() {

        Bundle args = new Bundle();

        CampaignFragment fragment = new CampaignFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private ArrayList<News> listNews;
    private NewsAdapter newsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            listNews = savedInstanceState.getParcelableArrayList(ARGS_NEWS);
        }
        if (listNews == null) {
            listNews = new ArrayList<>();
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView list = (ListView) view.findViewById(R.id.list);
        newsAdapter = new NewsAdapter(getContext(), listNews);
        list.setAdapter(newsAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (listNews.isEmpty()) {
            getListCompaign();
        }
    }

    private void getListCompaign() {
        GetCampaignsRequest getCampaignsRequest = new GetCampaignsRequest();
        showApiLoading();
        mApi.startRequest(REQUEST_GET_CAMPAIGN, getCampaignsRequest, this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_GET_CAMPAIGN) {
            return new GetCampaignsResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_GET_CAMPAIGN) {
            GetCampaignsResponse getCampaignsResponse = (GetCampaignsResponse) response;
            if (getCampaignsResponse.getNewses() != null) {
                listNews.clear();
                listNews.addAll(getCampaignsResponse.getNewses());
                newsAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AlertDialog dialog = AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }
}
