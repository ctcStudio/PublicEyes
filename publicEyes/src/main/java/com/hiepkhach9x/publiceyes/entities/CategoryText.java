package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hungh on 3/4/2017.
 */

public class CategoryText implements Parcelable{
    @SerializedName("category_id")
    private int id;
    @SerializedName("name")
    private String categoryName;
    private String description;

    public CategoryText() {
    }

    public CategoryText(int id, String categoryName,String description) {
        this.id = id;
        this.categoryName = categoryName;
        this.description = description;
    }


    protected CategoryText(Parcel in) {
        id = in.readInt();
        categoryName = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(categoryName);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryText> CREATOR = new Creator<CategoryText>() {
        @Override
        public CategoryText createFromParcel(Parcel in) {
            return new CategoryText(in);
        }

        @Override
        public CategoryText[] newArray(int size) {
            return new CategoryText[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
