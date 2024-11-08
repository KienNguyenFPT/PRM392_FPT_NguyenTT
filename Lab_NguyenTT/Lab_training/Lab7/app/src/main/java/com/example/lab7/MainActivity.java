package com.example.lab7;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.Manifest;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_PERMISSION_CODE = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnrequestpermission = (Button) findViewById(R.id.btnRequestPermission);
        Button btnopensettingpermission = (Button) findViewById(R.id.btnOpenSettings);
        btnrequestpermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickRequestPermission();
            }
        });
        btnopensettingpermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAppPermissionSettings();
            }
        });

    }
    private void openAppPermissionSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private void ClickRequestPermission(){
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.M){
            return;
        }
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
        }else {
            String permission = (Manifest.permission.ACCESS_FINE_LOCATION);
            requestPermissions(new String[]{permission}, REQUEST_PERMISSION_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_SHORT).show();

            }
        }
    }
}