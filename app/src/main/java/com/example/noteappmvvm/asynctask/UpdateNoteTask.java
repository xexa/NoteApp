package com.example.noteappmvvm.asynctask;

import android.os.AsyncTask;

import com.example.noteappmvvm.database.NoteDAO;
import com.example.noteappmvvm.model.Note;

public class UpdateNoteTask extends AsyncTask<Note ,Void , Void> {
    private NoteDAO noteDAO;

    public UpdateNoteTask(NoteDAO dao) {
        this.noteDAO = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.update(notes);
        return null;
    }
}
