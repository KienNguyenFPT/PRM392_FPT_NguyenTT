package com.example.lab11;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;

import Models.Trainee;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TraineeListActivity extends AppCompatActivity {
    ListView listView;
    TraineeService traineeService;
    ImageView imageViewAdd;
    ArrayList<Trainee> traineeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainee_list);

        listView = findViewById(R.id.listView);
        imageViewAdd = findViewById(R.id.imageViewAdd);
        traineeService = APIClient.getClient().create(TraineeService.class);

        imageViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TraineeListActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Gọi API để lấy danh sách Trainee
        getAllTrainees();

        // Xử lý sự kiện khi nhấn vào item trong danh sách
        listView.setOnItemClickListener((parent, view, position, id) -> {
            // Lấy Trainee từ danh sách dựa trên vị trí
            Trainee selectedTrainee = traineeList.get(position);

            // Tạo intent để chuyển sang UpdateTraineeActivity
            Intent intent = new Intent(TraineeListActivity.this, UpdateTraineeActivity.class);

            // Truyền thông tin của Trainee, bao gồm cả id
            intent.putExtra("traineeId", selectedTrainee.getId());
            intent.putExtra("traineeName", selectedTrainee.getName());
            intent.putExtra("traineeEmail", selectedTrainee.getEmail());
            intent.putExtra("traineeGender", selectedTrainee.getGender());
            intent.putExtra("traineePhone", selectedTrainee.getPhone());

            // Chuyển sang activity cập nhật thông tin
            startActivity(intent);
        });
    }

    private void getAllTrainees() {
        Call<Trainee[]> call = traineeService.getAllTrainees();
        call.enqueue(new Callback<Trainee[]>() {
            @Override
            public void onResponse(Call<Trainee[]> call, Response<Trainee[]> response) {
                if (response.body() != null) {
                    // Chuyển danh sách trainee từ response body
                    Trainee[] trainees = response.body();
                    // Chuyển đổi mảng Trainee[] thành ArrayList<Trainee>
                    traineeList = new ArrayList<>(Arrays.asList(trainees));

                    // Tạo adapter với danh sách trainee
                    TraineeAdapter adapter = new TraineeAdapter(TraineeListActivity.this, traineeList);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(TraineeListActivity.this, "No trainees found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Trainee[]> call, Throwable t) {
                Toast.makeText(TraineeListActivity.this, "Failed to load trainees", Toast.LENGTH_SHORT).show();
                Log.e("TraineeList", t.getMessage());
            }
        });
    }
}

