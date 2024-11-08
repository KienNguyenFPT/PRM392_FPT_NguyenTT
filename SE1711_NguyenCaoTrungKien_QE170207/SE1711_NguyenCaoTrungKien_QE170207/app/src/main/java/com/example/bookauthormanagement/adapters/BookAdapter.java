package com.example.bookauthormanagement.adapters;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookauthormanagement.R;
import com.example.bookauthormanagement.models.Book;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private List<Book> books;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Book book);
        void onEditClick(Book book);
        void onDeleteClick(Book book);
    }

    public BookAdapter(List<Book> books, OnItemClickListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        holder.textViewBookName.setText(book.getName());
        holder.textViewBookDetails.setText("Published on: " + book.getPublishDate() + ", Genre: " + book.getGenre());
        holder.imageViewBook.setImageResource(R.drawable.ic_book);  // Assuming ic_book.png is available in drawable folder

        holder.itemView.setOnClickListener(v -> listener.onItemClick(book));

        holder.menuIcon.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(holder.itemView.getContext(), holder.menuIcon);
            popupMenu.inflate(R.menu.menu_item);
            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    listener.onEditClick(book);
                    return true;
                } else if (item.getItemId() == R.id.action_delete) {
                    listener.onDeleteClick(book);
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
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        TextView textViewBookName, textViewBookDetails;
        ImageView imageViewBook, menuIcon;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewBookName = itemView.findViewById(R.id.textViewBookName);
            textViewBookDetails = itemView.findViewById(R.id.textViewBookDetails);
            imageViewBook = itemView.findViewById(R.id.imageViewBook);
            menuIcon = itemView.findViewById(R.id.menuIcon);
        }
    }
}
