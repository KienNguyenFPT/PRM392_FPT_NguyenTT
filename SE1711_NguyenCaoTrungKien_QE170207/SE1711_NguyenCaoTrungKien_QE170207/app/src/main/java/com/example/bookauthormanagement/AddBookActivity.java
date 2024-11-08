package com.example.bookauthormanagement;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookauthormanagement.models.Author;
import com.example.bookauthormanagement.models.Book;
import com.example.bookauthormanagement.viewmodels.AuthorViewModel;
import com.example.bookauthormanagement.viewmodels.BookViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class AddBookActivity extends AppCompatActivity {

    private TextInputEditText editTextBookName, editTextPublishDate, editTextGenre;
    private Spinner spinnerAuthors;
    private BookViewModel bookViewModel;
    private AuthorViewModel authorViewModel;
    private int bookId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        MaterialToolbar topAppBar = findViewById(R.id.topAppBar);
        topAppBar.setNavigationOnClickListener(v -> finish());

        editTextBookName = findViewById(R.id.editTextBookName);
        editTextPublishDate = findViewById(R.id.editTextPublishDate);
        editTextGenre = findViewById(R.id.editTextGenre);
        spinnerAuthors = findViewById(R.id.spinnerAuthors);
        findViewById(R.id.buttonSaveBook).setOnClickListener(v -> saveBook());

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);

        authorViewModel.getAuthors().observe(this, new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authors) {
                ArrayAdapter<Author> adapter = new ArrayAdapter<>(AddBookActivity.this, android.R.layout.simple_spinner_item, authors);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAuthors.setAdapter(adapter);

                // Check if there's an existing book to edit
                if (getIntent().hasExtra("bookId")) {
                    bookId = getIntent().getIntExtra("bookId", -1);
                    bookViewModel.getBookById(bookId).observe(AddBookActivity.this, new Observer<Book>() {
                        @Override
                        public void onChanged(Book book) {
                            if (book != null) {
                                editTextBookName.setText(book.getName());
                                editTextPublishDate.setText(book.getPublishDate());
                                editTextGenre.setText(book.getGenre());
                                // Set author spinner selection
                                for (int i = 0; i < spinnerAuthors.getAdapter().getCount(); i++) {
                                    if (((Author) spinnerAuthors.getItemAtPosition(i)).getId() == book.getAuthorId()) {
                                        spinnerAuthors.setSelection(i);
                                        break;
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });

        authorViewModel.loadAuthors();
    }

    private void saveBook() {
        String bookName = editTextBookName.getText().toString().trim();
        String publishDate = editTextPublishDate.getText().toString().trim();
        String genre = editTextGenre.getText().toString().trim();
        Author selectedAuthor = (Author) spinnerAuthors.getSelectedItem();

        if (bookName.isEmpty() || publishDate.isEmpty() || genre.isEmpty() || selectedAuthor == null) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Book book = new Book(bookId == -1 ? 0 : bookId, bookName, publishDate, genre, selectedAuthor.getId());
        if (bookId == -1) {
            bookViewModel.insertBook(book);
            Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
        } else {
            bookViewModel.updateBook(book);
            Toast.makeText(this, "Book updated successfully", Toast.LENGTH_SHORT).show();
        }
        finish();
    }
}
