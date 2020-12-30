package com.thirafikaz.sqlite_crud;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.thirafikaz.sqlite_crud.Adapter.NoteAdapter;
import com.thirafikaz.sqlite_crud.Database.NoteHelper;
import com.thirafikaz.sqlite_crud.Model.Note;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvNote;
    NoteHelper noteHelper;
    ArrayList<Note> listNote;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvNote = findViewById(R.id.rv_note);
        rvNote.setLayoutManager(new LinearLayoutManager(this));

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        adapter = new NoteAdapter(this);
        rvNote.setAdapter(adapter);

        listNote =new ArrayList<>();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addUpdateIntent = new Intent(MainActivity.this, AddUpdateActivity.class);
                startActivityForResult(addUpdateIntent, AddUpdateActivity.REQUEST_ADD);
            }
        });

        new LoadNoteAsynctask().execute();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AddUpdateActivity.REQUEST_ADD) {
            if (resultCode == AddUpdateActivity.RESULT_ADD) {
                Toast.makeText(this, "Berhasil Tambah Data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsynctask().execute();
            }
        } else if (requestCode == AddUpdateActivity.REQUEST_UPDATE) {
            if (resultCode == AddUpdateActivity.RESULT_UPDATE){
                Toast.makeText(this, "Berhasil Update Data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsynctask().execute();
            } else if (resultCode == AddUpdateActivity.RESULT_DELETE) {
                Toast.makeText(this,"Berhasil Delete Data", Toast.LENGTH_SHORT).show();
                new LoadNoteAsynctask().execute();
            }
        }
    }

    private class LoadNoteAsynctask extends AsyncTask<Void, Void, ArrayList<Note>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (listNote.size() > 0) {
                listNote.clear();
            }
        }

        @Override
        protected ArrayList<Note> doInBackground(Void... voids) {
            return noteHelper.getAllNote();
        }

        @Override
        protected void onPostExecute(ArrayList<Note> notes) {
            super.onPostExecute(notes);
            listNote.addAll(notes);
            adapter.setListNote((listNote));
            adapter.notifyDataSetChanged();

            if (listNote.size() == 0){
                Toast.makeText(MainActivity.this, "Data Empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
