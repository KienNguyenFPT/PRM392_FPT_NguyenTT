package com.example.bookauthormanagement;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookauthormanagement.models.Author;
import com.example.bookauthormanagement.viewmodels.AuthorViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

public class AddAuthorActivity extends AppCompatActivity {

    private TextInputEditText editTextAuthorName, editTextAddress, editTextPhone;
    private AuthorViewModel authorViewModel;
    private int authorId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        editTextAuthorName = findViewById(R.id.editTextAuthorName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhone = findViewById(R.id.editTextPhone);
        findViewById(R.id.buttonSaveAuthor).setOnClickListener(v -> saveAuthor());

        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        // Check if there's an existing author to edit
        if (getIntent().hasExtra("authorId")) {
            authorId = getIntent().getIntExtra("authorId", -1);
            authorViewModel.getAuthorById(authorId).observe(this, new Observer<Author>() {
                @Override
                public void onChanged(Author author) {
                    if (author != null) {
                        editTextAuthorName.setText(author.getName());
                        editTextAddress.setText(author.getAddress());
                        editTextPhone.setText(author.getPhone());
                    }
                }
            });
        }
    }

    private void saveAuthor() {
        String authorName = editTextAuthorName.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();

        if (authorName.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Author author = new Author(authorId == -1 ? 0 : authorId, authorName, address, phone);
        if (authorId == -1) {
            authorViewModel.insertAuthor(author);
            Toast.makeText(this, "Author added successfully", Toast.LENGTH_SHORT).show();
        } else {
            authorViewModel.updateAuthor(author);
            Toast.makeText(this, "Author updated successfully", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
