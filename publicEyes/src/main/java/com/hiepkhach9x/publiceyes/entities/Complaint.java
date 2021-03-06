package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;
import com.hiepkhach9x.publiceyes.Constants;
import com.hiepkhach9x.publiceyes.api.ApiConfig;

import co.utilities.DateUtils;

/**
 * Created by hungh on 3/5/2017.
 */

public class Complaint implements Parcelable {
    @SerializedName("violation_id")
    private int violationId;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("path")
    private String imageThumb;
    @SerializedName("location")
    private String location;
    @SerializedName("address")
    private String address;
    @SerializedName("district")
    private String district;
    @SerializedName("province")
    private String province;
    @SerializedName("operation_id")
    private int operationId;
    @SerializedName("email")
    private String userName;
    @SerializedName("create_date")
    private String time; // 2017-05-04T15:38:43.557
    @SerializedName("description")
    private String description;
    @SerializedName("isVideo")
    private boolean isVideo;
    @SerializedName("isImage")
    private boolean isPhoto;

    public Complaint() {
    }


    protected Complaint(Parcel in) {
        violationId = in.readInt();
        categoryId = in.readInt();
        categoryName = in.readString();
        imageThumb = in.readString();
        location = in.readString();
        address = in.readString();
        district = in.readString();
        province = in.readString();
        operationId = in.readInt();
        userName = in.readString();
        time = in.readString();
        description = in.readString();
        isVideo = in.readByte() != 0;
        isPhoto = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(violationId);
        dest.writeInt(categoryId);
        dest.writeString(categoryName);
        dest.writeString(imageThumb);
        dest.writeString(location);
        dest.writeString(address);
        dest.writeString(district);
        dest.writeString(province);
        dest.writeInt(operationId);
        dest.writeString(userName);
        dest.writeString(time);
        dest.writeString(description);
        dest.writeByte((byte) (isVideo ? 1 : 0));
        dest.writeByte((byte) (isPhoto ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Complaint> CREATOR = new Creator<Complaint>() {
        @Override
        public Complaint createFromParcel(Parcel in) {
            return new Complaint(in);
        }

        @Override
        public Complaint[] newArray(int size) {
            return new Complaint[size];
        }
    };

    public int getViolationId() {
        return violationId;
    }

    public void setViolationId(int violationId) {
        this.violationId = violationId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getUrl() {
        if (imageThumb.startsWith("http:") || imageThumb.startsWith("https:")) {
            return imageThumb;
        }
        return ApiConfig.makeUrlImage(imageThumb);
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public boolean isPhoto() {
        return isPhoto;
    }

    public void setPhoto(boolean photo) {
        isPhoto = photo;
    }

    public void setImageThumb(String imageThumb) {
        this.imageThumb = imageThumb;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getOperationId() {
        return operationId;
    }

    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTime() {
        return time;
    }

    public String getFormatTime() {
        if (TextUtils.isEmpty(time)) {
            return "";
        }
        return DateUtils.convertFormatDateTime(time, Constants.FORMAT_DATE, Constants.APP_FORMAT_DATE);
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
