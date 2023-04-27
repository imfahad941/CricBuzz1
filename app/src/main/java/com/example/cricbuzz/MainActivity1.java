package com.example.cricbuzz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        BottomNavigationView bottomNavigationView;
        ScoreFragment scoreFragment=new ScoreFragment();
        CommentaryFragment commentaryFragment=new CommentaryFragment();
        SquadFragment squadFragment=new SquadFragment();
            Intent intent=getIntent();
            String id=intent.getStringExtra("id");
            Log.d("MainActivity", "id received: " + id);
            bottomNavigationView=findViewById(R.id.bottom_navigation1);
            Bundle bundle=new Bundle();
            bundle.putString("id",id);
            Log.d("MainActivity1", "id sent: " + id);
            scoreFragment.setArguments(bundle);
            commentaryFragment.setArguments(bundle);
            squadFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.container,scoreFragment).commit();
            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {

                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.score:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container, scoreFragment).commit();
                            return true;
                        case R.id.comment:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,commentaryFragment).commit();
                        return true;
                        case R.id.squad:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,squadFragment).commit();
                        return true;
                    }
                    return false;
                }
            });
        }
    }
