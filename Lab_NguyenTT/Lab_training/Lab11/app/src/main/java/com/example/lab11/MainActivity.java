package com.example.lab11;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import Models.Trainee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TraineeService traineeService;
    EditText edName;
    EditText edEmail;
    EditText edGender;
    EditText edPhone;
    Button btnSave;
    ImageView imgNext;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edName = findViewById(R.id.edName);
        edEmail = findViewById(R.id.edEmail);
        edGender = findViewById(R.id.edGender);
        edPhone = findViewById(R.id.edPhone);
        btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();  // Gọi hàm save khi bấm nút
            }
        });

        // Khởi tạo traineeService
        traineeService = APIClient.getClient().create(TraineeService.class); // Đảm bảo đây là dòng này

        imgNext = findViewById(R.id.imgNext);

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Kết thúc Activity hiện tại và quay lại Activity trước đó
                finish();
            }
        });
    }

    private void save() {
        String name = edName.getText().toString();
        String email = edEmail.getText().toString();
        String gender = edGender.getText().toString();
        String phone = edPhone.getText().toString();

        // Kiểm tra các trường nhập liệu
        if (name.isEmpty() || email.isEmpty() || gender.isEmpty() || phone.isEmpty()) {
            Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }

        // Tạo đối tượng Trainee
        Trainee trainee = new Trainee(name, email, gender, phone);
        try {
            Call<Trainee> call = traineeService.createTrainees(trainee);
            call.enqueue(new Callback<Trainee>() {
                @Override
                public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                    if (response.body() != null) {
                        Toast.makeText(MainActivity.this, "Save successful!", Toast.LENGTH_LONG).show();
                        // Chuyển đến TraineeListActivity
                        Intent intent = new Intent(MainActivity.this, TraineeListActivity.class);
                        startActivity(intent);
                        finish(); // Kết thúc MainActivity
                    } else {
                        Toast.makeText(MainActivity.this, "Save failed: " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Trainee> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Save failed: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            Log.d("Wrong", e.getMessage());
        }
    }
}