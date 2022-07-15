package com.application.foggy.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.application.foggy.R;
import com.application.foggy.dashboard.DashboardActivity;
import com.google.android.gms.common.SignInButton;

public class LoginPageActivity extends AppCompatActivity {

    private SignInButton googleLoginBtn;

    private void initInstanceVariable() {
        googleLoginBtn = findViewById(R.id.google_login_btn);
    }

    private void initMethods() {
        loginBtnAction();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initInstanceVariable();
        initMethods();
    }

    private void loginBtnAction() {
        googleLoginBtn.setOnClickListener(view -> {
            startActivity(new Intent(this, DashboardActivity.class));
        });
    }

}