package com.example.noteappmvvm.asynctask;

import android.os.AsyncTask;

import com.example.noteappmvvm.database.NoteDAO;
import com.example.noteappmvvm.model.Note;

public class InsertNoteTask extends AsyncTask<Note, Void ,Void> {

    private NoteDAO noteDAO;

    public InsertNoteTask(NoteDAO dao) {
        this.noteDAO = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.insert(notes);
        return null;
    }
}
