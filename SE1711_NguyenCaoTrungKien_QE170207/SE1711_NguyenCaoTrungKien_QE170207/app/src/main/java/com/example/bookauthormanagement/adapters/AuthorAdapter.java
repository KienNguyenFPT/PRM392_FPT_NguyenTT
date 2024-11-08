package com.example.bookauthormanagement.adapters;
import android.view.LayoutInflater;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookauthormanagement.R;
import com.example.bookauthormanagement.models.Author;

import java.util.List;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorViewHolder> {

    private List<Author> authors;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Author author);
        void onEditClick(Author author);
        void onDeleteClick(Author author);
    }

    public AuthorAdapter(List<Author> authors, OnItemClickListener listener) {
        this.authors = authors;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AuthorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_author, parent, false);
        return new AuthorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AuthorViewHolder holder, int position) {
        Author author = authors.get(position);
        holder.textViewAuthorName.setText(author.getName());
        holder.textViewAuthorDetails.setText("Address: " + author.getAddress() + ", Phone: " + author.getPhone());

        holder.itemView.setOnClickListener(v -> listener.onItemClick(author));

        holder.menuIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.menuIcon);
            popupMenu.inflate(R.menu.menu_item);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    listener.onEditClick(author);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    listener.onDeleteClick(author);
                    return true;
                } else {
                    return false;
                }
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
        notifyDataSetChanged();
    }

    class AuthorViewHolder extends RecyclerView.ViewHolder {
        TextView textViewAuthorName, textViewAuthorDetails;
        ImageView menuIcon;

        public AuthorViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewAuthorName = itemView.findViewById(R.id.textViewAuthorName);
            textViewAuthorDetails = itemView.findViewById(R.id.textViewAuthorDetails);
            menuIcon = itemView.findViewById(R.id.menuIcon);
        }
    }
}