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

import com.honhai.foxconn.tankcrash.ChoiceView.HeavyTank;
import com.honhai.foxconn.tankcrash.ChoiceView.LightTank;
import com.honhai.foxconn.tankcrash.ChoiceView.HeightTank;
import com.honhai.foxconn.tankcrash.Network.ReceiveListener;
import com.honhai.foxconn.tankcrash.Network.ServerClientConstant;
import com.honhai.foxconn.tankcrash.Network.TankClient;

public class ChoiceActivity extends AppCompatActivity implements ReceiveListener {

    private final String TAG = "ChoiceActivity";

    private TankClient tankClient;
    LightTank lightTank;
    HeavyTank heavyTank;
    HeightTank heightTank;
    Button button;
    TextView textView;
    int tank = -1; // light = 0 , heavy = 1 , height = 2
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
        tankClient.sendMessage(ServerClientConstant.CLIENT_REGISTER);
    }

    private void setListener() {
        lightTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            heavyTank.setBackground(null);
            heightTank.setBackground(null);
            tank = 0;
        });
        heavyTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            lightTank.setBackground(null);
            heightTank.setBackground(null);
            tank = 1;
        });
        heightTank.setOnClickListener(v -> {
            v.setBackgroundResource(R.drawable.choice_background);
            lightTank.setBackground(null);
            heavyTank.setBackground(null);
            tank = 2;
        });
        button.setOnClickListener(v -> {
            tankClient.sendMessage(ServerClientConstant.CLIENT_READY + gameData.getMyOrder() + tank);
            button.setClickable(false);
        });
    }

    private void findViews() {
        lightTank = findViewById(R.id.lightTank);
        heavyTank = findViewById(R.id.heavyTank);
        heightTank = findViewById(R.id.heightTank);
        button = findViewById(R.id.battle);
        textView = findViewById(R.id.text);
    }

    @Override
    public void onMessageReceive(String message) {
        if (message.startsWith(ServerClientConstant.CLIENT_ORDER)) {
            if (gameData.getMyOrder() == -1) {
                int order = Character.getNumericValue(message.charAt(1));
                gameData.setMyOrder(order);
            }

            String s = getResources().getString(R.string.player) + message.substring(2);
            runOnUiThread(() -> textView.setText(s));
        } else if (message.startsWith(ServerClientConstant.SERVER_START_GAME)) {
            gameData.setPlayerAmount(
                    Character.getNumericValue(message.charAt(ServerClientConstant.SERVER_START_GAME.length())));

            Intent intent = new Intent();
            intent.setClass(this,GameActivity.class);
            startActivity(intent);
        }
    }
}
