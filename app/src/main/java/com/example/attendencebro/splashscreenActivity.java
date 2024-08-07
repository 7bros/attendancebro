package com.example.attendencebro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.window.SplashScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.attendencebro.dashBoard.dashboard;

public class splashscreenActivity extends Activity {
        private static final int SPLASH_TIME_OUT = 4000;

        private Animation animFadeIn, animTranslation, animRightIn, animBottomUp, animTopDown;
        private ImageView logo, riteLogo;
        private TextView text, lowerText, versionText;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.fragment_splashscreen);


            initViews();
            setupAnimations();
            navigateToDashboard();
        }

        private void initViews() {
            logo = findViewById(R.id.splashscreenImg);
            text = findViewById(R.id.splashscreenTxt);
            lowerText = findViewById(R.id.splashscreenLowerTxt);
            versionText = findViewById(R.id.versionTxt);
            riteLogo = findViewById(R.id.ivRLogo);
        }

        private void setupAnimations() {
            animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
            animTranslation = AnimationUtils.loadAnimation(this, R.anim.translate);
            animRightIn = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
            animBottomUp = AnimationUtils.loadAnimation(this, R.anim.bottomup);
            animTopDown = AnimationUtils.loadAnimation(this, R.anim.topdown);

            logo.setAnimation(animTopDown);
            text.setAnimation(animTopDown);
            lowerText.setAnimation(animBottomUp);
            riteLogo.setAnimation(animBottomUp);
            versionText.setAnimation(animTopDown);
        }

        private void navigateToDashboard() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(splashscreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }

