package com.example.lab10_vannhat.api;

import android.telecom.Call;

import com.example.lab10_vannhat.model.Trainee;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TraineeService {
    String TRAINEES = "Nhanvien";

    @GET(TRAINEES)
    retrofit2.Call<List<Trainee>> getAllTrainees();

    @GET(TRAINEES + "/{id}")
    retrofit2.Call<Trainee> getAllTrainees(@Path("id") long id);

    @POST(TRAINEES)
    retrofit2.Call<Trainee> createTrainees(@Body Trainee trainee);

    @PUT(TRAINEES + "/{id}")
    retrofit2.Call<Trainee> updateTrainees(@Path("id") long id, @Body Trainee trainee);

    @DELETE(TRAINEES+ "/{id}")
    retrofit2.Call<Void> deleteTrainees(@Path("id") long id);
}
