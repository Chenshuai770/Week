package com.cs.week.views;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenshuai on 2017/4/21.
 */

public class ToastUtils {
    private static Toast toast;
    public static void showToast(Context context,String  content){
        if (toast==null) {
            toast=Toast.makeText(context,content,Toast.LENGTH_SHORT);
        }else {
            toast.setText(content);
        }
        toast.show();
    }
}
