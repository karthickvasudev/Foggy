package com.application.foggy.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.constants.Roles;
import com.application.foggy.dashboard.DashboardActivity;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.application.foggy.modals.LoginSignupModal;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPageActivity extends AppCompatActivity {

    private SignInButton googleLoginBtn;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private ConstraintLayout loginLayout;

    private void initInstanceVariable() {
        googleLoginBtn = findViewById(R.id.google_login_btn);
        loginLayout = findViewById(R.id.login_layout);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        initInstanceVariable();
        initMethods();
    }

    private void initMethods() {
        loginBtnAction();
        googleSignIn();
    }

    private void googleSignIn() {
        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("164846260896-0e34nt61sli435li3t69nrr98b23feh4.apps.googleusercontent.com")
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    private void loginBtnAction() {
        googleLoginBtn.setOnClickListener(view -> {
            LoadingSpinner.show(this);
            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, 200);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                GoogleSignInAccount result = task.getResult();
                LoginSignupModal signup = LoginSignupModal.builder()
                        .id(result.getId())
                        .name(result.getDisplayName())
                        .email(result.getEmail())
                        .role(Roles.USER)
                        .photoUrl(result.getPhotoUrl().toString())
                        .build();
                singupLoginCallApi(signup);
            } catch (ApiException e) {
                LoadingSpinner.dismissIf();
                Snackbar.make(loginLayout,"Login Failed",Snackbar.LENGTH_LONG).show();

            }
        }
    }

    private void singupLoginCallApi(LoginSignupModal signup) {
        Call<LoginSignupModal> api = ApiInstance.getApiRepository().getLoginSignupDetails(signup);
        api.enqueue(new Callback<LoginSignupModal>() {
            @Override
            public void onResponse(Call<LoginSignupModal> call, Response<LoginSignupModal> response) {
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            }
            @Override
            public void onFailure(Call<LoginSignupModal> call, Throwable t) {
                LoadingSpinner.dismissIf();
                Snackbar.make(loginLayout,"Login Failed",Snackbar.LENGTH_LONG).show();
            }
        });
    }
}