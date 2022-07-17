package com.application.foggy.dashboard;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.foggy.R;
import com.application.foggy.api.ApiInstance;
import com.application.foggy.api.ApiRepository;
import com.application.foggy.constants.Constants;
import com.application.foggy.databinding.ActivityDashboardBinding;
import com.application.foggy.loadingspinner.LoadingSpinner;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    private GoogleSignInOptions googleSignInOptions;
    private GoogleSignInClient googleSignInClient;
    private NavigationView navigationView;
    private TextView displayName;
    private TextView email;
    private ImageView userPhoto;

    private void initInstanceVariables() {
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        displayName = headerView.findViewById(R.id.displayName);
        email = headerView.findViewById(R.id.email);
        userPhoto = headerView.findViewById(R.id.userPhoto);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarDashboard.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        LoadingSpinner.dismissIf();
        initInstanceVariables();
        initMethods();
    }

    private void initMethods() {
        verifyGoogleLoggedIn();
        initLogout();
//        retrofitCheck();
    }

   /* private void retrofitCheck() {
        ApiRepository apiRepository = ApiInstance.getInstance().create(ApiRepository.class);
        Call<List<Sample>> posts = apiRepository.getPosts();
        posts.enqueue(new Callback<List<Sample>>() {
            @Override
            public void onResponse(Call<List<Sample>> call, Response<List<Sample>> response) {

            }

            @Override
            public void onFailure(Call<List<Sample>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }*/

    private void initLogout() {
        MenuItem item = navigationView.getMenu().findItem(R.id.nav_logout);
        item.setOnMenuItemClickListener(menuItem -> {
            Task<Void> voidTask = googleSignInClient.signOut();
            try {
                voidTask.getResult(ApiException.class);
                finish();
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return true;
        });
    }

    private void verifyGoogleLoggedIn() {
        GoogleSignInAccount lastSignedInAccount = GoogleSignIn.getLastSignedInAccount(this);
        if (lastSignedInAccount != null) {
            String _displayName = lastSignedInAccount.getDisplayName();
            String _email = lastSignedInAccount.getEmail();
            String _id = lastSignedInAccount.getId();
            String _token = lastSignedInAccount.getIdToken();
            Uri _photoUrl = lastSignedInAccount.getPhotoUrl();
            Constants.setToken(_token);
            displayName.setText(_displayName);
            email.setText(_email);
            Glide.with(this).load(_photoUrl).into(userPhoto);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}