package com.cs.week.modle.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.cs.week.R;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.modle.adpter.ItemListCompleteAdapter;
import com.cs.week.views.ToastUtils;
import com.luck.picture.lib.decoration.GridSpacingItemDecoration;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.PictureConfig;
import com.yalantis.ucrop.entity.LocalMedia;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.cs.week.R.id.et_content;
import static com.cs.week.R.id.et_title;

public class AddActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTlMain;
    private ViewPager mVpMain;
    private ImageView mIvAdd;
    private EditText mEtTitle;
    private EditText mEtContent;
    private RecyclerView mRlvAdd;
    private TextView mTvTime;
    private FloatingActionButton mFltAdd;
    private Context context = AddActivity.this;
    //图片选择
    private int maxSelectNum = 4;// 加载图片默认
    private ItemListCompleteAdapter adapter;
    private List<LocalMedia> selectMedia = new ArrayList<>();

    //用来封装recycleview的本地图片路径
    private List<String> path = new ArrayList<>();
    private String time;
    private int flag = -1;
    private LinearLayout mLlAddPriority;
    private LinearLayout mLlAddBlue;
    private LinearLayout mLlAddGreen;
    private LinearLayout mLlAddOrigin;
    private LinearLayout mLlAddRed;
    private LinearLayout mLlAddItem;
    private ImageView mIvAddPriority;
    private DateSqlListResult dateSqlListResult;
    private int color = 0;
    private TextView mTvCreatTime;
    private String creattime;
    private String event;
    private int flag1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        initView();
    }

    private void initView() {
        flag = getIntent().getIntExtra("flag", 0);
        Log.d("AddActivity", "flag:" + flag);
        flag1=flag;

        mIvAdd = (ImageView) findViewById(R.id.iv_add);
        mEtTitle = (EditText) findViewById(et_title);
        mEtContent = (EditText) findViewById(et_content);

        mRlvAdd = (RecyclerView) findViewById(R.id.rlv_add);
        mTvTime = (TextView) findViewById(R.id.tv_time);
        mFltAdd = (FloatingActionButton) findViewById(R.id.flt_add);
        mFltAdd.setOnClickListener(this);

        mTvTime.setOnClickListener(this);
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
        mRlvAdd.setLayoutManager(staggeredGridLayoutManager);
        int spanCount = 4; // 3 columns
        int spacing = 11; // 8px
        boolean includeEdge = false;
        mRlvAdd.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
        mRlvAdd.setLayoutManager(staggeredGridLayoutManager);
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
        mRlvAdd.setAdapter(adapter);
        adapter.setOnItemClickListener(new ItemListCompleteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Toast.makeText(context, "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });

        mLlAddPriority = (LinearLayout) findViewById(R.id.ll_add_priority);
        mLlAddPriority.setOnClickListener(this);
        mLlAddBlue = (LinearLayout) findViewById(R.id.ll_add_blue);
        mLlAddBlue.setOnClickListener(this);
        mLlAddGreen = (LinearLayout) findViewById(R.id.ll_add_green);
        mLlAddGreen.setOnClickListener(this);
        mLlAddOrigin = (LinearLayout) findViewById(R.id.ll_add_origin);
        mLlAddOrigin.setOnClickListener(this);
        mLlAddRed = (LinearLayout) findViewById(R.id.ll_add_red);
        mLlAddRed.setOnClickListener(this);
        mLlAddItem = (LinearLayout) findViewById(R.id.ll_add_item);

        mLlAddItem.setOnClickListener(this);
        mIvAddPriority = (ImageView) findViewById(R.id.iv_add_priority);
        mIvAddPriority.setOnClickListener(this);
        mTvCreatTime = (TextView) findViewById(R.id.tv_creat_time);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
        Date date = new Date();
        creattime = df.format(date);
        mTvCreatTime.setText("创建时间为:"+ creattime);
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
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flt_add:
                hideKeyboard();
                String title = mEtTitle.getText().toString();
                String content = mEtContent.getText().toString();
                flag = getIntent().getIntExtra("flag", 0);
                if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                    ToastUtils.showToast(context, "请填写内容后保存");
                } else {
                    Intent intent = new Intent("发送消息");
                    Connector.getDatabase();
                    dateSqlListResult = new DateSqlListResult();
                    dateSqlListResult.setTitle(title);
                    dateSqlListResult.setColor(color);
                    if (!TextUtils.isEmpty(time)) {
                        dateSqlListResult.setTime(time);
                    } else {
                    }
                    dateSqlListResult.setContent(content);
                    dateSqlListResult.setFlag(flag);
                    if (path.size() > 0) {
                        dateSqlListResult.setImagepath(path.get(0));
                    }
                    dateSqlListResult.setCreattime(creattime);
                    dateSqlListResult.setEvent(event);

                    List<DateSqlListResult> all = DataSupport.findAll(DateSqlListResult.class);
                    if (all.size() == 0) {
                        dateSqlListResult.setDeleteid(1);
                    } else if (all.size() > 0) {
                        dateSqlListResult.setDeleteid(all.size() + 1);
                    }
                    dateSqlListResult.save();

                  /*  if (dateSqlListResult.save()) {
                        Toast.makeText(context, "存储成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "存储失败", Toast.LENGTH_SHORT).show();
                    }*/
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("data", dateSqlListResult);
                    intent.putExtras(bundle);
                    sendBroadcast(intent);
                    finish();
                    // Log.d("AddActivity", "DataSupport.findFirst(DateSqlListResult.class):" + DataSupport.findFirst(DateSqlListResult.class));
                    // Log.d("AddActivity", dataListResult.getContent());
                }
                break;
            case R.id.tv_time:
                //ll   ToastUtils.showToast(AddActivity.this,"da");

                TimePickerView mPvTime = new TimePickerView(AddActivity.this, TimePickerView.Type.HOURS_MINS);
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
                        Log.d("AddActivity", "day:" + day);

                        AlarmManager alarmMgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getApplicationContext(), MyReceiver.class);
                        int randomNumber = (int) Math.round(Math.random() * 10000);
                        PendingIntent alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), randomNumber, intent, 0);

                        Log.d("AddActivity", "flag1:" + flag1);
                        if (flag-day==0) {
                           // alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), alarmIntent);
                            alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),2*60*1000, alarmIntent);
                        }else if (flag -day>0 ) {
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
                        } else if(flag-day<0){
                            ToastUtils.showToast(getApplicationContext(), "有事件未处理");
                        }
                        mTvTime.setText("闹铃设置为:" + time);
                    }
                });

                break;
            case R.id.ll_add_priority:

                if (mLlAddItem.getVisibility() == View.GONE) {
                    mLlAddItem.setVisibility(View.VISIBLE);
                } else {
                    mLlAddItem.setVisibility(View.GONE);
                }
                break;
            case R.id.ll_add_blue:
                color = 1;
                event="日常";
                mIvAddPriority.setImageResource(R.drawable.ic_priority_darkblue);
                mLlAddItem.setVisibility(View.GONE);
                break;
            case R.id.ll_add_green:
                color = 2;
                event="一般";
                mIvAddPriority.setImageResource(R.drawable.ic_priority_green);
                mLlAddItem.setVisibility(View.GONE);
                break;
            case R.id.ll_add_origin:
                color = 3;
                event="重要";
                mIvAddPriority.setImageResource(R.drawable.ic_priority_origin);
                mLlAddItem.setVisibility(View.GONE);
                break;
            case R.id.ll_add_red:
                color = 4;
                event="紧急";
                mIvAddPriority.setImageResource(R.drawable.ic_priority_red);
                mLlAddItem.setVisibility(View.GONE);
                break;
            case R.id.ll_add_item:
                break;
            case R.id.iv_add_priority:
                break;


        }
    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2 && resultCode == RESULT_OK) {

        }
    }


}
