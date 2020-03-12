package com.ocr.databaseexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView lRecyclerView = findViewById(R.id.recycler_view);
        lRecyclerView.setLayoutManager((new LinearLayoutManager(this)));
        lRecyclerView.setHasFixedSize(true);
        final NoteAdapter lNoteAdapter = new NoteAdapter();
        lRecyclerView.setAdapter(lNoteAdapter);

        mNoteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> pNotes) {
                //Update recyclerview
                lNoteAdapter.setNotes(pNotes);
            }
        });
    }
}
