package com.example.mobappgrado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GradoDB";
    private static final int DATABASE_VERSION = 1;

    // Users table
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    // Terms table
    private static final String TABLE_TERMS = "terms";
    private static final String COLUMN_TERM_ID = "term_id";
    private static final String COLUMN_TERM_NAME = "term_name";
    private static final String COLUMN_USER_ID = "user_id";

    // Subjects table
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String COLUMN_SUBJECT_ID = "subject_id";
    private static final String COLUMN_SUBJECT_NAME = "subject_name";
    private static final String COLUMN_TERM_ID_FK = "term_id";

    // Create table query
    private static final String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT UNIQUE,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    // Create terms table query
    private static final String CREATE_TERMS_TABLE = "CREATE TABLE " + TABLE_TERMS + "("
            + COLUMN_TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_TERM_NAME + " TEXT,"
            + COLUMN_USER_ID + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_ID + ")"
            + ")";

    // Create subjects table query
    private static final String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "("
            + COLUMN_SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_SUBJECT_NAME + " TEXT,"
            + COLUMN_TERM_ID_FK + " INTEGER,"
            + "FOREIGN KEY(" + COLUMN_TERM_ID_FK + ") REFERENCES " + TABLE_TERMS + "(" + COLUMN_TERM_ID + ")"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TERMS_TABLE);
        db.execSQL(CREATE_SUBJECTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }
    // Add new user
    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    // Check if user exists
    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?" + " AND " + COLUMN_PASSWORD + "=?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    // Check if username exists
    public boolean checkUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID};
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);

        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}