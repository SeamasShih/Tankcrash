package com.honhai.foxconn.tankcrash;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.honhai.foxconn.tankcrash.buttonview.DirectorKey;
import com.honhai.foxconn.tankcrash.buttonview.FireKey;
import com.honhai.foxconn.tankcrash.buttonview.RaiseKey;
import com.honhai.foxconn.tankcrash.buttonview.TurnKey;
import com.honhai.foxconn.tankcrash.network.TcpReceiveListener;
import com.honhai.foxconn.tankcrash.network.TcpSerCliConstant;
import com.honhai.foxconn.tankcrash.network.TcpTankClient;
import com.honhai.foxconn.tankcrash.network.UdpReceiveListener;
import com.honhai.foxconn.tankcrash.network.UdpSerCliConstant;
import com.honhai.foxconn.tankcrash.network.UdpTankClient;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.TankPrototype;
import com.honhai.foxconn.tankcrash.tankdrawable.tank.HeightTank;

import java.util.StringTokenizer;

public class GameActivity extends AppCompatActivity implements UdpReceiveListener, TcpReceiveListener {
    private final String TAG = "GameActivity";
    GameSurfaceView surfaceView;
    ConstraintLayout mainLayout;
    DirectorKey upKey, downKey, leftKey, rightKey;
    FireKey fireKey;
    RaiseKey raiseKey, lowerKey;
    TurnKey turnLeftKey, turnRightKey;
    GameData gameData = GameData.getInstance();
    UdpTankClient udpTankClient = UdpTankClient.getClient(this);
    TcpTankClient tcpTankClient = TcpTankClient.getClient(this);
    int bulletOrder = 0;

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
        if (tank != null) {
            switch (tank.getType()) {
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
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
        });
        downKey.setOnClickListener(v -> {
            gameData.getMyself().goDown();
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
        });
        leftKey.setOnClickListener(v -> {
            gameData.getMyself().goLeft();
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
        });
        rightKey.setOnClickListener(v -> {
            gameData.getMyself().goRight();
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
        });
        turnLeftKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunLeft();
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_DIR + gameData.getMyOrder() + gameData.getMySiteString());
        });
        turnRightKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunRight();
            udpTankClient.sendMessage(UdpSerCliConstant.C_TANK_DIR + gameData.getMyOrder() + gameData.getMySiteString());
        });
        fireKey.setOnClickListener(v -> {
            int order = gameData.getMyOrder()*10 + bulletOrder;
            int rotation = gameData.getMyself().getTank().getGunRotation();
            float x = 0,y = 0;
            switch ((rotation+360)%360){
                case 0:
                    x = gameData.getMyself().getSite()[0];
                    y = gameData.getMyself().getSite()[1]-1;
                    break;
                case 90:
                    x = gameData.getMyself().getSite()[0]+1;
                    y = gameData.getMyself().getSite()[1];
                    break;
                case 180:
                    x = gameData.getMyself().getSite()[0];
                    y = gameData.getMyself().getSite()[1]+1;
                    break;
                case 270:
                    x = gameData.getMyself().getSite()[0]-1;
                    y = gameData.getMyself().getSite()[1];
                    break;
            }
            gameData.shoot(order,x,y);
            bulletOrder = (bulletOrder+1)%10;

            tcpTankClient.sendMessage(TcpSerCliConstant.C_FIRE + gameData.getMyOrder());
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
        udpTankClient.sendMessage(UdpSerCliConstant.C_INITIAL_TANK_DATA);
    }

    @Override
    public void onUdpMessageReceive(String message) {
        Log.d(TAG, "onUdpMessageReceive: message : " + message);

        if (message.startsWith(UdpSerCliConstant.C_INITIAL_TANK_DATA)) {
            for (int i = 0; i < gameData.getPlayerAmount(); i++) {
                int tank = Character.getNumericValue(message.charAt(UdpSerCliConstant.C_INITIAL_TANK_DATA.length() + i));
                gameData.getPlayer(i).setTank(tank);
            }
        } else if (message.startsWith(UdpSerCliConstant.C_TANK_SITE)) {
            StringTokenizer tokenizer = new StringTokenizer(message, " ");

            int order = Character.getNumericValue(tokenizer.nextToken().charAt(UdpSerCliConstant.C_TANK_SITE.length()));
            float x = Float.valueOf(tokenizer.nextToken());
            float y = Float.valueOf(tokenizer.nextToken());
            gameData.setTankSite(order, x, y);
        }
    }

    @Override
    public void onTcpMessageReceive(String message) {
        Log.d(TAG, "onTcpMessageReceive: message : " + message);
    }
}
