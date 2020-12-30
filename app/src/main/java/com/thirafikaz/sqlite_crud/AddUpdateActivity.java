package com.thirafikaz.sqlite_crud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.thirafikaz.sqlite_crud.Database.NoteHelper;
import com.thirafikaz.sqlite_crud.Model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddUpdateActivity extends AppCompatActivity {

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;

    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;

    public static int RESULT_DELETE = 301;

    public static String EXTRA_POSITION = "extra position";
    public static String EXTRA_NOTE = "extra npte";

    EditText edtJudul, edtDesc;
    Button btnSubmit;

    Note dataNote;
    NoteHelper noteHelper;
    boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);

        edtDesc  = findViewById(R.id.edt_deskripsi);
        edtJudul  = findViewById(R.id.edt_judul);
        btnSubmit  = findViewById(R.id.btn_submit);

        noteHelper = new NoteHelper(this);
        noteHelper.open();

        dataNote =getIntent().getParcelableExtra(EXTRA_NOTE);

        if (dataNote != null) {
            isUpdate = true;
        }

        String actionBarTitle, btnTitle;

        if (isUpdate && dataNote != null) {
            actionBarTitle =  "Update Data";
            btnTitle = "Update";

            edtJudul.setText(dataNote.getJudul());
            edtDesc.setText(dataNote.getDeskripsi());
        } else {
            actionBarTitle = "Add Data";
            btnTitle = "Add";
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(actionBarTitle);
        }
        btnSubmit.setText(btnTitle);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = edtJudul.getText().toString();
                String desc = edtDesc.getText().toString();

                if (TextUtils.isEmpty(judul) || TextUtils.isEmpty(desc)) {
                    Toast.makeText(AddUpdateActivity.this,"Can't Be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    Note newNote = new Note();
                    newNote.setJudul(judul);
                    newNote.setDeskripsi(desc);

                    if (isUpdate) {
                        newNote.setTanggal(dataNote.getTanggal());
                        newNote.setId(dataNote.getId());

                        noteHelper.updateData(newNote);
                        setResult(RESULT_UPDATE);
                        finish();
                    }else {
                        newNote.setTanggal(currentDate());

                        noteHelper.insertData(newNote);
                        setResult(RESULT_ADD);
                        finish();
                    }
                }
            }
        });
    }

    private String currentDate(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isUpdate) {
            getMenuInflater().inflate(R.menu.delete_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mn_delete){
            noteHelper.deleteData(dataNote.getId());
            setResult(RESULT_DELETE);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
