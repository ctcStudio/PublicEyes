package com.hiepkhach9x.publiceyes.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.base.api.BaseResponse;
import com.hiepkhach9x.base.api.ResponseListener;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.adapter.CategoryTextAdapter;
import com.hiepkhach9x.publiceyes.api.request.GetListCategoryRequest;
import com.hiepkhach9x.publiceyes.api.response.GetListCategoryResponse;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.CategoryText;
import com.hiepkhach9x.publiceyes.entities.Complaint;
import com.hiepkhach9x.publiceyes.store.CategoryCar;
import com.hiepkhach9x.publiceyes.store.CategoryMoto;
import com.hiepkhach9x.publiceyes.store.UserPref;
import com.hiepkhach9x.publiceyes.ui.dialog.AppAlertDialog;

import java.util.ArrayList;

/**
 * Created by hungh on 3/4/2017.
 */

public class CategoryTextFragment extends BaseAppFragment implements ActionbarInfo, ResponseListener {
    public static final int TYPE_CAR = 0;
    public static final int TYPE_MOTO = 1;
    private static final String ARGS_TYPE = "args.type";
    private static final String ARGS_IMAGE_URL = "args.image.url";
    private static final String ARGS_DESCRIPTION = "args.description";
    private static final String ARGS_CATEGORIES = "args.categories";
    private static final String ARGS_CATEGORIES_SERVER = "args.categories.server";

    private static final int REQUEST_GET_CATEGORIES = 104;
    private ArrayList<CategoryText> categories;
    private ArrayList<Category> categoriesSever;
    private CategoryTextAdapter categoryAdapter;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    public static CategoryTextFragment newInstance(int type, String filePath, String description) {

        Bundle args = new Bundle();
        args.putInt(ARGS_TYPE, type);
        args.putString(ARGS_IMAGE_URL, filePath);
        args.putString(ARGS_DESCRIPTION, description);
        CategoryTextFragment fragment = new CategoryTextFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private int type;
    private String imageUrl;
    private String description;

    private void parseBundle(Bundle bundle) {
        type = bundle.getInt(ARGS_TYPE);
        imageUrl = bundle.getString(ARGS_IMAGE_URL);
        description = bundle.getString(ARGS_DESCRIPTION);
        categories = bundle.getParcelableArrayList(ARGS_CATEGORIES);
        categoriesSever = bundle.getParcelableArrayList(ARGS_CATEGORIES_SERVER);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            parseBundle(savedInstanceState);
        } else if (getArguments() != null) {
            parseBundle(getArguments());
        }
        switch (type) {
            case TYPE_MOTO:
                categories = CategoryMoto.getCategoriesMoto();
                break;
            case TYPE_CAR:
                categories = CategoryCar.getCategoriesCar();
                break;
            default:
                categories = new ArrayList<>();
        }

        if(categoriesSever == null) {
            categoriesSever = new ArrayList<>();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ARGS_TYPE,type);
        outState.putString(ARGS_IMAGE_URL, imageUrl);
        outState.putString(ARGS_DESCRIPTION, description);
        outState.putParcelableArrayList(ARGS_CATEGORIES, categories);
        outState.putParcelableArrayList(ARGS_CATEGORIES_SERVER, categoriesSever);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView categoryList = (ListView) view.findViewById(R.id.list_category);
        categoryAdapter = new CategoryTextAdapter(getContext(), categories);
        categoryList.setAdapter(categoryAdapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i > categoriesSever.size()) {
                    i = categoriesSever.size() - 1;
                }
                Category category = categoriesSever.get(i);
                if (mNavigationManager != null) {
                    Complaint complaint = new Complaint();
                    complaint.setCategoryId(category.getId());
                    complaint.setCategoryName(category.getCategoryName());
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
        if (categoriesSever.isEmpty()) {
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
                categoriesSever.clear();
                categoriesSever.addAll(List);
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
