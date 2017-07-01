package com.hiepkhach9x.publiceyes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;
import com.hiepkhach9x.publiceyes.entities.CategoryText;

import java.util.ArrayList;

/**
 * Created by hungh on 7/1/2017.
 */

public class CategoryTextAdapter extends ArrayAdapter<Category> {

    private ArrayList<CategoryText> categories;
    private LayoutInflater inflater;

    public CategoryTextAdapter(Context context, ArrayList<CategoryText> categories) {
        super(context, 0);
        this.categories = categories;
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
            convertView = inflater.inflate(R.layout.item_category_text, parent, false);
            holder = new ViewHolder();
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.name = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        CategoryText category = categories.get(position);
        holder.name.setText(category.getCategoryName());
        holder.description.setText(getContext().getString(R.string.penance,category.getDescription()));

        return convertView;
    }

    private class ViewHolder {
        TextView description;
        TextView name;
    }
}