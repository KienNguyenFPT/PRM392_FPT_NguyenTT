package com.example.bookauthormanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookauthormanagement.database.DatabaseHelper;
import com.example.bookauthormanagement.models.Author;

import java.util.ArrayList;
import java.util.List;

public class AuthorDao {
    private DatabaseHelper dbHelper;

    public AuthorDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertAuthor(Author author) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AUTHOR_NAME, author.getName());
        values.put(DatabaseHelper.COLUMN_ADDRESS, author.getAddress());
        values.put(DatabaseHelper.COLUMN_PHONE, author.getPhone());
        return db.insert(DatabaseHelper.TABLE_AUTHOR, null, values);
    }

    public int updateAuthor(Author author) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AUTHOR_NAME, author.getName());
        values.put(DatabaseHelper.COLUMN_ADDRESS, author.getAddress());
        values.put(DatabaseHelper.COLUMN_PHONE, author.getPhone());
        return db.update(DatabaseHelper.TABLE_AUTHOR, values, DatabaseHelper.COLUMN_AUTHOR_PRIMARY_ID + " = ?", new String[]{String.valueOf(author.getId())});
    }

    public int deleteAuthor(int authorId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DatabaseHelper.TABLE_BOOK, DatabaseHelper.COLUMN_AUTHOR_ID + " = ?", new String[]{String.valueOf(authorId)});
        return db.delete(DatabaseHelper.TABLE_AUTHOR, DatabaseHelper.COLUMN_AUTHOR_PRIMARY_ID + " = ?", new String[]{String.valueOf(authorId)});
    }

    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_AUTHOR, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_PRIMARY_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_NAME));
                String address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS));
                String phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE));
                authors.add(new Author(id, name, address, phone));
            }
            cursor.close();
        }
        return authors;
    }

    public Author getAuthorById(int authorId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_AUTHOR, null, DatabaseHelper.COLUMN_AUTHOR_PRIMARY_ID + " = ?", new String[]{String.valueOf(authorId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Author author = new Author(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_PRIMARY_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PHONE))
            );
            cursor.close();
            return author;
        } else {
            return null;
        }
    }

}
