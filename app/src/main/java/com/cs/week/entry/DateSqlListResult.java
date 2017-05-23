package com.cs.week.entry;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.DataSupport;

/**
 * Created by chenshuai on 2017/4/27.
 */

public class DateSqlListResult extends DataSupport implements Parcelable {
    private String title;
    private int flag;
    private String content;
    private String time;
    private String imagepath;
    private int deleteid;
    private int color;
    private String creattime;
    private String event;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public int getDeleteid() {
        return deleteid;
    }

    public void setDeleteid(int deleteid) {
        this.deleteid = deleteid;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCreattime() {
        return creattime;
    }

    public void setCreattime(String creattime) {
        this.creattime = creattime;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.flag);
        dest.writeString(this.content);
        dest.writeString(this.time);
        dest.writeString(this.imagepath);
        dest.writeInt(this.deleteid);
        dest.writeInt(this.color);
        dest.writeString(this.creattime);
        dest.writeString(this.event);
    }

    public DateSqlListResult() {
    }

    protected DateSqlListResult(Parcel in) {
        this.title = in.readString();
        this.flag = in.readInt();
        this.content = in.readString();
        this.time = in.readString();
        this.imagepath = in.readString();
        this.deleteid = in.readInt();
        this.color = in.readInt();
        this.creattime = in.readString();
        this.event = in.readString();
    }

    public static final Parcelable.Creator<DateSqlListResult> CREATOR = new Parcelable.Creator<DateSqlListResult>() {
        @Override
        public DateSqlListResult createFromParcel(Parcel source) {
            return new DateSqlListResult(source);
        }

        @Override
        public DateSqlListResult[] newArray(int size) {
            return new DateSqlListResult[size];
        }
    };
}
