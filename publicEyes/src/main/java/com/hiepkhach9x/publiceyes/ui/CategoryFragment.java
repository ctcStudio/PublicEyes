package com.hiepkhach9x.publiceyes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hiepkhach9x.base.BaseAppFragment;
import com.hiepkhach9x.base.actionbar.ActionbarInfo;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.store.DummyData;

import java.util.ArrayList;

/**
 * Created by hungh on 3/4/2017.
 */

public class CategoryFragment extends BaseAppFragment implements ActionbarInfo {

    private ArrayList<Category> categories;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_category;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = DummyData.createCategories();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView categoryList = (ListView) view.findViewById(R.id.list_category);
        CategoryAdapter categoryAdapter = new CategoryAdapter(getContext());
        categoryList.setAdapter(categoryAdapter);

        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mNavigationManager != null) {
                    mNavigationManager.showPage(new LocationFragment());
                }
            }
        });
    }

    @Override
    public String getActionbarTitle() {
        return "Category";
    }

    private class CategoryAdapter extends ArrayAdapter<Category> {

        LayoutInflater inflater;

        public CategoryAdapter(Context context) {
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
            holder.icon.setImageResource(category.getCategoryIcon());
            holder.name.setText(category.getCategoryName());

            return convertView;
        }
    }

    static class ViewHolder {
        ImageView icon;
        TextView name;
    }
}
