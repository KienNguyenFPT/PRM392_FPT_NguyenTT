package com.example.lab10_vannhat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab10_vannhat.api.TraineeRepository;
import com.example.lab10_vannhat.api.TraineeService;
import com.example.lab10_vannhat.model.Trainee;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeDetailActivity extends AppCompatActivity {
    private TraineeService traineeService;
    private EditText etName, etEmail, etPhone, etGender;
    private Button btnUpdate, btnDelete;
    private long traineeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_detail);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        traineeService = TraineeRepository.getTraineeService();
        traineeId = getIntent().getLongExtra("trainee_id", -1);

        loadTraineeDetails();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTrainee();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTrainee();
            }
        });
    }

    private void loadTraineeDetails() {
        Call<Trainee> call = traineeService.getAllTrainees(traineeId);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Trainee trainee = response.body();
                    etName.setText(trainee.getName());
                    etEmail.setText(trainee.getEmail());
                    etPhone.setText(trainee.getPhone());
                    etGender.setText(trainee.getGender());
                } else {
                    Toast.makeText(TraineeDetailActivity.this, "Failed to load trainee details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(TraineeDetailActivity.this, "Failed to load trainee details", Toast.LENGTH_SHORT).show();
                Log.e("TraineeDetailActivity", "onFailure: ", t);
            }
        });
    }

    private void updateTrainee() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        Trainee trainee = new Trainee(traineeId, name, email, phone, gender);
        Call<Trainee> call = traineeService.updateTrainees(traineeId, trainee);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TraineeDetailActivity.this, "Trainee updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TraineeDetailActivity.this, "Failed to update trainee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(TraineeDetailActivity.this, "Failed to update trainee", Toast.LENGTH_SHORT).show();
                Log.e("TraineeDetailActivity", "onFailure: ", t);
            }
        });
    }

    private void deleteTrainee() {
        Call<Void> call = traineeService.deleteTrainees(traineeId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(TraineeDetailActivity.this, "Trainee deleted successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(TraineeDetailActivity.this, "Failed to delete trainee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(TraineeDetailActivity.this, "Failed to delete trainee", Toast.LENGTH_SHORT).show();
                Log.e("TraineeDetailActivity", "onFailure: ", t);
            }
        });
    }
}
