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

public class GameActivity extends AppCompatActivity {
    GameSurfaceView surfaceView;
    ConstraintLayout mainLayout;
    DirectorKey upKey, downKey, leftKey, rightKey;
    FireKey fireKey;
    RaiseKey raiseKey, lowerKey;
    TurnKey turnLeftKey , turnRightKey;
    GameData gameData = GameData.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        inflateButtonView();
        findKeysViews();
        setListener();
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
        upKey.setOnClickListener(v -> gameData.getMyself().goUp());
        downKey.setOnClickListener(v -> gameData.getMyself().goDown());
        leftKey.setOnClickListener(v -> gameData.getMyself().goLeft());
        rightKey.setOnClickListener(v -> gameData.getMyself().goRight());
        turnLeftKey.setOnClickListener(v -> gameData.getMyself().getTank().turnGunLeft());
        turnRightKey.setOnClickListener(v -> gameData.getMyself().getTank().turnGunRight());
    }

    private void inflateButtonView() {
        View.inflate(this,R.layout.game_button_layout,mainLayout);
    }

    private void findViews() {
        mainLayout = findViewById(R.id.gameLayout);
        surfaceView = findViewById(R.id.surfaceView);
    }
}
