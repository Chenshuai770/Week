package com.cs.week.modle.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.cs.week.R;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.modle.adpter.ItemListCompleteAdapter;
import com.cs.week.views.ToastUtils;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cs.week.R.id.btn_look;

public class LookActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mIvLook;
    private Toolbar mTbLook;
    private EditText mEtLookTitle;
    private EditText mEtLookContent;
    private TextView mTvLookTime;
    private Button mBtn;
    private LinearLayout mContentAdd;
    private String time;
    private RecyclerView mRlvLook;
    //用来封装recycleview的本地图片路径
    private List<String> path = new ArrayList<>();
    private Context context = LookActivity.this;
    //图片选择
    private int maxSelectNum = 4;// 加载图片默认
    private ItemListCompleteAdapter adapter;
    private List<LocalMedia> selectMedia = new ArrayList<>();
    private ImageView mIvLookPriority;
    private LinearLayout mLlLookPriority;
    private LinearLayout mLlLookBlue;
    private LinearLayout mLlLookGreen;
    private LinearLayout mLlLookOrigin;
    private LinearLayout mLlLookRed;
    private LinearLayout mLlLookItem;
    private int color = 0;
    private String time1;
    private TextView mTvLookCreattime;
    private String creattime;
    private String event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look);
        initView();

    }

    private void initView() {
        Intent intent = getIntent();
        mIvLook = (ImageView) findViewById(R.id.iv_look);
        mTbLook = (Toolbar) findViewById(R.id.tb_look);
        mTbLook.setTitle("");
        setSupportActionBar(mTbLook);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTbLook.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvLookPriority = (ImageView) findViewById(R.id.iv_look_priority);
        mIvLookPriority.setOnClickListener(this);
        mLlLookPriority = (LinearLayout) findViewById(R.id.ll_look_priority);
        mLlLookPriority.setOnClickListener(this);
        mLlLookBlue = (LinearLayout) findViewById(R.id.ll_look_blue);
        mLlLookBlue.setOnClickListener(this);
        mLlLookGreen = (LinearLayout) findViewById(R.id.ll_look_green);
        mLlLookGreen.setOnClickListener(this);
        mLlLookOrigin = (LinearLayout) findViewById(R.id.ll_look_origin);
        mLlLookOrigin.setOnClickListener(this);
        mLlLookRed = (LinearLayout) findViewById(R.id.ll_look_red);
        mLlLookRed.setOnClickListener(this);
        mLlLookItem = (LinearLayout) findViewById(R.id.ll_look_item);
        mLlLookItem.setOnClickListener(this);
        mEtLookTitle = (EditText) findViewById(R.id.et_look_title);
        mEtLookContent = (EditText) findViewById(R.id.et_look_content);
        mTvLookTime = (TextView) findViewById(R.id.tv_look_time);
        mRlvLook = (RecyclerView) findViewById(R.id.rlv_look);
        time1 = intent.getStringExtra("time");
        if (TextUtils.isEmpty(time1)) {
            mTvLookTime.setText("修改闹铃");
        } else {
            mTvLookTime.setText("闹铃:"+time1);
        }
        mTvLookTime.setOnClickListener(this);

        mBtn = (Button) findViewById(btn_look);
        mBtn.setOnClickListener(this);
        String title = intent.getStringExtra("title");
        String path1 = intent.getStringExtra("path");
        int deleteid = intent.getIntExtra("deleteid", 0);
        int color = intent.getIntExtra("color", 0);
        int flag = intent.getIntExtra("flag", 0);
        String content = intent.getStringExtra("content");
        Glide.with(this).load(path1).into(mIvLook);
        mEtLookContent.setText(content);
        mEtLookTitle.setText(title);
        switch (color) {
            case 0:
                mIvLookPriority.setImageResource(R.drawable.ic_priority_blue);
                break;
            case 1:
                mIvLookPriority.setImageResource(R.drawable.ic_priority_darkblue);
                break;
            case 2:
                mIvLookPriority.setImageResource(R.drawable.ic_priority_green);
                break;
            case 3:
                mIvLookPriority.setImageResource(R.drawable.ic_priority_origin);
                break;
            case 4:
                mIvLookPriority.setImageResource(R.drawable.ic_priority_red);
                break;

        }

        if (color==0) {
            event="默认";
        }

        FunctionConfig config = new FunctionConfig();
        config.setType(1);
        //最大可选数量
        config.setMaxSelectNum(1);
        config.setEnableCrop(false);
        config.setEnablePreview(false);
        PictureConfig.init(config);
        GridLayoutManager staggeredGridLayoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRlvLook.setLayoutManager(staggeredGridLayoutManager);
        int spanCount = 4; // 3 columns
        int spacing = 11; // 8px
        boolean includeEdge = false;
        mRlvLook.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mRlvLook.setLayoutManager(staggeredGridLayoutManager);
        adapter = new ItemListCompleteAdapter(context, new ItemListCompleteAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick(int type, int position) {
                switch (type) {
                    case 0:
                        FunctionConfig config = new FunctionConfig();
                        config.setType(1);
                        config.setMaxSelectNum(1);
                        config.setEnableCrop(false);
                        config.setEnablePreview(false);
                        config.setCompress(true);
                        PictureConfig.init(config);
                        PictureConfig.getPictureConfig().openPhoto(context, resultCallback);
                        break;
                    case 1:
                        // 删除图片
                        path.remove(position);
                        selectMedia.remove(position);
                        adapter.notifyItemRemoved(position);
                        break;
                }
            }
        });
        adapter.setSelectMax(maxSelectNum);
        mRlvLook.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemListCompleteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
        mTvLookCreattime = (TextView) findViewById(R.id.tv_look_creattime);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        Date date = new Date();
        creattime = df.format(date);
        mTvLookCreattime.setText("修改时间为:"+ creattime);
    }

    /**
     * 图片回调方法
     */
    private PictureConfig.OnSelectResultCallback resultCallback = new PictureConfig.OnSelectResultCallback() {
        @Override
        public void onSelectSuccess(List<LocalMedia> resultList) {
            selectMedia = resultList;
            //Log.i("callBack_result", selectMedia.size() + "");
            path.clear();
            if (selectMedia != null) {
                adapter.setList(selectMedia);
                for (int i = 0; i < resultList.size(); i++) {
                    path.add(resultList.get(i).getCompressPath());
                    Log.d("path", path.get(i));
                }
                adapter.notifyDataSetChanged();
                Glide.with(LookActivity.this).load(path.get(0)).into(mIvLook);
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_look:
                String content = mEtLookContent.getText().toString();
                String title = mEtLookTitle.getText().toString();
                int deleteid = getIntent().getIntExtra("deleteid", 0);
                Log.d("LookActivity", "deleteid:" + deleteid);
                DateSqlListResult dateSqlListResult = DataSupport.find(DateSqlListResult.class, deleteid);
                if (!TextUtils.isEmpty(title)) {
                    dateSqlListResult.setTitle(title);
                } else {
                }
                if (!TextUtils.isEmpty(time)) {
                    dateSqlListResult.setTime(time);
                } else {

                }
                if (path.size() > 0) {
                    dateSqlListResult.setImagepath(path.get(0));
                }
                dateSqlListResult.setCreattime(creattime);
                dateSqlListResult.setColor(color);
                dateSqlListResult.setContent(content);
                dateSqlListResult.setEvent(event);
                dateSqlListResult.update(deleteid);
                Intent intent = new Intent("更新消息");
                sendBroadcast(intent);
                finish();
                break;
            case R.id.tv_look_time:
                TimePickerView mPvTime = new TimePickerView(LookActivity.this, TimePickerView.Type.HOURS_MINS);
                mPvTime.setTime(new Date());
                mPvTime.setCyclic(false);
                mPvTime.setCancelable(true);
                mPvTime.show();
                //时间选择后回调
                mPvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date) {
                        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                        time = format.format(date);
                    /*    String substring = time.substring(Integer.parseInt(":"));
                          Log.d("AddActivity", substring);*/
                        //设置指定时间的闹铃,并指定半秒后重复
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        int hours = calendar.getTime().getHours();
                        int mini = calendar.getTime().getMinutes();
                        Log.d("AddActivity", "calendar.getTime().getHours():" + calendar.getTime().getHours());
                        Log.d("AddActivity", "calendar.getTime().getHours():" + calendar.getTime().getMinutes());

                        calendar.setTimeInMillis(System.currentTimeMillis());
                        calendar.set(Calendar.HOUR_OF_DAY, hours);
                        calendar.set(Calendar.MINUTE, mini);
                        int day = calendar.getTime().getDay();

                        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                        int randomNumber = (int) Math.round(Math.random() * 10000);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), randomNumber, intent, 0);
                        int flag = getIntent().getIntExtra("flag", 0);
                        if (day == flag) {

                            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),2*60*1000, alarmIntent);

                        } else if (flag > day) {
                            if (flag - day == 1) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 1 * 24 * 60 * 60 * 1000, alarmIntent);
                            } else if (flag - day == 2) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 2 * 24 * 60 * 60 * 1000, alarmIntent);
                            } else if (flag - day == 3) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 3 * 24 * 60 * 60 * 1000, alarmIntent);
                            } else if (flag - day == 4) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 4 * 24 * 60 * 60 * 1000, alarmIntent);
                            } else if (flag - day == 5) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 5 * 24 * 60 * 60 * 1000, alarmIntent);
                            } else if (flag - day == 6) {
                                alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + 6 * 24 * 60 * 60 * 1000, alarmIntent);
                            }
                        } else {
                            ToastUtils.showToast(getApplicationContext(), "有事件未处理");
                        }
                        mTvLookTime.setText("闹铃:"+time);
                    }
                });
                break;
            case R.id.ll_look_priority:

                if (mLlLookItem.getVisibility() == View.GONE) {
                    mLlLookItem.setVisibility(View.VISIBLE);
                } else {
                    mLlLookItem.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_look_blue:
                event="日常";
                color = 1;
                mIvLookPriority.setImageResource(R.drawable.ic_priority_darkblue);
                mLlLookItem.setVisibility(View.GONE);
                break;
            case R.id.ll_look_green:
                event="一般";
                color = 2;
                mIvLookPriority.setImageResource(R.drawable.ic_priority_green);
                mLlLookItem.setVisibility(View.GONE);
                break;
            case R.id.ll_look_origin:
                event="重要";
                color = 3;
                mIvLookPriority.setImageResource(R.drawable.ic_priority_origin);
                mLlLookItem.setVisibility(View.GONE);
                break;
            case R.id.ll_look_red:
                event="紧急";
                color = 4;
                mIvLookPriority.setImageResource(R.drawable.ic_priority_red);
                mLlLookItem.setVisibility(View.GONE);
                break;
            case R.id.ll_add_item:
                break;
            case R.id.iv_add_priority:
                break;
        }
    }
}
