package com.cs.week.entry;

import org.litepal.crud.DataSupport;

/**
 * Created by chenshuai on 2017/4/25.
 */

public class DateRegistResult extends DataSupport{
    private String name;
    private String password;
    private int tagid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String  getPassword() {
        return password;
    }

    public void setPassword(String  password) {
        this.password = password;
    }


    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }
}
