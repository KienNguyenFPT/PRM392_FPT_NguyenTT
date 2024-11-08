package com.example.se1711_qe170207_nguyencaotrungkien.ui.sach;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.se1711_qe170207_nguyencaotrungkien.R;
import com.example.se1711_qe170207_nguyencaotrungkien.model.Sach;
import com.example.se1711_qe170207_nguyencaotrungkien.repo.FirestoreRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AddBookDialogFragment extends DialogFragment {

    private EditText editTextTitle;
    private EditText editTextAuthor;
    private EditText editTextDescription;
    private Button buttonAdd;

    private FirestoreRepository<Sach> firestoreRepository;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_book, container, false);

        editTextTitle = view.findViewById(R.id.editTextTitle);
        editTextAuthor = view.findViewById(R.id.editTextAuthor);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        buttonAdd = view.findViewById(R.id.buttonAdd);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        firestoreRepository = new FirestoreRepository<>(db, Sach.class, "sach", getContext());

        buttonAdd.setOnClickListener(v -> addBook());

        return view;
    }

    private void addBook() {
        String title = editTextTitle.getText().toString().trim();
        String author = editTextAuthor.getText().toString().trim();
        String description = editTextDescription.getText().toString().trim();

        if (title.isEmpty() || author.isEmpty() || description.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        Sach newBook = new Sach(title, new Date(), description, author);
        firestoreRepository.add(newBook).thenAccept(documentReference -> {
            Toast.makeText(getContext(), "Book added successfully!", Toast.LENGTH_SHORT).show();
            dismiss();
        }).exceptionally(e -> {
            Toast.makeText(getContext(), "Error adding book: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        });
    }
}