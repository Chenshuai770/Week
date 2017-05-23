package com.cs.week.modle.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.cs.week.R;
import com.cs.week.entry.DateRegistResult;
import com.cs.week.views.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mPassword;
    private Button mEmailSignInButton;
    private ScrollView mLoginForm;
    private EditText mEmail;
    private Button mEmailRegisteredButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        mPassword = (EditText) findViewById(R.id.password);
        mEmail = (EditText) findViewById(R.id.email);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mLoginForm = (ScrollView) findViewById(R.id.login_form);
        mEmailSignInButton.setOnClickListener(this);
        mEmailRegisteredButton = (Button) findViewById(R.id.email_registered_button);
        mEmailRegisteredButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.email_sign_in_button:
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入账号和密码");
                } else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入账号");
                } else if (!TextUtils.isEmpty(email) && TextUtils.isEmpty(password)) {
                    ToastUtils.showToast(getApplicationContext(), "请输入密码");
                } else {
                    List<DateRegistResult> all = DataSupport.findAll(DateRegistResult.class);
                    for (int i = 0; i < all.size(); i++) {
                        if (all.get(i).getName().equals(email)&&all.get(i).getPassword().equals(password)) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
                break;
            case R.id.email_registered_button:
                Intent intent = new Intent(LoginActivity.this, RegisteredActivity.class);
                startActivity(intent);
                break;
        }
    }


}

