package com.example.noteappmvvm.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteappmvvm.R;
import com.example.noteappmvvm.model.Note;
import com.example.noteappmvvm.util.Utility;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewholder> {

    private static final String TAG = "NoteAdapter";

    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteListener mOnNoteListener;

    public NoteAdapter(ArrayList<Note> noteList, OnNoteListener onNoteListener) {
        this.notes = noteList;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_list_item,parent,false);

        return new MyViewholder(view, mOnNoteListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {

        try {
            String month = notes.get(position).getTimestamp().substring(0,2);
            month = Utility.getCurrentMonth(month);

            String year = notes.get(position).getTimestamp().substring(3);
            String timestamp = month + " " + year;
            holder.timestampTextView.setText(timestamp);

            holder.titleTextView.setText(notes.get(position).getTitle());
        }catch (NullPointerException e){
            Log.i(TAG, "onBindViewHolder: "+e.getLocalizedMessage());
        }


    }

    @Override
    public int getItemCount() {
        if (notes.size() > 0)
            return notes.size();
        return 0;
    }

    class MyViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView titleTextView;
        private TextView timestampTextView;

        OnNoteListener onNoteListener;

        public MyViewholder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title_text_view);
            timestampTextView = itemView.findViewById(R.id.timestamp_text_view);
            this.onNoteListener = onNoteListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());

        }
    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
