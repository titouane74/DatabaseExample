package com.ocr.databaseexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 12/03/2020
 */
public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mRepository;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public void insert(Note pNote) {
        mRepository.insert(pNote);
    }

    public void update(Note pNote) {
        mRepository.update(pNote);
    }

    public void delete(Note pNote) {
        mRepository.delete(pNote);
    }

    public void deleteAllNotes() {
        mRepository.deleteAllnotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }
}
