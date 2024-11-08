package com.example.lab6;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import androidx.appcompat.app.AppCompatActivity;

// MainActivity.java
public class lab62Activity extends AppCompatActivity {
    Button btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lab62);

        btnMenu=findViewById(R.id.buttonMenu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowMenu();
            }
        });
    }

    private void ShowMenu(){
        PopupMenu popupMenu= new PopupMenu(this,btnMenu);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuitem) {
                if (menuitem.getItemId() == R.id.action_option1) {
                    btnMenu.setText("Menu Them");
                } else if (menuitem.getItemId() == R.id.action_option2) {
                    btnMenu.setText("Menu Sua");
                } else if (menuitem.getItemId() == R.id.action_option3) {
                    btnMenu.setText("Menu Xoa");
                }
                return false;
            }

        });
    }


}

