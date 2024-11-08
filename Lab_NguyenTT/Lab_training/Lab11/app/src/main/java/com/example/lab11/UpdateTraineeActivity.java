package com.example.lab11;

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

public class UpdateTraineeActivity extends AppCompatActivity {
    EditText edUpName, edUpEmail, edUpGender, edUpPhone;
    Button btnUpdate, btnDelete;
    long traineeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_trainee);

        edUpName = findViewById(R.id.edUpName);
        edUpEmail = findViewById(R.id.edUpEmail);
        edUpGender = findViewById(R.id.edUpGender);
        edUpPhone = findViewById(R.id.edUpPhone);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        // Lấy thông tin từ Intent
        traineeId = getIntent().getLongExtra("traineeId", -1);
        String traineeName = getIntent().getStringExtra("traineeName");
        String traineeEmail = getIntent().getStringExtra("traineeEmail");
        String traineeGender = getIntent().getStringExtra("traineeGender");
        String traineePhone = getIntent().getStringExtra("traineePhone");

        // Hiển thị thông tin vào EditText
        edUpName.setText(traineeName);
        edUpEmail.setText(traineeEmail);
        edUpGender.setText(traineeGender);
        edUpPhone.setText(traineePhone);

        // Xử lý cập nhật thông tin khi bấm nút Update
        btnUpdate.setOnClickListener(v -> {
            updateTrainee();
        });

        btnDelete.setOnClickListener(v -> {
            deleteTrainee();
        });
    }

    private void updateTrainee() {
        String updatedName = edUpName.getText().toString();
        String updatedEmail = edUpEmail.getText().toString();
        String updatedGender = edUpGender.getText().toString();
        String updatedPhone = edUpPhone.getText().toString();

        if (updatedName.isEmpty() || updatedEmail.isEmpty() || updatedGender.isEmpty() || updatedPhone.isEmpty()) {
            Toast.makeText(UpdateTraineeActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Trainee updatedTrainee = new Trainee(traineeId, updatedName, updatedEmail, updatedGender, updatedPhone);

        Call<Trainee> call = APIClient.getClient().create(TraineeService.class)
                .updateTrainees(String.valueOf(traineeId), updatedTrainee);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateTraineeActivity.this, "Update successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateTraineeActivity.this, TraineeListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateTraineeActivity.this, "Update failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(UpdateTraineeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteTrainee() {
        Call<Trainee> call = APIClient.getClient().create(TraineeService.class)
                .deleteTrainees(String.valueOf(traineeId));  // Gọi API để xóa trainee dựa trên id
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateTraineeActivity.this, "Delete successful!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateTraineeActivity.this, TraineeListActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(UpdateTraineeActivity.this, "Delete failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(UpdateTraineeActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
