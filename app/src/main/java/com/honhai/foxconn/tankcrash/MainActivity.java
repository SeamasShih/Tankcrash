package com.honhai.foxconn.tankcrash;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.honhai.foxconn.tankcrash.Network.ReceiveListener;
import com.honhai.foxconn.tankcrash.Network.TankClient;

public class MainActivity extends AppCompatActivity implements ReceiveListener {

    private final String TAG = "MainActivity";
    EditText editText;
    Button button;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        findViews();
        setListener();
    }

    private void setListener() {
        button.setOnClickListener(v -> {
            String s = String.valueOf(editText.getText());
            String serverIp = "192.168.1.37";
            int port = 9487;

            TankClient tankClient =  new TankClient(this, serverIp, port);
            tankClient.sendMessage("from tank client");
        });
    }

    private void findViews() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }

    @Override
    public void onMessageReceive(String message) {
        Log.d(TAG, "onMessageReceive: message : " + message);
    }
}
