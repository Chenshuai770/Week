package com.cs.week.modle.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by chenshuai on 2017/5/8.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alaramIntent = new Intent(context, AlarmSettingAgain.class);
        alaramIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(alaramIntent);
    }
}
