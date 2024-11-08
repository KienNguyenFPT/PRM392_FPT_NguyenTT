package com.example.bookauthormanagement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookauthormanagement.adapters.BookAdapter;
import com.example.bookauthormanagement.models.Book;
import com.example.bookauthormanagement.viewmodels.BookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class BookListFragment extends Fragment {

    private BookViewModel bookViewModel;
    private RecyclerView recyclerViewBooks;
    private BookAdapter bookAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        recyclerViewBooks = view.findViewById(R.id.recyclerViewBooks);
        FloatingActionButton fabAddBook = view.findViewById(R.id.fab_add_book);
        fabAddBook.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddBookActivity.class));
        });

        recyclerViewBooks.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookAdapter = new BookAdapter(null, new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                // Handle book item click
            }

            @Override
            public void onEditClick(Book book) {
                Intent intent = new Intent(getActivity(), AddBookActivity.class);
                intent.putExtra("bookId", book.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Book book) {
                bookViewModel.deleteBook(book.getId());
            }
        });
        recyclerViewBooks.setAdapter(bookAdapter);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                bookAdapter.setBooks(books);
            }
        });

        bookViewModel.loadBooks();

        return view;
    }
}
