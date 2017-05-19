package com.hiepkhach9x.publiceyes.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HungHN on 5/19/17.
 */

public class ValueConfig implements Parcelable {
    @SerializedName("key_cf")
    private String keyCf;
    @SerializedName("value_cf")
    private String valueCf;

    protected ValueConfig(Parcel in) {
        keyCf = in.readString();
        valueCf = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(keyCf);
        dest.writeString(valueCf);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ValueConfig> CREATOR = new Creator<ValueConfig>() {
        @Override
        public ValueConfig createFromParcel(Parcel in) {
            return new ValueConfig(in);
        }

        @Override
        public ValueConfig[] newArray(int size) {
            return new ValueConfig[size];
        }
    };

    public String getKeyCf() {
        return keyCf;
    }

    public void setKeyCf(String keyCf) {
        this.keyCf = keyCf;
    }

    public String getValueCf() {
        return valueCf;
    }

    public void setValueCf(String valueCf) {
        this.valueCf = valueCf;
    }
}
