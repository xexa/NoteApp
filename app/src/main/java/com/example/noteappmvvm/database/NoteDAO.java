package com.example.noteappmvvm.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.noteappmvvm.model.Note;

import java.util.List;

@Dao
public interface NoteDAO {

    @Insert
    long[] insert(Note... notes);

    @Query("SELECT * FROM notes_table")
    LiveData<List<Note>> getNotes();

    @Delete
    int delete(Note... notes);

    @Update
    int update(Note... notes);
}
