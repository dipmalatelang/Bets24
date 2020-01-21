package com.app.bet.Login;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.bet.R;

public class SplashScreenActivity extends AppCompatActivity {


    ImageView imgLogo;
    Animation anim;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashsreen);

        imgLogo = findViewById(R.id.imgLogo);
        anim = AnimationUtils.loadAnimation(this, R.anim.blink);

    }
    @Override
    protected void onResume () {
        super.onResume();
        imgLogo.startAnimation(anim);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class), ActivityOptions.makeSceneTransitionAnimation(SplashScreenActivity.this, imgLogo, "imgLogo").toBundle());
                }
                else {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 1000);

    }
}