package com.example.translator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE = "TranslationDB.db";
    public static final String TABLE_TRANSLATION = "tbl_Translation";
    public static final String COL_ID = "id";
    public static final String COL_URDU = "urdu";
    public static final String COL_MARATHI = "marathi";
    public static final String COL_HINDI = "hindi";
    public static final int VERSION = 1;
    public static final String CREATE_TABLE_TRANSLATION = "CREATE TABLE " + TABLE_TRANSLATION + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COL_URDU + " TEXT ," + COL_HINDI + " TEXT ," + COL_MARATHI + " TEXT)";


    public DbHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_TRANSLATION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSLATION);
        onCreate(db);
    }

    public boolean insert_data(String urduWord, String hindiWord, String marathiWord) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_URDU, urduWord);
        cv.put(COL_HINDI, hindiWord);
        cv.put(COL_MARATHI, marathiWord);

        long result = db.insert(TABLE_TRANSLATION, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM " + TABLE_TRANSLATION, null);
        return cur;
    }

    public Cursor getTranslation(String word, String from, String to) {
        SQLiteDatabase db = this.getWritableDatabase();

        String mFrom, mTo;
        if (from == "Urdu") {
            mFrom = COL_URDU;
        } else if (from == "Hindi") {
            mFrom = COL_HINDI;
        } else {
            mFrom = COL_MARATHI;
        }
        if (to == "Urdu") {
            mTo = COL_URDU;
        } else if (to == "Hindi") {
            mTo = COL_HINDI;
        } else {
            mTo = COL_MARATHI;
        }

        String selectionQuery = "SELECT " + mTo + " FROM " + TABLE_TRANSLATION + " WHERE " + mFrom + " = '" + word + "'";
        Cursor result = db.rawQuery(selectionQuery, null);
//        String[] projection = {mTo};
//        String selection = mFrom + "LIKE ?";
//        String[] selectionArgs = {word};
//
//        Cursor result = db.query(TABLE_TRANSLATION, projection, selection, selectionArgs, null, null, null);
        return result;
    }

}
