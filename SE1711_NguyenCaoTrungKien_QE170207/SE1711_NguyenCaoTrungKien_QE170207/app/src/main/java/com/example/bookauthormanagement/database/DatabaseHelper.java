package com.example.bookauthormanagement.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "bookauthor.db";
    private static final int DATABASE_VERSION = 1;

    // Table names
    public static final String TABLE_BOOK = "Sach";
    public static final String TABLE_AUTHOR = "Tacgia";


    // BOOK table column names
    public static final String COLUMN_BOOK_ID = "Masach";
    public static final String COLUMN_BOOK_NAME = "Tensach";
    public static final String COLUMN_PUBLISH_DATE = "NgayXB";
    public static final String COLUMN_GENRE = "Theloai";
    public static final String COLUMN_AUTHOR_ID = "idTacgia"; // Correct this line if necessary

    // AUTHOR table column names
    public static final String COLUMN_AUTHOR_PRIMARY_ID = "IDTacgia"; // Renamed this variable
    public static final String COLUMN_AUTHOR_NAME = "TenTacgia";
    public static final String COLUMN_ADDRESS = "Diachi";
    public static final String COLUMN_PHONE = "Dienthoai";

    // Table create statements
    private static final String CREATE_TABLE_BOOK = "CREATE TABLE "
            + TABLE_BOOK + "(" + COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_BOOK_NAME + " TEXT,"
            + COLUMN_PUBLISH_DATE + " TEXT,"
            + COLUMN_GENRE + " TEXT,"
            + COLUMN_AUTHOR_ID + " INTEGER" + ")";

    private static final String CREATE_TABLE_AUTHOR = "CREATE TABLE "
            + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_PRIMARY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_AUTHOR_NAME + " TEXT,"
            + COLUMN_ADDRESS + " TEXT,"
            + COLUMN_PHONE + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOK);
        db.execSQL(CREATE_TABLE_AUTHOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        onCreate(db);
    }
}
