package com.ocr.databaseexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mButonAddNote = findViewById(R.id.button_add_note);
        mButonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddNoteActivity.class);
                startActivityForResult(intent, ADD_NOTE_REQUEST);
            }
        });

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String lTitle = data.getStringExtra(AddNoteActivity.EXTRA_TITLE);
            String lDescription = data.getStringExtra(AddNoteActivity.EXTRA_DESCRIPTION);
            int lPriority = data.getIntExtra(AddNoteActivity.EXTRA_PRIORITY,1);

            Note lNote = new Note(lTitle, lDescription, lPriority);
            mNoteViewModel.insert(lNote);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }
}
