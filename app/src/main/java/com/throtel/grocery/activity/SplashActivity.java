package com.throtel.grocery.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import books.activity.HomeBookActivity;
import books.fcm.Config;
import books.models.UserBookDetail;
import com.throtel.grocery.activity.HomeActivity;

import androidx.core.content.ContextCompat;

import com.throtel.grocery.R;
import com.throtel.grocery.models.UserDetail;

public class SplashActivity extends BaseActivity {
    Handler handler;
    Intent intent;
    private UserDetail userDetail;
    private UserBookDetail userBookDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.white)); // Navigation bar the soft bottom of some phones like nexus and some Samsung note series
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white)); //status bar or the time bar at the top
        }
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (localData.isLoggedIn()) {
//                    if (localData.isBookLoggedIn()) {
//                        Log.d("BAG", "T or F..." + localData.isLoggedIn());
//                        intent = new Intent(SplashActivity.this, HomeBookActivity.class);
//                    } else {
//                        intent = new Intent(SplashActivity.this, HomeActivity.class);
//                    }
//                }
//                else {
//                    //localData.setBookSignIn(userBookDetail);
//                    intent = new Intent(SplashActivity.this, Choosemodule.class);
//                }
if(localData.isLoggedIn()){
                    intent = new Intent(SplashActivity.this, HomeActivity.class);
                }
else if(localData.isBookLoggedIn()){
    intent = new Intent(SplashActivity.this, HomeBookActivity.class);
                }
else{
    intent = new Intent(SplashActivity.this, Choosemodule.class);
}
                startActivity(intent);
                finish();
            }
        }, 3000);

    }


}


