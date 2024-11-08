package com.example.lab6;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class lab63Activity extends AppCompatActivity {
    private Button btnChonMau;
    ConstraintLayout manHinh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab63);
        btnChonMau =(Button) findViewById(R.id.buttonChonmau);
        manHinh=(ConstraintLayout) findViewById(R.id.manHinh);
        registerForContextMenu(btnChonMau);
        btnChonMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContextMenu(btnChonMau);
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem menuitem){
        if (menuitem.getItemId() == R.id.action_option1) {
            manHinh.setBackgroundColor(Color.RED);
        } else if (menuitem.getItemId() == R.id.action_option2) {
            manHinh.setBackgroundColor(Color.YELLOW);
        } else if (menuitem.getItemId() == R.id.action_option3) {
            manHinh.setBackgroundColor(Color.BLACK);
        }
        return false;
    }

}




