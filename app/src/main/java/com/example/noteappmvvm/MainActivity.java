package com.example.noteappmvvm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toolbar;

import com.example.noteappmvvm.adapters.NoteAdapter;
import com.example.noteappmvvm.database.NoteRepository;
import com.example.noteappmvvm.model.Note;
import com.example.noteappmvvm.util.VerticalSpacingItemDecorator;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        NoteAdapter.OnNoteListener,
        View.OnClickListener {

    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private NoteAdapter noteAdapter;
    private ArrayList<Note> notes = new ArrayList<>();

    private NoteRepository noteRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        findViewById(R.id.fab).setOnClickListener(this);
        initRecyclerView();

        Toolbar toolbar = findViewById(R.id.note_toolbar);

        setActionBar(toolbar);

        noteRepository = new NoteRepository(this);

        retrieveNotes();


        setTitle("Notes");
    }

    private void retrieveNotes(){
        noteRepository.retrieveNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> noteList) {
                if (notes.size() > 0){
                    notes.clear();
                }
                if (notes != null){
                    notes.addAll(noteList);
                }
                noteAdapter.notifyDataSetChanged();
            }
        });
    }


    private void initRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        recyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(simpleCallback).attachToRecyclerView(recyclerView);
        noteAdapter = new NoteAdapter(notes, this);
        recyclerView.setAdapter(noteAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Intent intent = new Intent(MainActivity.this,NoteActivity.class);
        intent.putExtra("selected_note", notes.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fab){
            Intent intent = new Intent(MainActivity.this, NoteActivity.class);
            startActivity(intent);
        }
    }

    private void deleteNote(Note note){
        notes.remove(note);
        noteAdapter.notifyDataSetChanged();

    }

    private ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteNote(notes.get(viewHolder.getAdapterPosition()));
        }
    };
}