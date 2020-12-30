package com.thirafikaz.sqlite_crud.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.thirafikaz.sqlite_crud.Model.Note;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.thirafikaz.sqlite_crud.Database.DataBaseNoteContract.NoteColumn.DESKRIPSI;
import static com.thirafikaz.sqlite_crud.Database.DataBaseNoteContract.NoteColumn.JUDUL;
import static com.thirafikaz.sqlite_crud.Database.DataBaseNoteContract.NoteColumn.TANGGAL;
import static com.thirafikaz.sqlite_crud.Database.DataBaseNoteContract.TABLE_NOTE;

public class NoteHelper {
    private static DatabaseNoteHelper databaseNoteHelper;
    private static SQLiteDatabase sqLiteDatabase;
    private static String DATABASE_TABLE = TABLE_NOTE;

    public NoteHelper(Context context) {
        databaseNoteHelper = new DatabaseNoteHelper(context);
    }

    public void open() {
        sqLiteDatabase = databaseNoteHelper.getWritableDatabase();
    }

    public ArrayList<Note> getAllNote() {
        ArrayList<Note> data = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null
        );
        cursor.moveToFirst();

        Note note;
        if (cursor.getCount() > 0) {
            do {
                note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                note.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                note.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DESKRIPSI)));
                note.setTanggal(cursor.getString(cursor.getColumnIndexOrThrow(TANGGAL)));

                data.add(note);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        cursor.close();
        return data;
    }

    public void insertData(Note note) {
        ContentValues values = new ContentValues();
        values.put(JUDUL, note.getJudul());
        values.put(DESKRIPSI, note.getDeskripsi());
        values.put(TANGGAL, note.getTanggal());
        sqLiteDatabase.insert(DATABASE_TABLE, null, values);
    }

    public void updateData(Note note) {
        ContentValues values = new ContentValues();
        values.put(JUDUL, note.getJudul());
        values.put(DESKRIPSI, note.getDeskripsi());
        values.put(TANGGAL, note.getTanggal());
        sqLiteDatabase.update(DATABASE_TABLE, values, _ID + "= '" + note.getId()+"'", null);
    }

    public void deleteData(int id) {
        sqLiteDatabase.delete(DATABASE_TABLE, _ID + "= '" + id+ "'", null);
    }
}
