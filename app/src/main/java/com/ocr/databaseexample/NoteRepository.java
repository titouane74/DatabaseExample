package com.ocr.databaseexample;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

/**
 * Created by Florence LE BOURNOT on 12/03/2020
 */
public class NoteRepository {

    private NoteDao mNoteDao;
    private LiveData<List<Note>> mAllNotes;

    public NoteRepository(Application pApplication) {
        NoteDatabase lDatabase = NoteDatabase.getInstance(pApplication);
        mNoteDao = lDatabase.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public void insert(Note pNote) {
        new InsertNoteAsyncTask(mNoteDao).execute(pNote);
    }

    public void update(Note pNote) {
        new UpdateNoteAsyncTask(mNoteDao).execute(pNote);

    }

    public void delete(Note pNote) {
        new DeleteNoteAsyncTask(mNoteDao).execute(pNote);

    }

    public void deleteAllnotes() {
        new DeleteAllNoteAsyncTask(mNoteDao).execute();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao lNoteDao;

        private InsertNoteAsyncTask(NoteDao pNoteDao) {
            this.lNoteDao = pNoteDao;
        }

        @Override
        protected Void doInBackground(Note... pNotes) {
            lNoteDao.insert(pNotes[0]);
            return null;
        }
    }
    private static class UpdateNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao lNoteDao;

        private UpdateNoteAsyncTask(NoteDao pNoteDao) {
            this.lNoteDao = pNoteDao;
        }

        @Override
        protected Void doInBackground(Note... pNotes) {
            lNoteDao.update(pNotes[0]);
            return null;
        }
    }
    private static class DeleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao lNoteDao;

        private DeleteNoteAsyncTask(NoteDao pNoteDao) {
            this.lNoteDao = pNoteDao;
        }

        @Override
        protected Void doInBackground(Note... pNotes) {
            lNoteDao.delete(pNotes[0]);
            return null;
        }
    }
    private static class DeleteAllNoteAsyncTask extends AsyncTask<Void, Void, Void> {
        private NoteDao lNoteDao;

        private DeleteAllNoteAsyncTask(NoteDao pNoteDao) {
            this.lNoteDao = pNoteDao;
        }

        @Override
        protected Void doInBackground(Void... pVoids) {
            lNoteDao.deleteAllNotes();
            return null;
        }
    }

}
