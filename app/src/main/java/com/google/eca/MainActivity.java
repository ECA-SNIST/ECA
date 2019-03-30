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
    //Animation animation;
    Handler h;


       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        e = findViewById(R.id.emerge);
        c = findViewById(R.id.computing);
        a = findViewById(R.id.arena);
        logo = findViewById(R.id.splash_logo);

//        animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.anima);
//
        h = new Handler();
//        h.postDelayed(r1,1000);

        h.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(MainActivity.this,Login.class));

            }
        },2000);

    }
}
