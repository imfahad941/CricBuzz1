package com.example.cricbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView=findViewById(R.id.img);

        Glide.with(this).asGif().load(R.raw.cricket).into(imageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(),Register.class));
            }
        },3500
        );



    }
}