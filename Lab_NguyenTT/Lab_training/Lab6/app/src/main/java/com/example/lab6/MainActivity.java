package com.example.lab6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button buttonLab61, buttonLab62, buttonLab63;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonLab61 = (Button) findViewById(R.id.buttonLab61);
        buttonLab62 = (Button) findViewById(R.id.buttonLab62);
        buttonLab63 = (Button) findViewById(R.id.buttonLab63);
        buttonLab61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab61Activity.class);
                startActivity(intent);
            }
        });
        buttonLab62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab62Activity.class);
                startActivity(intent);
            }
        });
        buttonLab63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, lab63Activity.class);
                startActivity(intent);
            }
        });
    }
}

