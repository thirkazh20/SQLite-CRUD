package com.thirafikaz.sqlite_crud.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thirafikaz.sqlite_crud.AddUpdateActivity;
import com.thirafikaz.sqlite_crud.Model.Note;
import com.thirafikaz.sqlite_crud.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private ArrayList<Note> data;
    private Activity activity;
    ArrayList<Note> getListNote() {return data;}

    public NoteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListNote(ArrayList<Note> data){
        this.data = data;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteViewHolder(LayoutInflater.from(activity).inflate(R.layout.item_nota, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, final int position) {
        holder.tvJudul.setText(getListNote().get(position).getJudul());
        holder.tvDesc.setText(getListNote().get(position).getDeskripsi());
        holder.tvTanggal.setText(getListNote().get(position).getTanggal());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(activity, AddUpdateActivity.class);
                intent.putExtra(AddUpdateActivity.EXTRA_NOTE, getListNote().get(position));
                intent.putExtra(AddUpdateActivity.EXTRA_POSITION, position);
                activity.startActivityForResult(intent, AddUpdateActivity.REQUEST_UPDATE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {

        TextView tvJudul, tvDesc, tvTanggal;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvJudul = itemView.findViewById(R.id.tv_judul);
            tvDesc = itemView.findViewById(R.id.tv_deskripsi);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
        }
    }
}
