package com.ocr.databaseexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 12/03/2020
 */
@Dao
public interface NoteDao {

    @Insert
    void insert(Note pNote);

    @Update
    void update(Note pNote);

    @Delete
    void delete(Note pNote);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
