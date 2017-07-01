package com.hiepkhach9x.publiceyes.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hiepkhach9x.base.ImageUtil;
import com.hiepkhach9x.publiceyes.R;
import com.hiepkhach9x.publiceyes.entities.Category;

import java.util.ArrayList;

/**
 * Created by hungh on 7/1/2017.
 */

public class CategoryAdapter extends ArrayAdapter<Category> {

    private ArrayList<Category> categories;
    private LayoutInflater inflater;

    public CategoryAdapter(Context context, ArrayList<Category> categories) {
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

    private class ViewHolder {
        ImageView icon;
        TextView name;
    }
}