package com.honhai.foxconn.tankcrash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.honhai.foxconn.tankcrash.ChoiceView.LightTank;

public class ChoiceActivity extends AppCompatActivity {

    LightTank lightTank;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
    }

    private void setListener() {
        lightTank.setOnClickListener(v -> lightTank.setBackgroundResource(R.drawable.choice_background));
        button.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setClass(this,GameActivity.class);
            startActivity(intent);
        });
    }

    private void findViews() {
        lightTank = findViewById(R.id.lightTank);
        button = findViewById(R.id.battle);
    }
}
