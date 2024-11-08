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

import com.example.bookauthormanagement.adapters.AuthorAdapter;
import com.example.bookauthormanagement.models.Author;
import com.example.bookauthormanagement.viewmodels.AuthorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AuthorListFragment extends Fragment {

    private AuthorViewModel authorViewModel;
    private RecyclerView recyclerViewAuthors;
    private AuthorAdapter authorAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_author_list, container, false);

        recyclerViewAuthors = view.findViewById(R.id.recyclerViewAuthors);
        FloatingActionButton fabAddAuthor = view.findViewById(R.id.fab_add_author);
        fabAddAuthor.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddAuthorActivity.class));
        });

        recyclerViewAuthors.setLayoutManager(new LinearLayoutManager(getActivity()));
        authorAdapter = new AuthorAdapter(null, new AuthorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Author author) {
                // Handle author item click
            }

            @Override
            public void onEditClick(Author author) {
                Intent intent = new Intent(getActivity(), AddAuthorActivity.class);
                intent.putExtra("authorId", author.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Author author) {
                authorViewModel.deleteAuthor(author.getId());
            }
        });
        recyclerViewAuthors.setAdapter(authorAdapter);

        authorViewModel = new ViewModelProvider(this).get(AuthorViewModel.class);
        authorViewModel.getAuthors().observe(getViewLifecycleOwner(), new Observer<List<Author>>() {
            @Override
            public void onChanged(List<Author> authors) {
                authorAdapter.setAuthors(authors);
            }
        });

        authorViewModel.loadAuthors();

        return view;
    }
}
