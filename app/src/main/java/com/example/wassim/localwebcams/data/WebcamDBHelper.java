package com.example.wassim.localwebcams.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.wassim.localwebcams.data.WebcamContract.WebcamEntry;

public class WebcamDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "webcams.db";
    private static final int DATABASE_VERSION = 5;

    public WebcamDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_WEBCAMS_TABLE =
                "CREATE TABLE " + WebcamEntry.TABLE_NAME + " (" +
                        WebcamEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        WebcamEntry.COLUMN_WEBCAM_ID + " INTEGER, " +
                        " UNIQUE (" + WebcamEntry.COLUMN_WEBCAM_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_WEBCAMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + WebcamEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
