package com.example.se1711qe170207nguyencaotrungkien.ui.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import com.example.se1711qe170207nguyencaotrungkien.R;
import com.example.se1711qe170207nguyencaotrungkien.model.Tacgia;

import java.util.List;

public class TacgiaAdapter extends ArrayAdapter<Tacgia> {

    public TacgiaAdapter(Context context, List<Tacgia> tacgiaList) {
        super(context, 0, tacgiaList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tacgia, parent, false);
        }

        Tacgia tacgia = getItem(position);

        TextView tvTenTacgia = convertView.findViewById(R.id.tvTenTacgia);
        TextView tvEmail = convertView.findViewById(R.id.tvEmail);
        TextView tvDiachi = convertView.findViewById(R.id.tvDiachi);
        TextView tvDienthoai = convertView.findViewById(R.id.tvDienthoai);

        tvTenTacgia.setText(tacgia.getTenTacgia());
        tvEmail.setText(tacgia.getEmail());
        tvDiachi.setText(tacgia.getDiachi());
        tvDienthoai.setText(tacgia.getDienthoai());

        return convertView;
    }
}
