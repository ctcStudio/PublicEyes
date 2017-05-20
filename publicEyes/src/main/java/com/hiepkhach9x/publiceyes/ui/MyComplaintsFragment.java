package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.ComplaintsAdapter;
import com.hiepkhach9x.publiceyes.api.request.GetListReportRequest;
import com.hiepkhach9x.publiceyes.api.response.GetListReportResponse;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import java.util.ArrayList;

/**
 * Created by hungh on 3/5/2017.
 */

public class MyComplaintsFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler,
        ResponseListener {

    private static final int REQUEST_GET_COMPLAINTS = 107;
    private static final String ARGS_COMPLAINTS = "args.complaint";
    private RecyclerView mListComplaints;
    private ArrayList<Complaint> mComplaints;
    private ComplaintsAdapter adapter;

    public static MyComplaintsFragment newInstance() {

        Bundle args = new Bundle();

        MyComplaintsFragment fragment = new MyComplaintsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mComplaints = savedInstanceState.getParcelableArrayList(ARGS_COMPLAINTS);
        }

        if (mComplaints == null) {
            mComplaints = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARGS_COMPLAINTS, mComplaints);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_my_complaints;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mListComplaints = (RecyclerView) view.findViewById(R.id.list_complaint);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mListComplaints.setLayoutManager(layoutManager);
        adapter = new ComplaintsAdapter(getContext(), mComplaints);
        mListComplaints.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mComplaints.isEmpty()) {
            getListReport();
        }
    }

    @Override
    public boolean onLeftHandled() {
        if (mNavigationManager != null && mNavigationManager.isBackStackEmpty()) {
            mNavigationManager.replaceAll(new HomeFragment());
            return true;
        }
        return false;
    }

    @Override
    public boolean onRightHandled() {
        return false;
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.my_complaints);
    }

    private void getListReport() {
        GetListReportRequest getListReportRequest = new GetListReportRequest();
        showApiLoading();
        mApi.startRequest(REQUEST_GET_COMPLAINTS, getListReportRequest, this);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_GET_COMPLAINTS) {
            return new GetListReportResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_GET_COMPLAINTS) {
            GetListReportResponse getListReportResponse = (GetListReportResponse) response;
            if (getListReportResponse.getComplaints() != null) {
                mComplaints.addAll(getListReportResponse.getComplaints());
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onError(int requestId, Exception e) {
        dismissApiLoading();
        AppAlertDialog.errorApiAlertDialogOk(getContext(), e, null);
    }
}
