package com.hiepkhach9x.publiceyes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.ImageUtil;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.api.request.GetListCategoryRequest;
import com.hiepkhach9x.publiceyes.api.response.GetListCategoryResponse;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.store.DummyData;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import java.util.ArrayList;

import co.core.imageloader.NDisplayOptions;

/**
 * Created by hungh on 3/4/2017.
 */

public class CategoryFragment extends BaseAppFragment implements ActionbarInfo, ResponseListener {

    private static final String ARGS_IMAGE_URL = "args.image.url";
    private static final String ARGS_DESCRIPTION = "args.description";
    private static final String ARGS_CATEGORIES = "args.categories";

    private static final int REQUEST_GET_CATEGORIES = 104;
    private ArrayList<Category> categories;
    private CategoryAdapter categoryAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    public static CategoryFragment newInstance(String filePath, String description) {

        Bundle args = new Bundle();
        args.putString(ARGS_IMAGE_URL, filePath);
        args.putString(ARGS_DESCRIPTION, description);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String imageUrl;
    private String description;

    private void parseBundle(Bundle bundle) {
        imageUrl = bundle.getString(ARGS_IMAGE_URL);
        description = bundle.getString(ARGS_DESCRIPTION);
        categories = bundle.getParcelableArrayList(ARGS_CATEGORIES);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parseBundle(savedInstanceState);
        } else if (getArguments() != null) {
            parseBundle(getArguments());
        }
        if (categories == null) {
            categories = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(ARGS_IMAGE_URL, imageUrl);
        outState.putString(ARGS_DESCRIPTION, description);
        outState.putParcelableArrayList(ARGS_CATEGORIES, categories);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView categoryList = (ListView) view.findViewById(R.id.list_category);
        categoryAdapter = new CategoryAdapter(getContext());
        categoryList.setAdapter(categoryAdapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = categories.get(i);
                if (mNavigationManager != null) {
                    Complaint complaint = new Complaint();
                    complaint.setCategoryId(category.getId());
                    complaint.setImageThumb(imageUrl);
                    complaint.setDescription(description);
                    complaint.setUserName(UserPref.get().getEmail());
                    mNavigationManager.showPage(LocationFragment.newInstance(complaint));
                }
            }
        });
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (categories.isEmpty()) {
            getListCategory();
        }
    }

    private void getListCategory() {
        GetListCategoryRequest getListCategoryRequest = new GetListCategoryRequest();
        showApiLoading();
        mApi.restartRequest(REQUEST_GET_CATEGORIES, getListCategoryRequest, this);
    }

    @Override
    public String getActionbarTitle() {
        return getString(R.string.category);
    }

    @Override
    public BaseResponse parse(int requestId, String response) throws Exception {
        if (requestId == REQUEST_GET_CATEGORIES) {
            return new GetListCategoryResponse(response);
        }
        return null;
    }

    @Override
    public void onResponse(int requestId, BaseResponse response) {
        dismissApiLoading();
        if (requestId == REQUEST_GET_CATEGORIES) {
            GetListCategoryResponse getListCategoryResponse = (GetListCategoryResponse) response;
            ArrayList<Category> List = getListCategoryResponse.getCategories();
//            ArrayList<Category> List = DummyData.createCategories(getContext());
            if (List != null) {
                categories.clear();
                categories.addAll(List);
                categoryAdapter.notifyDataSetChanged();
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

    private class CategoryAdapter extends ArrayAdapter<Category> {

        LayoutInflater inflater;

        CategoryAdapter(Context context) {
            super(context, 0);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return categories.size();
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.item_category, parent, false);
                holder = new ViewHolder();
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                holder.name = (TextView) convertView.findViewById(R.id.name);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Category category = categories.get(position);
            if (category.getCategoryIcon() != 0) {
                holder.icon.setImageResource(category.getCategoryIcon());
            } else {
                ImageUtil.loadImage(getContext(), category.getAvatar(), holder.icon);
            }
            holder.name.setText(category.getCategoryName());

            return convertView;
        }
    }

    private class ViewHolder {
        ImageView icon;
        TextView name;
    }
}
