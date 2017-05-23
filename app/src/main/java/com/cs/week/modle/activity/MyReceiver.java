package com.cs.week.modle.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alaramIntent = new Intent(context, MyActivity.class);
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alaramIntent);
    }
}
