package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hungh on 3/3/2017.
 */

public class User implements Parcelable{
    @SerializedName("decentralization_id")
    private int id;
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String fullName;
    @SerializedName("mobile_phone")
    private String mobileNumber;
    @SerializedName("address")
    private String address;
    @SerializedName("id_card")
    private String idCard;
    @SerializedName("bonus_point")
    private int point;
    @SerializedName("age")
    private int age;

    public User() {
    }

    protected User(Parcel in) {
        email = in.readString();
        fullName = in.readString();
        mobileNumber = in.readString();
        address = in.readString();
        idCard = in.readString();
        point = in.readInt();
        age = in.readInt();
        id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(fullName);
        dest.writeString(mobileNumber);
        dest.writeString(address);
        dest.writeString(idCard);
        dest.writeInt(point);
        dest.writeInt(age);
        dest.writeInt(id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
