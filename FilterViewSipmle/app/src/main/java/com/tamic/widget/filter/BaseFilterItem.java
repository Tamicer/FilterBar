package com.tamic.widget.filter;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by tamic on 2017-08-03.
 *
 * @link {https://github.com/Tamicer/FilterBar}
 */
public class BaseFilterItem extends BaseBean implements Parcelable {

    private int id;
    private String name;
    private int code;
    private String value;

    public BaseFilterItem() {
    }

    protected BaseFilterItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        code = in.readInt();
        value = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(code);
        dest.writeString(value);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseFilterItem> CREATOR = new Creator<BaseFilterItem>() {

        @Override
        public BaseFilterItem createFromParcel(Parcel in) {
            return new BaseFilterItem(in);
        }

        @Override
        public BaseFilterItem[] newArray(int size) {
            return new BaseFilterItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
