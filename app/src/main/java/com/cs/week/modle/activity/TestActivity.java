package com.cs.week.modle.activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.bigkoo.pickerview.TimePickerView;
import com.cs.week.R;
import com.cs.week.views.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnTest;
    private LinearLayout mActivityTest;
    private TextView tvTest;
    private Calendar calendar = Calendar.getInstance();
    private int hourOfDay;
    private int minute;
    private TimePickerView mPvTime;
    private String time;
    private Button mBtnTest2;
    private TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    private void initView() {
        mBtnTest = (Button) findViewById(R.id.btn_test1);
        mActivityTest = (LinearLayout) findViewById(R.id.activity_test);
        mBtnTest.setOnClickListener(this);
        tvTest = (TextView) findViewById(R.id.tv_test);
        tvTest.setOnClickListener(this);

        mPvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        mPvTime.setTime(new Date());
        mPvTime.setCyclic(false);
        mPvTime.setCancelable(true);
        //时间选择后回调
        mPvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                time = format.format(date);
                tvTest.setText(time);
            }
        });
        mBtnTest2 = (Button) findViewById(R.id.btn_test2);
        mBtnTest2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_test1:
              /*  // 进行闹铃注册
                Intent intent = new Intent(TestActivity.this, AlarmReceiver.class);
                //通过这个绑定广播
                PendingIntent sender = PendingIntent.getBroadcast(TestActivity.this, 0, intent, 0);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.add(Calendar.SECOND,5);
                AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
                *//*AlarmManager.RTC_WAKEUP，硬件闹钟，当闹钟发射时唤醒手机休眠；*//*
                manager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);*/

                calendar.setTimeInMillis(System.currentTimeMillis());
                //获取当前系统的时间
                int mHour = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute = calendar.get(Calendar.MINUTE);


                // 设置时间
//广播跳转
//启动一个广播
//创建闹钟
//SharedPreferences保存数据，并提交
/*  SharedPreferences time1Share = getPreferences(0);
  SharedPreferences.Editor editor = time1Share.edit();
  editor.putString("TIME1", tmpS);
  editor.commit();*/
                timePickerDialog = new TimePickerDialog(TestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        // 设置时间
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute+1);
                        calendar.set(Calendar.MILLISECOND, 0);
                        calendar.set(Calendar.SECOND, 0);

                        //广播跳转
                        Intent intent = new Intent(TestActivity.this, AlarmReceiver.class);
                        //启动一个广播
                        PendingIntent sender = PendingIntent.getBroadcast(TestActivity.this, 0, intent, 0);
                        //创建闹钟
                        AlarmManager am;
                        am = (AlarmManager) getSystemService(ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                        ToastUtils.showToast(getApplicationContext(), "闹钟设置成功");
                        //SharedPreferences保存数据，并提交
                      /*  SharedPreferences time1Share = getPreferences(0);
                        SharedPreferences.Editor editor = time1Share.edit();
                        editor.putString("TIME1", tmpS);
                        editor.commit();*/


                    }
                }, mHour, mMinute, true);
                timePickerDialog.show();



                break;


            case R.id.btn_test2:
                calendar.setTimeInMillis(System.currentTimeMillis());
                //获取当前系统的时间
                int mHour1 = calendar.get(Calendar.HOUR_OF_DAY);
                int mMinute1 = calendar.get(Calendar.MINUTE);
                TimePickerDialog  timePickerDialog = new TimePickerDialog(TestActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        // 设置时间
                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        calendar.set(Calendar.MILLISECOND, 0);
                        calendar.set(Calendar.SECOND, 0);

                        //广播跳转
                        Intent intent = new Intent(TestActivity.this, AlarmReceiver.class);
                        //启动一个广播
                        PendingIntent sender = PendingIntent.getBroadcast(TestActivity.this, 0, intent, 0);
                        //创建闹钟
                        AlarmManager am;
                        am = (AlarmManager) getSystemService(ALARM_SERVICE);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
                        ToastUtils.showToast(getApplicationContext(), "闹钟设置成功");
                        //SharedPreferences保存数据，并提交
                      /*  SharedPreferences time1Share = getPreferences(0);
                        SharedPreferences.Editor editor = time1Share.edit();
                        editor.putString("TIME1", tmpS);
                        editor.commit();*/


                    }
                }, mHour1, mMinute1, true);
                timePickerDialog.show();

                break;
        }
    }
}
