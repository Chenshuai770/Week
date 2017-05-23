package com.cs.week.modle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.cs.week.R;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.modle.adpter.ItemQuireResult;

import org.litepal.crud.DataSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class QuireActivity extends AppCompatActivity {

    private Toolbar mToolbarQuire;
    private RecyclerView mRlvQuire;
    private ItemQuireResult adapter;
    private List<DateSqlListResult> mList=new ArrayList<>();
    private List<DateSqlListResult> quire=new ArrayList<>();
    private String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quire);
        initView();
    }

    private void initView() {
        mToolbarQuire = (Toolbar) findViewById(R.id.toolbar_quire);
        setSupportActionBar(mToolbarQuire);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        query = intent.getStringExtra("query");

        List<DateSqlListResult> all = DataSupport.findAll(DateSqlListResult.class);
        for (int i = 0; i < all.size(); i++) {

            if (query.equals(all.get(i).getTitle())){
                mList.add(all.get(i));

            }

            if (query.equals(all.get(i).getContent())){
                mList.add(all.get(i));
            }
            if (query.equals(all.get(i).getTime())){
                mList.add(all.get(i));
            }

        }

        if (query.equals("日常")){
            List<DateSqlListResult> use = DataSupport.where("event = ? ", "日常").find(DateSqlListResult.class);
            mList.addAll(use);
        }

        if (query.equals("一般")){
            List<DateSqlListResult> common = DataSupport.where("event = ? ", "一般").find(DateSqlListResult.class);
            mList.addAll(common);
        }

        if (query.equals("重要")){
            List<DateSqlListResult> important = DataSupport.where("event = ? ", "重要").find(DateSqlListResult.class);
            mList.addAll(important);
        }

        if (query.equals("紧急")){
            List<DateSqlListResult> crash = DataSupport.where("event = ? ", "紧急").find(DateSqlListResult.class);
            mList.addAll(crash);
        }
        if (query.equals("默认")){
            List<DateSqlListResult> default1 = DataSupport.where("event = ? ", "默认").find(DateSqlListResult.class);
            mList.addAll(default1);
        }
        //Log.d("QuireActivity", "use.size():" + use.size());
        if(query.equals("全部")){
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            mList.addAll(alldata);
        }

        if (query.equals("一月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==1) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("二月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==2) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("三月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==3) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("四月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==4) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

        if (query.equals("五月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==5) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("六月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==6) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("七月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==7) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("八月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==8) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("九月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==9) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("十月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==10) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("十一月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==11) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
        if (query.equals("十二月")) {
            List<DateSqlListResult> alldata = DataSupport.findAll(DateSqlListResult.class);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < alldata.size(); i++) {
                String creattime = alldata.get(i).getCreattime();
                try {
                    Date date =sdf.parse(creattime);
                    calendar.setTime(date);
                    if ((calendar.getTime().getMonth()+1)==12) {
                        mList.add(alldata.get(i));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }

        if (mList.size()==0){
            Toast.makeText(this, "请检查输入关键字", Toast.LENGTH_SHORT).show();
        }

        mToolbarQuire.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mRlvQuire = (RecyclerView) findViewById(R.id.rlv_quire);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRlvQuire.setLayoutManager(layoutManager);
        adapter = new ItemQuireResult(this, mList);
        mRlvQuire.setAdapter(adapter);

        adapter.setOnItemClickLitener(new ItemQuireResult.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(QuireActivity.this, LookActivity.class);

                String time = mList.get(position).getTime();
                String title = mList.get(position).getTitle();
                String content = mList.get(position).getContent();
                int color = mList.get(position).getColor();
                int deleteid = mList.get(position).getDeleteid();
                if (!TextUtils.isEmpty(mList.get(position).getImagepath())) {
                    String path = mList.get(position).getImagepath();
                    intent.putExtra("path",path);
                }
                intent.putExtra("time",time);
                intent.putExtra("title",title);
                intent.putExtra("content",content);
                intent.putExtra("deleteid",deleteid);
                intent.putExtra("color",color);
                startActivity(intent);
                finish();
            }
            @Override
            public boolean onLongItemClick(View view, int position) {
                return false;
            }
        });

    }
}
