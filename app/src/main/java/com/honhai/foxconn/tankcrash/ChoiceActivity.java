package com.honhai.foxconn.tankcrash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.honhai.foxconn.tankcrash.ChoiceView.HeavyTank;
import com.honhai.foxconn.tankcrash.ChoiceView.LightTank;
import com.honhai.foxconn.tankcrash.ChoiceView.HeightTank;

public class ChoiceActivity extends AppCompatActivity {

    LightTank lightTank;
    HeavyTank heavyTank;
    HeightTank heightTank;
    Button button;
    TextView textView;
    GameData gameData = GameData.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
    }

    private void setListener() {
        lightTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            heavyTank.setBackground(null);
            heightTank.setBackground(null);
        });
        heavyTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            lightTank.setBackground(null);
            heightTank.setBackground(null);
        });
        heightTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            lightTank.setBackground(null);
            heavyTank.setBackground(null);
        });
        button.setOnClickListener(v -> {
            //todo Ian sent info to server
            Intent intent = new Intent();
            intent.setClass(this,GameActivity.class);
            startActivity(intent);
        });
        textView.setOnClickListener(v -> {
            //todo Ian change player amount by receive from server
        });
    }

    private void findViews() {
        lightTank = findViewById(R.id.lightTank);
        heavyTank = findViewById(R.id.heavyTank);
        heightTank = findViewById(R.id.heightTank);
        button = findViewById(R.id.battle);
        textView = findViewById(R.id.text);
    }
}
