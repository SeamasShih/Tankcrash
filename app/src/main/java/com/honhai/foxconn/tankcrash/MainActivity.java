package com.honhai.foxconn.tankcrash;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

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

            Intent intent = new Intent();
            intent.setClass(this,ChoiceActivity.class);
            startActivity(intent);
        });
    }

    private void findViews() {
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
    }
}
