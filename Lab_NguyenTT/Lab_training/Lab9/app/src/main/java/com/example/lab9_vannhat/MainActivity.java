package com.example.lab9_vannhat;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.example.lab9_vannhat.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Database database;
    private ListView lvCongViec;
    private ArrayList<CongViec> arrayCongViec;
    private CongViecAdapter adapter;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

//        EdgeToEdge.enable(this);
//        setContentView(R.layout.activity_content_main);

        mapping();

        adapter = new CongViecAdapter(this, R.layout.dong_cong_viec, arrayCongViec);
        lvCongViec.setAdapter(adapter);

        //Tao database ghi chu
        database = new Database(this, "GhiChu.sqlite", null, 1);

        //Tao table
        database.QueryData("Create table if not exists CongViec(id Integer Primary Key Autoincrement," + "TenCV nvarchar(200))");

        //Insert
//        database.QueryData("Insert into CongViec values(null, 'Project Android')");
//        database.QueryData("Insert into CongViec values(null, 'Design app')");
//        Cursor dataCongviec = database.GetData("Select * from CongViec");
//        while (dataCongviec.moveToNext()) {
//            String ten = dataCongviec.getString(1);
//            int id = dataCongviec.getInt(0);
//            arrayCongViec.add(new CongViec(id, ten));
//        }
//        adapter.notifyDataSetChanged();
        GetDataCongViec();
    }
    private void mapping() {
        lvCongViec = findViewById(R.id.lvCongViec);
        arrayCongViec = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_congviec, menu);
        return super.onCreateOptionsMenu(menu);
    }
    private void GetDataCongViec() {
        //Select data
        Cursor dataCongviec = database.GetData("Select * from CongViec");
        // delete array before adding to update new data and avoiding duplicate
        arrayCongViec.clear();

        while (dataCongviec.moveToNext()) {
            String ten = dataCongviec.getString(1);
            int id = dataCongviec.getInt(0);
            arrayCongViec.add(new CongViec(id, ten));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menuAdd) {
            DialogThem();
        }
        return super.onOptionsItemSelected(item);
    }

    private void DialogThem() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_them_cong_viec);

        EditText edtTenCV = (EditText) dialog.findViewById(R.id.edtTenCV);
        Button btnAdd = (Button) dialog.findViewById(R.id.btnAdd);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenCV = edtTenCV.getText().toString().trim();
                // test string null
                if (tenCV.equals("")) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập tên công việc  !", Toast.LENGTH_SHORT).show();
                } else {
                    database.QueryData("Insert into CongViec values(null, '"+ tenCV +"')");
                    Toast.makeText(MainActivity.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    GetDataCongViec();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogSuaCongViec(String ten, int id) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_sua);

        EditText edtTenCV = (EditText) dialog.findViewById(R.id.edtTenCV);
        Button btnConfirm = (Button) dialog.findViewById(R.id.btnConfirm);
        Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);

        edtTenCV.setText(ten);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenMoi = edtTenCV.getText().toString().trim();
                database.QueryData("UPDATE CongViec SET TenCV = '"+ tenMoi + "' WHERE id = '"+ id +"'");
                dialog.dismiss();
                GetDataCongViec();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void DialogXoaCongViec(String tencv, int Id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa công việc "+ tencv + " không ?");
        dialogXoa.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.QueryData("DELETE FROM CongViec WHERE  Id = '"+ Id +"' ");
                Toast.makeText(MainActivity.this, "Đã xóa", Toast.LENGTH_SHORT).show();
                GetDataCongViec();
            }
        });

        dialogXoa.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dialogXoa.show();
    }
}