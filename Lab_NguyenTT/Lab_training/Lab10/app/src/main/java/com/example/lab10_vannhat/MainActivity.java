package com.example.lab10_vannhat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10_vannhat.api.TraineeRepository;
import com.example.lab10_vannhat.api.TraineeService;
import com.example.lab10_vannhat.model.Trainee;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private TraineeService traineeService;
    private RecyclerView recyclerView;
    private TraineeAdapter traineeAdapter;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        traineeAdapter = new TraineeAdapter();
        recyclerView.setAdapter(traineeAdapter);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreateTraineeActivity.class);
                startActivity(intent);
            }
        });

        traineeService = TraineeRepository.getTraineeService();
        loadTrainees();
    }
    @Override
    protected void onResume() {
        super.onResume();
        // Reload the list of trainees when returning to MainActivity
        loadTrainees();
    }

    private void loadTrainees() {
        Call<List<Trainee>> call = traineeService.getAllTrainees();
        call.enqueue(new Callback<List<Trainee>>() {
            @Override
            public void onResponse(Call<List<Trainee>> call, Response<List<Trainee>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    traineeAdapter.setTrainees(response.body());
                } else {
                    Toast.makeText(MainActivity.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Trainee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "onFailure: ", t);
            }
        });
    }
}
