package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarHandler;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.ComplaintsAdapter;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.store.DummyData;

import java.util.ArrayList;

/**
 * Created by hungh on 3/5/2017.
 */

public class MyComplaintsFragment extends BaseAppFragment implements ActionbarInfo, ActionbarHandler {

    private RecyclerView mListComplaints;
    private ArrayList<Complaint> mComplaints;

    public static MyComplaintsFragment newInstance() {

        Bundle args = new Bundle();

        MyComplaintsFragment fragment = new MyComplaintsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mComplaints = DummyData.createComplaints();
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
        ComplaintsAdapter adapter = new ComplaintsAdapter(getContext(), mComplaints);
        mListComplaints.setAdapter(adapter);
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
        return "My Complaints";
    }
}
