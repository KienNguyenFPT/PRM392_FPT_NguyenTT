package com.example.se1711qe170207nguyencaotrungkien.ui.sach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.se1711qe170207nguyencaotrungkien.R;
import com.example.se1711qe170207nguyencaotrungkien.model.Sach;
import com.example.se1711qe170207nguyencaotrungkien.repo.FirestoreRepository;
import com.example.se1711qe170207nguyencaotrungkien.ui.adapter.BookAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class BookFragment extends Fragment {

    private FirebaseFirestore firestore;
    private ListView listViewBooks;
    private Button buttonAdd;
    private FirestoreRepository<Sach> firestoreRepository;
    private BookAdapter bookAdapter; // Adapter để hiển thị sách
// no sai ten package trong fire base a P
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        firestore = FirebaseFirestore.getInstance();
        listViewBooks = view.findViewById(R.id.listViewBooks);
        buttonAdd = view.findViewById(R.id.buttonAdd);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firestoreRepository = new FirestoreRepository<>(db, Sach.class, "sach", getContext());

        buttonAdd.setOnClickListener(v -> showAddBookDialog());

        loadBooks();

        return view;
    }

    private void loadBooks() {
        firestoreRepository.getAll().thenAccept(this::onBooksLoaded)
                .exceptionally(e -> {
                    Toast.makeText(getContext(), "Error loading books: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    return null;
                });
    }

    private void onBooksLoaded(List<Sach> books) {
        bookAdapter = new BookAdapter(getContext(), books); // Giả sử bạn có adapter này
        listViewBooks.setAdapter(bookAdapter);
    }

    private void showAddBookDialog() {
        AddBookDialogFragment dialogFragment = new AddBookDialogFragment();
        dialogFragment.show(getParentFragmentManager(), "AddBookDialog");
    }
}
