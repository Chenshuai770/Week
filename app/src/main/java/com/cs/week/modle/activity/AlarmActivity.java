package com.cs.week.modle.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.cs.week.R;
import com.cs.week.views.SelectRemindCyclePopup;
import com.cs.week.views.SelectRemindWayPopup;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.cs.week.R.id.tv_repeat_value;
import static com.cs.week.R.id.tv_ring_value;

public class AlarmActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTvTimeSelect;
    private TextView mTvRepeatValue;
    private RelativeLayout mRlRepeat;
    private TextView mTvRingValue;
    private RelativeLayout mRlRing;
    private Button mBtnSet;
    private TimePickerView mPvTime;
    private LinearLayout mAllLayout;
    private int cycle;
    private int ring;
    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        initView();
    }

    private void initView() {
        mTvTimeSelect = (TextView) findViewById(R.id.tv_time_select);
        mTvTimeSelect.setOnClickListener(this);
        mTvRepeatValue = (TextView) findViewById(tv_repeat_value);
        mRlRepeat = (RelativeLayout) findViewById(R.id.rl_repeat);
        mRlRepeat.setOnClickListener(this);
        mTvRingValue = (TextView) findViewById(tv_ring_value);
        mRlRing = (RelativeLayout) findViewById(R.id.rl_ring);
        mRlRing.setOnClickListener(this);
        mBtnSet = (Button) findViewById(R.id.btn_set);
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
                mTvTimeSelect.setText(time);
            }
        });

        mBtnSet.setOnClickListener(this);
        mAllLayout = (LinearLayout) findViewById(R.id.all_layout);
        mAllLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_time_select:
                mPvTime.show();
                break;
            case R.id.rl_repeat:
                selectRemindCycle();
                break;
            case R.id.rl_ring:
                selectRingWay();
                break;
            case R.id.btn_set:
                setClock();
                break;
        }
    }
    /**
     * 设置铃声等
     */
    private void selectRingWay() {
        SelectRemindWayPopup fp = new SelectRemindWayPopup(this);
        fp.showPopup(mAllLayout);
        fp.setOnSelectRemindWayPopupListener(new SelectRemindWayPopup.SelectRemindWayPopupOnClickListener() {
            @Override
            public void obtainMessage(int flag) {
                switch (flag) {
                    // 震动
                    case 0:
                        mTvRingValue.setText("震动");
                        ring = 0;
                        break;
                    // 铃声
                    case 1:
                        mTvRingValue.setText("铃声");
                        ring = 1;
                        break;
                    default:
                        break;
                }
            }
        });
    }
    /**
     * 设置周期
     */
    private void selectRemindCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(mAllLayout);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup.SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    // 星期一
                    case 0:
                        break;
                    // 星期二
                    case 1:

                        break;
                    // 星期三
                    case 2:

                        break;
                    // 星期四
                    case 3:

                        break;
                    // 星期五
                    case 4:

                        break;
                    // 星期六
                    case 5:

                        break;
                    // 星期日
                    case 6:
                        break;
                    // 确定
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        mTvRepeatValue.setText(parseRepeat(repeat, 0));
                        cycle = repeat;
                        fp.dismiss();
                        break;
                    case 8:
                        mTvRepeatValue.setText("每天");
                        cycle = 0;
                        fp.dismiss();
                        break;
                    case 9:
                        mTvRepeatValue.setText("只响一次");
                        cycle = -1;
                        fp.dismiss();
                        break;
                    default:
                        setClock();
                        break;
                }
            }
        });
    }

    /**
     * @param repeat 解析二进制闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二cycle等，flag=1,返回weeks(1,2,3)
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "周一";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "周二";
                weeks = "2";
            } else {
                cycle = cycle + "," + "周二";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "周三";
                weeks = "3";
            } else {
                cycle = cycle + "," + "周三";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "周四";
                weeks = "4";
            } else {
                cycle = cycle + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "周五";
                weeks = "5";
            } else {
                cycle = cycle + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "周六";
                weeks = "6";
            } else {
                cycle = cycle + "," + "周六";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "周日";
                weeks = "7";
            } else {
                cycle = cycle + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }

        return flag == 0 ? cycle : weeks;
    }

    /**
     * 设置闹钟
     */

    private void setClock() {
        if (time != null && time.length() > 0) {
            String[] times = time.split(":");
            if (cycle == 0) {//是每天的闹钟
                AlarmManagerUtil.setAlarm(this, 0, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            } if(cycle == -1){//是只响一次的闹钟
                AlarmManagerUtil.setAlarm(this, 1, Integer.parseInt(times[0]), Integer.parseInt
                        (times[1]), 0, 0, "闹钟响了", ring);
            }else {//多选，周几的闹钟
                String weeksStr = parseRepeat(cycle, 1);
                String[] weeks = weeksStr.split(",");
                for (int i = 0; i < weeks.length; i++) {
                    AlarmManagerUtil.setAlarm(this, 2, Integer.parseInt(times[0]), Integer
                            .parseInt(times[1]), i, Integer.parseInt(weeks[i]), "闹钟响了", ring);
                }
            }
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
        }

    }

}
