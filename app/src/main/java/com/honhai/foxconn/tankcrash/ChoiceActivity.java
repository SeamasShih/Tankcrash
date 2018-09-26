package com.honhai.foxconn.tankcrash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.honhai.foxconn.tankcrash.ChoiceView.LightTank;
import com.honhai.foxconn.tankcrash.Network.ReceiveListener;
import com.honhai.foxconn.tankcrash.Network.TankClient;

public class ChoiceActivity extends AppCompatActivity implements ReceiveListener {

    private final String TAG = "ChoiceActivity";

    private TankClient tankClient;
    LightTank lightTank;
    Button button;
    TextView textView;
    GameData gameData = GameData.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choice_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setTankClient();
        setListener();
    }

    private void setTankClient() {
        tankClient = TankClient.getTankClient(this);
        tankClient.sendMessage("client register");
    }

    private void setListener() {
        lightTank.setOnClickListener(v -> lightTank.setBackgroundResource(R.drawable.choice_background));
        button.setOnClickListener(v -> {
            tankClient.sendMessage();

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
        button = findViewById(R.id.battle);
        textView = findViewById(R.id.text);
    }

    @Override
    public void onMessageReceive(String message) {
        Log.d(TAG, "onMessageReceive: implement in ChoiceActivity");
    }
}
