package com.cs.week.modle.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cs.week.R;
import com.cs.week.entry.DateRegistResult;
import com.cs.week.views.ToastUtils;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class RegisteredActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView mAemail;
    private EditText mApassword;
    private Button mRegisteredButton;
    private LinearLayout mEmailLoginForm;
    private Button mRegisteredDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);
        initView();
    }

    private void initView() {
        mAemail = (AutoCompleteTextView) findViewById(R.id.aemail);
        mApassword = (EditText) findViewById(R.id.apassword);
        mRegisteredButton = (Button) findViewById(R.id.registered_button);
        mRegisteredButton.setOnClickListener(this);
        mRegisteredDeleteButton = (Button) findViewById(R.id.registered_delete_button);
        mRegisteredDeleteButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registered_button:
                String email = mAemail.getText().toString();
                String password = mApassword.getText().toString();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入账号和密码");
                } else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入账号");
                } else if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入密码");
                } else {
                    Connector.getDatabase();
                    DateRegistResult dateRegistResult = new DateRegistResult();
                    dateRegistResult.setName(email);
                    dateRegistResult.setPassword(password);
                    List<DateRegistResult> all = DataSupport.findAll(DateRegistResult.class);
                    if (all.size() == 0) {
                        dateRegistResult.setTagid(1);
                    } else if (all.size() > 0) {
                        dateRegistResult.setTagid(all.size() + 1);
                    }
                    dateRegistResult.save();
                    if (dateRegistResult.isSaved()) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredActivity.this);
                        builder.setTitle("注册成功");
                        builder.setMessage("账号为:" + email + "\n" + "密码为:" + password);
                        builder.setPositiveButton("请用注册信息去登陆", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        });
                        builder.create().show();

                    } else {
                        Toast.makeText(this, "请输入正确注册信息", Toast.LENGTH_SHORT).show();
                    }

                }
                break;
            case R.id.registered_delete_button:

                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisteredActivity.this);
                builder.setMessage("是否删除所有注册数据库数据信息");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataSupport.deleteAll(DateRegistResult.class);
                        Toast.makeText(RegisteredActivity.this, "注册数据已全部删除", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       builder.create().cancel();
                    }
                });
                builder.create().show();

                break;
        }
    }


}
