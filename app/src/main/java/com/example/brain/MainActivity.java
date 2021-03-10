package com.example.brain;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim, sideAnim;
    ImageView logo;
    TextView name, slogan;
    private  static int Splash_time=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        logo = findViewById(R.id.imageView);
        name = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);



        logo.setAnimation(bottomAnim);
        name.setAnimation(sideAnim);
        slogan.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent in = new Intent(MainActivity.this, login.class);
                Pair[]pairs=new Pair[2];
                pairs[0]=new Pair<View, String>(logo,"logo_image");
                pairs[1]=new Pair<View, String>(name,"logo_text");
                ActivityOptions options=ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);

                startActivity(in,options.toBundle());
                finish();
            }
        }, Splash_time);
    }
}
