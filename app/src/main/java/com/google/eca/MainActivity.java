package com.google.eca;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView e,c,a;
    ImageView logo;
    Animation animation;
    Handler h;


       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e = findViewById(R.id.emerge);
        c = findViewById(R.id.computing);
        a = findViewById(R.id.arena);
        logo = findViewById(R.id.splash_logo);

        h = new Handler();

        animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anima);

        e.setVisibility(View.VISIBLE);
        e.startAnimation(animation);

        c.setVisibility(View.VISIBLE);
        c.startAnimation(animation);

        a.setVisibility(View.VISIBLE);
        a.startAnimation(animation);

        logo.setVisibility(View.VISIBLE);
        logo.startAnimation(animation);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(MainActivity.this,Login.class));
                overridePendingTransition(R.anim.anima,R.anim.fade);
                finish();

            }
        },3000);

    }
}
