package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hungh on 3/4/2017.
 */

public class Category implements Parcelable{
    @SerializedName("category_id")
    private int id;
    @SerializedName("path")
    private int categoryIcon;
    @SerializedName("name")
    private String categoryName;

    public Category() {
    }

    public Category(int id, int categoryIcon, String categoryName) {
        this.id = id;
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
    }

    protected Category(Parcel in) {
        id = in.readInt();
        categoryIcon = in.readInt();
        categoryName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(categoryIcon);
        dest.writeString(categoryName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
