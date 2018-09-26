package com.honhai.foxconn.tankcrash;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.honhai.foxconn.tankcrash.ButtonView.DirectorKey;
import com.honhai.foxconn.tankcrash.ButtonView.FireKey;
import com.honhai.foxconn.tankcrash.ButtonView.RaiseKey;
import com.honhai.foxconn.tankcrash.ButtonView.TurnKey;
import com.honhai.foxconn.tankcrash.Network.ReceiveListener;
import com.honhai.foxconn.tankcrash.Network.ServerClientConstant;
import com.honhai.foxconn.tankcrash.Network.TankClient;
import com.honhai.foxconn.tankcrash.Tank.Prototype.TankPrototype;
import com.honhai.foxconn.tankcrash.Tank.Tank.HeightTank;

import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements ReceiveListener {
    private final String TAG = "GameActivity";
    GameSurfaceView surfaceView;
    ConstraintLayout mainLayout;
    DirectorKey upKey, downKey, leftKey, rightKey;
    FireKey fireKey;
    RaiseKey raiseKey, lowerKey;
    TurnKey turnLeftKey, turnRightKey;
    GameData gameData = GameData.getInstance();
    TankClient tankClient = TankClient.getTankClient(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        inflateButtonView();
        findKeysViews();
        setTankKey();
        setListener();
        setClientInfo();
    }

    private void setTankKey() {
        TankPrototype tank = gameData.getMyself().getTank();
        if (tank != null){
            switch (tank.getType()){
                case 0:
                case 1:
                    raiseKey.setVisibility(View.INVISIBLE);
                    lowerKey.setVisibility(View.INVISIBLE);
                    break;
                case 2:
                    raiseKey.setOnClickListener(v -> {
                        ((HeightTank) gameData.getMyself().getTank()).raiseGun();
                        //todo Ian send to server some data
                    });
                    lowerKey.setOnClickListener(v -> {
                        ((HeightTank) gameData.getMyself().getTank()).lowerGun();
                        //todo Ian send to server some data
                    });
                    break;
            }
        }
    }

    private void findKeysViews() {
        upKey = findViewById(R.id.upKey);
        downKey = findViewById(R.id.downKey);
        leftKey = findViewById(R.id.leftKey);
        rightKey = findViewById(R.id.rightKey);
        fireKey = findViewById(R.id.fireKey);
        raiseKey = findViewById(R.id.raiseKey);
        lowerKey = findViewById(R.id.lowerKey);
        turnLeftKey = findViewById(R.id.turnLeftKey);
        turnRightKey = findViewById(R.id.turnRightKey);
    }

    private void setListener() {
        upKey.setOnClickListener(v -> {
            gameData.getMyself().goUp();
            tankClient.sendMessage(gameData.getMyOrder() + "s" + Arrays.toString(gameData.getMySite()));
        });
        downKey.setOnClickListener(v -> {
            gameData.getMyself().goDown();
            tankClient.sendMessage(gameData.getMyOrder() + "s" + Arrays.toString(gameData.getMySite()));
        });
        leftKey.setOnClickListener(v -> {
            gameData.getMyself().goLeft();
            tankClient.sendMessage(gameData.getMyOrder() + "s" + Arrays.toString(gameData.getMySite()));
        });
        rightKey.setOnClickListener(v -> {
            gameData.getMyself().goRight();
            tankClient.sendMessage(gameData.getMyOrder() + "s" + Arrays.toString(gameData.getMySite()));
        });
        turnLeftKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunLeft();
            tankClient.sendMessage(gameData.getMyOrder() + "d" + Arrays.toString(gameData.getMySite()));
        });
        turnRightKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunRight();
            tankClient.sendMessage(gameData.getMyOrder() + "d" + Arrays.toString(gameData.getMySite()));
        });
    }

    private void inflateButtonView() {
        View.inflate(this, R.layout.game_button_layout, mainLayout);
    }

    private void findViews() {
        mainLayout = findViewById(R.id.gameLayout);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void setClientInfo() {
        tankClient.sendMessage(ServerClientConstant.CLIENT_INITIAL_DATA);
    }

    @Override
    public void onMessageReceive(String message) {
        Log.d(TAG, "onMessageReceive: message : " + message);

        if (message.startsWith(ServerClientConstant.CLIENT_TANK_DATA)) {
            for (int i = 0; i < gameData.getPlayerAmount(); i++) {
                int tank = Character.getNumericValue(message.charAt(ServerClientConstant.CLIENT_TANK_DATA.length() + i));
                gameData.getPlayer(i).setTank(tank);
            }
        }
    }
}
