package com.thirafikaz.sqlite_crud.Database;

import android.provider.BaseColumns;

public class DataBaseNoteContract {
    static String TABLE_NOTE = "note";

    static final class NoteColumn implements BaseColumns{
        static String JUDUL = "judul";
        static String DESKRIPSI = "deskripsi";
        static String TANGGAL = "tanggal";
    }
}
