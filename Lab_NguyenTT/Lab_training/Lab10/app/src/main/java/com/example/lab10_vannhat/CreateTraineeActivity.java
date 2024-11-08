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

public class CreateTraineeActivity extends AppCompatActivity {
    private TraineeService traineeService;
    private EditText etName, etEmail, etPhone, etGender;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trainee);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etGender = findViewById(R.id.etGender);
        btnSave = findViewById(R.id.btnSave);

        traineeService = TraineeRepository.getTraineeService();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrainee();
            }
        });
    }

    private void saveTrainee() {
        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String phone = etPhone.getText().toString();
        String gender = etGender.getText().toString();

        Trainee trainee = new Trainee(name, email, phone, gender);
        Call<Trainee> call = traineeService.createTrainees(trainee);
        call.enqueue(new Callback<Trainee>() {
            @Override
            public void onResponse(Call<Trainee> call, Response<Trainee> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateTraineeActivity.this, "Trainee created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateTraineeActivity.this, "Failed to create trainee", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee> call, Throwable t) {
                Toast.makeText(CreateTraineeActivity.this, "Failed to create trainee", Toast.LENGTH_SHORT).show();
                Log.e("CreateTraineeActivity", "onFailure: ", t);
            }
        });
    }
}

