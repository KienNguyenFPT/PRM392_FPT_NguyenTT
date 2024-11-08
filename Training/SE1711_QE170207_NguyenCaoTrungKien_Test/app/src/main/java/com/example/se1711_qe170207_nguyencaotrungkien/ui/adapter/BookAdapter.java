package com.example.se1711_qe170207_nguyencaotrungkien.ui.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.se1711_qe170207_nguyencaotrungkien.R;
import com.example.se1711_qe170207_nguyencaotrungkien.model.Sach;

import java.util.List;

public class BookAdapter extends ArrayAdapter<Sach> {
    private final List<Sach> books;

    public BookAdapter(Context context, List<Sach> books) {
        super(context, 0, books);
        this.books = books;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_book, parent, false);
        }

        Sach book = books.get(position);

        TextView titleView = convertView.findViewById(R.id.textViewTitle);
        TextView authorView = convertView.findViewById(R.id.textViewAuthor);
        TextView publicationDateView = convertView.findViewById(R.id.textViewPublicationDate);
        TextView genreView = convertView.findViewById(R.id.textViewGenre);

        titleView.setText(book.getTensach());
        authorView.setText(book.getTacgia());
        publicationDateView.setText(book.getNgayXB().toString());
        genreView.setText(book.getTheloai());

        return convertView;
    }
}
