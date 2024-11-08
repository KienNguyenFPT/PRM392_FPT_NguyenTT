package com.example.lab10_vannhat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab10_vannhat.model.Trainee;

import java.util.ArrayList;
import java.util.List;

public class TraineeAdapter extends RecyclerView.Adapter<TraineeAdapter.ViewHolder> {
    private List<Trainee> trainees = new ArrayList<>();
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_trainee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trainee trainee = trainees.get(position);
        holder.tvName.setText(trainee.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TraineeDetailActivity.class);
                intent.putExtra("trainee_id", trainee.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trainees.size();
    }

    public void setTrainees(List<Trainee> trainees) {
        this.trainees = trainees;
        notifyDataSetChanged();
    }
    public TraineeAdapter() {
        this.context = context;
        this.trainees = new ArrayList<>();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}

