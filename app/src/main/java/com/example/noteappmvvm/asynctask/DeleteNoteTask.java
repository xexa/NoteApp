package com.example.noteappmvvm.asynctask;

import android.os.AsyncTask;

import com.example.noteappmvvm.database.NoteDAO;
import com.example.noteappmvvm.model.Note;

public class DeleteNoteTask extends AsyncTask<Note ,Void , Void> {
    private NoteDAO noteDAO;

    public DeleteNoteTask(NoteDAO dao) {
        this.noteDAO = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        noteDAO.delete(notes);
        return null;
    }
}
