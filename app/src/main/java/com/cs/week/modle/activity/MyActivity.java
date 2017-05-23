package com.cs.week.modle.activity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.cs.week.R;

public class MyActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private android.os.Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 加载指定音乐，并为之创建MediaPlayer对象
        mediaPlayer=MediaPlayer.create(this, R.raw.in_call_alarm);

        // 播放音乐
        mediaPlayer.start();
        // 创建一个对话框
        new AlertDialog.Builder(MyActivity.this).setTitle("闹钟")
                .setMessage("闹钟响了，起床啦，懒虫！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 停止音乐播放
                        mediaPlayer.stop();
                        // 结束该Activity
                        MyActivity.this.finish();
                    }
                }).show();

    }

}
