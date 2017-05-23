package com.cs.week.modle.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cs.week.R;
import com.cs.week.base.BaseFragment;
import com.cs.week.entry.DateSqlListResult;
import com.cs.week.modle.activity.LookActivity;
import com.cs.week.modle.adpter.ItemSunResult;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenshuai on 2017/4/27.
 */

public class SunFragment extends BaseFragment {
    private RecyclerView mRecyclerView;
    private ItemSunResult adapter;
    private List<DateSqlListResult> mList=new ArrayList<>();

    private boolean isPrepared;
    //标志当前页面是否可见
    private boolean isVisible;

    private BroadcastReceiver receiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
           switch (intent.getAction()){
               case "发送消息":
                   initData();
                   break;
               case "更新消息":
                   initData();
                   break;
           }

        }
    };


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fm_sun, container, false);
        initView(view);
        initData();
        registerBroadcast(getContext(),receiver);
        return view;
    }

    private void initData() {
        mList.clear();
        List<DateSqlListResult> data = DataSupport.where("flag=?","0").find(DateSqlListResult.class);
        mList.addAll(data);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initView(View view) {
        mRecyclerView= (RecyclerView) view.findViewById(R.id.rl_sun);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter=new ItemSunResult(getContext(),mList);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickLitener(new ItemSunResult.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), LookActivity.class);

                String time = mList.get(position).getTime();
                String title = mList.get(position).getTitle();
                String content = mList.get(position).getContent();
                int flag = mList.get(position).getFlag();
                intent.putExtra("flag",flag);
                int deleteid = mList.get(position).getDeleteid();
                int color = mList.get(position).getColor();
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
               // Toast.makeText(getActivity(), "短按", Toast.LENGTH_SHORT).show();

            }

            @Override
            public boolean onLongItemClick(View view, final int position) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("是否删除");
                builder.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        int deleteid = mList.get(position).getDeleteid();
                        DataSupport.delete(DateSqlListResult.class,deleteid);
                        mList.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setCancelable(false);
                builder.create().show();
                return true;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepared=true;
        lazyLoad();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        //懒加载
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onInvisible() {
    }

    private void onVisible() {
        lazyLoad();
    }

    private void lazyLoad() {
        if (!isVisible || !isPrepared) {
            return;
        }

    }

    private void registerBroadcast(Context context, BroadcastReceiver receiver) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("发送消息");
        filter.addAction("更新消息");
        getContext().registerReceiver(receiver,filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(receiver);
    }

}
