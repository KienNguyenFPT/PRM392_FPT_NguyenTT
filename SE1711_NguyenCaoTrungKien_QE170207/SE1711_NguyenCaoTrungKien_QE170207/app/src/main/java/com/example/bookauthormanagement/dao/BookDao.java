package com.example.bookauthormanagement.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bookauthormanagement.database.DatabaseHelper;
import com.example.bookauthormanagement.models.Book;

import java.util.ArrayList;
import java.util.List;

public class BookDao {
    private DatabaseHelper dbHelper;

    public BookDao(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public long insertBook(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOK_NAME, book.getName());
        values.put(DatabaseHelper.COLUMN_PUBLISH_DATE, book.getPublishDate());
        values.put(DatabaseHelper.COLUMN_GENRE, book.getGenre());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ID, book.getAuthorId());
        return db.insert(DatabaseHelper.TABLE_BOOK, null, values);
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_BOOK_NAME, book.getName());
        values.put(DatabaseHelper.COLUMN_PUBLISH_DATE, book.getPublishDate());
        values.put(DatabaseHelper.COLUMN_GENRE, book.getGenre());
        values.put(DatabaseHelper.COLUMN_AUTHOR_ID, book.getAuthorId());
        return db.update(DatabaseHelper.TABLE_BOOK, values, DatabaseHelper.COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(book.getId())});
    }

    public int deleteBook(int bookId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(DatabaseHelper.TABLE_BOOK, DatabaseHelper.COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(bookId)});
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOK, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_NAME));
                String publishDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLISH_DATE));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENRE));
                int authorId = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID));
                books.add(new Book(id, name, publishDate, genre, authorId));
            }
            cursor.close();
        }
        return books;
    }

    public List<Book> getBooksByAuthorId(int authorId) {
        List<Book> books = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOK, null, DatabaseHelper.COLUMN_AUTHOR_ID + " = ?", new String[]{String.valueOf(authorId)}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_NAME));
                String publishDate = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLISH_DATE));
                String genre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENRE));
                books.add(new Book(id, name, publishDate, genre, authorId));
            }
            cursor.close();
        }
        return books;
    }

    public Book getBookById(int bookId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DatabaseHelper.TABLE_BOOK, null, DatabaseHelper.COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(bookId)}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            Book book = new Book(
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_BOOK_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PUBLISH_DATE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_GENRE)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_AUTHOR_ID))
            );
            cursor.close();
            return book;
        } else {
            return null;
        }
    }

}
