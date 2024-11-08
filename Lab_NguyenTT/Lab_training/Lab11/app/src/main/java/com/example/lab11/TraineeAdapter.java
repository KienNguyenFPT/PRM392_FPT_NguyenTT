package com.example.lab11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import Models.Trainee;

public class TraineeAdapter extends ArrayAdapter<Trainee> {
    private List<Trainee> traineeList;

    public TraineeAdapter(Context context, List<Trainee> trainees) {
        super(context, 0, trainees);
        this.traineeList = trainees;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Kiểm tra xem convertView có null hay không, nếu có thì khởi tạo mới
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_trainee, parent, false);
        }

        // Lấy đối tượng Trainee hiện tại
        Trainee trainee = traineeList.get(position);

        // Khởi tạo các TextView
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        TextView txtGender = convertView.findViewById(R.id.txtGender);
        TextView txtPhone = convertView.findViewById(R.id.txtPhone);

        // Đặt dữ liệu vào các TextView
        txtName.setText("Name: " + trainee.getName()); // Cập nhật giá trị
        txtEmail.setText("Email: " + trainee.getEmail()); // Cập nhật giá trị
        txtGender.setText("Gender: " + trainee.getGender()); // Cập nhật giá trị
        txtPhone.setText("Phone: " + trainee.getPhone()); // Cập nhật giá trị

        return convertView;
    }
}
