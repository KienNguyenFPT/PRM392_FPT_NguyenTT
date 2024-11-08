package com.example.onlinepayment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinepayment.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edQuantity;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConfirm = findViewById(R.id.btnConfirm);
        edQuantity = findViewById(R.id.edQuantity);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edQuantity.getText() == null || edQuantity.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Hãy nhập số lượng muốn mua", Toast.LENGTH_LONG).show();
                    return;
                }

                String quantityString = edQuantity.getText().toString();
                double total = Double.parseDouble(quantityString) * (double) 1000000;
                Intent intent = new Intent(MainActivity.this, OrderPayment.class);
                intent.putExtra("quantity", edQuantity.getText().toString());
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });
    }


}