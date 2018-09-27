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
import com.honhai.foxconn.tankcrash.network.ReceiveListener;
import com.honhai.foxconn.tankcrash.network.SerCliConstant;
import com.honhai.foxconn.tankcrash.network.TankClient;
import com.honhai.foxconn.tankcrash.tankdrawable.prototype.TankPrototype;
import com.honhai.foxconn.tankcrash.tankdrawable.tank.HeightTank;

import java.util.StringTokenizer;

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
            if (checkCanGo(0)) {
                Log.d("Seamas","gogogo");
                gameData.getMyself().goUp();
                tankClient.sendMessage(SerCliConstant.CLI_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
            }
        });
        downKey.setOnClickListener(v -> {
            if (checkCanGo(2)) {
                gameData.getMyself().goDown();
                tankClient.sendMessage(SerCliConstant.CLI_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
            }
        });
        leftKey.setOnClickListener(v -> {
            if (checkCanGo(3)) {
                gameData.getMyself().goLeft();
                tankClient.sendMessage(SerCliConstant.CLI_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
            }
        });
        rightKey.setOnClickListener(v -> {
            if (checkCanGo(1)) {
                gameData.getMyself().goRight();
                tankClient.sendMessage(SerCliConstant.CLI_TANK_SITE + gameData.getMyOrder() + gameData.getMySiteString());
            }
        });
        turnLeftKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunLeft();
            tankClient.sendMessage(SerCliConstant.CLI_TANK_DIR + gameData.getMyOrder() + gameData.getMySiteString());
        });
        turnRightKey.setOnClickListener(v -> {
            gameData.getMyself().getTank().turnGunRight();
            tankClient.sendMessage(SerCliConstant.CLI_TANK_DIR + gameData.getMyOrder() + gameData.getMySiteString());
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
            //todo Ian send bullet data to server
        });
    }

    private boolean checkCanGo(int direction){
        int x = (int)gameData.getMyself().getSite()[0];
        int y = (int)gameData.getMyself().getSite()[1];
        switch (direction){
            case 0: //up
                if (gameData.getMap(x,y-1) == MapData.TEST_ROAD)
                    return true;
                break;
            case 1: //right
                if (gameData.getMap(x+1,y) == MapData.TEST_ROAD)
                    return true;
                break;
            case 2: // down
                if (gameData.getMap(x,y+1) == MapData.TEST_ROAD)
                    return true;
                break;
            case 3: //left
                if (gameData.getMap(x-1,y) == MapData.TEST_ROAD)
                    return true;
                break;
        }
        return false;
    }

    private void inflateButtonView() {
        View.inflate(this, R.layout.game_button_layout, mainLayout);
    }

    private void findViews() {
        mainLayout = findViewById(R.id.gameLayout);
        surfaceView = findViewById(R.id.surfaceView);
    }

    private void setClientInfo() {
        tankClient.sendMessage(SerCliConstant.CLI_INITIAL_TANK_DATA);
    }

    @Override
    public void onMessageReceive(String message) {
        if (message.startsWith(SerCliConstant.CLI_INITIAL_TANK_DATA)) {
            for (int i = 0; i < gameData.getPlayerAmount(); i++) {
                int tank = Character.getNumericValue(message.charAt(SerCliConstant.CLI_INITIAL_TANK_DATA.length() + i));
                gameData.getPlayer(i).setTank(tank);
            }
        } else if (message.startsWith(SerCliConstant.CLI_TANK_SITE)) {
            StringTokenizer tokenizer = new StringTokenizer(message, " ");

            int order = Character.getNumericValue(tokenizer.nextToken().charAt(SerCliConstant.CLI_TANK_SITE.length()));
            float x = Float.valueOf(tokenizer.nextToken());
            float y = Float.valueOf(tokenizer.nextToken());
            gameData.setTankSite(order, x, y);
        }
    }
}
