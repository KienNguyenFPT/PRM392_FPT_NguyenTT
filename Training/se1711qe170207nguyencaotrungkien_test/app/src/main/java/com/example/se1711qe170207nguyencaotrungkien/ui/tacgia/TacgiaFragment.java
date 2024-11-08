package com.example.se1711qe170207nguyencaotrungkien.ui.tacgia;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.se1711qe170207nguyencaotrungkien.R;
import com.example.se1711qe170207nguyencaotrungkien.model.Tacgia;
import com.example.se1711qe170207nguyencaotrungkien.repo.FirestoreRepository;
import com.example.se1711qe170207nguyencaotrungkien.ui.adapter.TacgiaAdapter; // Đảm bảo bạn đã tạo adapter này
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class TacgiaFragment extends Fragment {

    private FirestoreRepository<Tacgia> firestoreRepository;
    private ListView listViewTacgia; // Khai báo ListView
    private TacgiaAdapter tacgiaAdapter; // Adapter để hiển thị danh sách tác giả

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tacgia_list, container, false);
        firestoreRepository = new FirestoreRepository<>(FirestoreRepository.getFirestoreInstance(), Tacgia.class, "tacgia", getContext());

        listViewTacgia = rootView.findViewById(R.id.listViewTacgia); // Khởi tạo ListView
        Button buttonAddTacgia = rootView.findViewById(R.id.buttonAdd);
        buttonAddTacgia.setOnClickListener(v -> showAddTacgiaDialog());

        loadTacgiaData();

        return rootView;
    }

    private void loadTacgiaData() {
        firestoreRepository.getAll().thenAccept(this::onTacgiaLoaded)
                .exceptionally(e -> {
                    Toast.makeText(getContext(), "Lỗi khi tải dữ liệu tác giả: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void onTacgiaLoaded(List<Tacgia> tacgiaList) {
        tacgiaAdapter = new TacgiaAdapter(getContext(), tacgiaList);
        listViewTacgia.setAdapter(tacgiaAdapter);
    }

    private void showAddTacgiaDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_tacgia, null);
        builder.setView(dialogView);

        EditText editTextTenTacgia = dialogView.findViewById(R.id.editTextTenTacgia);
        EditText editTextEmail = dialogView.findViewById(R.id.editTextEmail);
        EditText editTextDiachi = dialogView.findViewById(R.id.editTextDiachi);
        EditText editTextDienthoai = dialogView.findViewById(R.id.editTextDienthoai);
        Button buttonAddTacgia = dialogView.findViewById(R.id.buttonAddTacgia);

        AlertDialog dialog = builder.create();
        buttonAddTacgia.setOnClickListener(v -> {
            String tenTacgia = editTextTenTacgia.getText().toString();
            String email = editTextEmail.getText().toString();
            String diachi = editTextDiachi.getText().toString();
            String dienthoai = editTextDienthoai.getText().toString();

            if (!tenTacgia.isEmpty() && !email.isEmpty() && !diachi.isEmpty() && !dienthoai.isEmpty()) {
                Tacgia newTacgia = new Tacgia(tenTacgia, email, diachi, dienthoai);
                firestoreRepository.add(newTacgia, "id", "tenTacgia").thenRun(() -> {
                    Toast.makeText(getContext(), "Tác giả đã được thêm thành công!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    loadTacgiaData(); // Tải lại danh sách sau khi thêm tác giả mới
                }).exceptionally(e -> {
                    Toast.makeText(getContext(), "Lỗi khi thêm tác giả: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
            } else {
                Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
