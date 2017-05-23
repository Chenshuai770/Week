package com.cs.week.modle.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.cs.week.R;
import com.cs.week.base.BaseFragment;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.modle.adpter.TabAdapter;
import com.cs.week.modle.fragment.FriFragment;
import com.cs.week.modle.fragment.MonFragment;
import com.cs.week.modle.fragment.SatFragment;
import com.cs.week.modle.fragment.SunFragment;
import com.cs.week.modle.fragment.ThurFragment;
import com.cs.week.modle.fragment.TuesFragment;
import com.cs.week.modle.fragment.WedFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar;
    private FloatingActionButton mFltMain;
    private TabLayout mTabs;
    private ViewPager mViewpager;
    private Context context=MainActivity.this;
    public static final String[] tabTitle = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private List<BaseFragment> mList_fm;
    private TabAdapter mAdapter;
    private int flag;
    private SearchView searchView;
    private List<DateSqlListResult> mList=new ArrayList<>();
    private ArrayList<Integer> mListId=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }
    private void initView() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //将Actiongbar传入进去
        setSupportActionBar(mToolbar);
        //设置我们的SearchView
        searchView = (SearchView) findViewById(R.id.searchView);

        searchView.setIconifiedByDefault(true);//设置展开后图标的样式,这里只有两种,一种图标在搜索框外,一种在搜索框内
        searchView.onActionViewExpanded();// 写上此句后searchView初始是可以点击输入的状态，如果不写，那么就需要点击下放大镜，才能出现输入框,也就是设置为ToolBar的ActionView，默认展开
        searchView.requestFocus();//输入焦点
        searchView.setSubmitButtonEnabled(true);//添加提交按钮，监听在OnQueryTextListener的onQueryTextSubmit响应
        searchView.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        searchView.setIconified(false);//输入框内icon不显示
        searchView.requestFocusFromTouch();//模拟焦点点击事件

        searchView.setFocusable(false);//禁止弹出输入法，在某些情况下有需要
        searchView.clearFocus();//禁止弹出输入法，在某些情况下有需要

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //单击搜索按钮时激发该方法query是输入的字符串
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), QuireActivity.class);
                intent.putExtra("query",query);
                startActivity(intent);
                return true;
            }
            //用户输入字符时激发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mFltMain = (FloatingActionButton) findViewById(R.id.flt_main);
        mFltMain.setOnClickListener(this);
        mTabs = (TabLayout) findViewById(R.id.tabs);
        mViewpager = (ViewPager) findViewById(R.id.viewpager);
        List<BaseFragment> mList_fm = new ArrayList<>();
        mList_fm.add(new SunFragment());
        mList_fm.add(new MonFragment());
        mList_fm.add(new TuesFragment());
        mList_fm.add(new WedFragment());
        mList_fm.add(new ThurFragment());
        mList_fm.add(new FriFragment());
        mList_fm.add(new SatFragment());

        mAdapter=new TabAdapter(getSupportFragmentManager(),mList_fm);
        mViewpager.setAdapter(mAdapter);
        mTabs.setupWithViewPager(mViewpager);
        mTabs.setTabsFromPagerAdapter(mAdapter);
        mViewpager.setOffscreenPageLimit(1);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.getTime().getDay();
        flag=day;
        mViewpager.setCurrentItem(day);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                Log.d("MainActivity", "mViewpager.getCurrentItem():" + mViewpager.getCurrentItem());
                flag=position;
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    /**
     *  onCreateOptionsMenu 来初始化Activity/Fragment的选项菜单。
     Android同时提供了另一个方法 onOptionsItemSelected()，该方法在菜单项被点击时调用，在这里处理菜单项的点击事件。所以一般不会在 onCreateOptionsMenu 为某个item实现其callback.
     需要纠正的是，MenuItem没有setClickListener，而是MenuItem.setOnMenuItemClickListener.
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.flt_main:
                Intent intent = new Intent(context, AddActivity.class);

                intent.putExtra("flag",flag);
                startActivity(intent);
                break;
        }
    }
}
