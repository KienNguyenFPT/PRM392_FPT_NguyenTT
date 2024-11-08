package com.example.lab11;

public class TraineeRepository {
    public static TraineeRepository getTraineeService() {
        return (TraineeRepository) APIClient.getClient().create(TraineeService.class);
    }
}
