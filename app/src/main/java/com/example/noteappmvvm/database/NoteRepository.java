package com.example.noteappmvvm.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.noteappmvvm.asynctask.DeleteNoteTask;
import com.example.noteappmvvm.asynctask.InsertNoteTask;
import com.example.noteappmvvm.model.Note;

import java.util.List;

public class NoteRepository {
    private NoteDatabase noteDatabase;
    private NoteDAO noteDAO;

    public NoteRepository(Context context) {
        noteDatabase = NoteDatabase.getInstance(context);

        noteDAO = noteDatabase.noteDAO();
    }

    public void insertNote(Note note) {
        new InsertNoteTask(noteDAO).execute(note);
    }

    public void deleteNote(Note note) {
        new DeleteNoteTask(noteDAO).execute(note);
    }

    public void updateNote(Note note) {
    }

    public LiveData<List<Note>> retrieveNotes(){
        return noteDatabase.noteDAO().getNotes();
    }
}
