package com.thirafikaz.sqlite_crud.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseNoteHelper extends SQLiteOpenHelper {

    public DatabaseNoteHelper (Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }

    static String DATABASE_NAME = "dbnote";
    static int DATABASE_VERSION = 1;
    static final String SQL_CREATE_TABLE = String.
            format("CREATE TABLE %s"
                    + "(%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "%s TEXT NOT NULL,"
                    + "%s TEXT NOT NULL,"
                    + "%s TEXT NOT NULL)",
                    DataBaseNoteContract.TABLE_NOTE,
                    DataBaseNoteContract.NoteColumn._ID,
                    DataBaseNoteContract.NoteColumn.JUDUL,
                    DataBaseNoteContract.NoteColumn.DESKRIPSI,
                    DataBaseNoteContract.NoteColumn.TANGGAL);


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DataBaseNoteContract.TABLE_NOTE);
        onCreate(sqLiteDatabase);

    }
}
