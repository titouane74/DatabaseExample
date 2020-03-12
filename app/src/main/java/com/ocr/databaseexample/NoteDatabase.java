package com.ocr.databaseexample;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * Created by Florence LE BOURNOT on 12/03/2020
 */
@Database(entities = {Note.class},version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context pContext) {
        if (instance == null )  {
            instance = Room.databaseBuilder(pContext.getApplicationContext(),
                    NoteDatabase.class,"note_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            new PopulateDbAsyncTask(instance).execute();
            super.onCreate(db);
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private NoteDao lNoteDao;

        private PopulateDbAsyncTask(NoteDatabase db) {
            lNoteDao = db.noteDao();
        }
        @Override
        protected Void doInBackground(Void... pVoids) {
            lNoteDao.insert(new Note("Title 1","Description 1", 1));
            lNoteDao.insert(new Note("Title 2","Description 2", 2));
            lNoteDao.insert(new Note("Title 3","Description 3", 3));
            return null;
        }
    }
}
