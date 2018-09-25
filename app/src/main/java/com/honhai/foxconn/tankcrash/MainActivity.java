package com.honhai.foxconn.tankcrash;

import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.honhai.foxconn.tankcrash.ButtonView.DirectorKey;
import com.honhai.foxconn.tankcrash.ButtonView.FireKey;
import com.honhai.foxconn.tankcrash.ButtonView.RaiseKey;
import com.honhai.foxconn.tankcrash.ButtonView.TurnKey;

public class MainActivity extends AppCompatActivity {

    GameSurfaceView surfaceView;
    ConstraintLayout mainLayout;
    DirectorKey upKey, downKey, leftKey, rightKey;
    FireKey fireKey;
    RaiseKey raiseKey, lowerKey;
    TurnKey turnLeftKey , turnRightKey;
    GameData gameData = GameData.getInstance();

    final String TAG = "Seamas";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        inflateButtonView();
        findKeysViews();
        setSurfaceView();
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
        turnLeftKey.setOnClickListener(v -> gameData.getMyself().getTank().turnGunLeft());
        turnRightKey.setOnClickListener(v -> gameData.getMyself().getTank().turnGunRight());
    }

    private void setSurfaceView() {
    }

    private void inflateButtonView() {
        View.inflate(this,R.layout.game_button_layout,mainLayout);
    }

    private void findViews() {
        mainLayout = findViewById(R.id.mainLayout);
        surfaceView = findViewById(R.id.surfaceView);
    }
}
