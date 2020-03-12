package com.ocr.databaseexample;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_NOTE_REQUEST = 1;
    public static final int EDIT_NOTE_REQUEST = 2;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton mButonAddNote = findViewById(R.id.button_add_note);
        mButonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddEditNoteActivity.class);
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

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override  //Drag and drop
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                mNoteViewModel.delete(lNoteAdapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(lRecyclerView);

        lNoteAdapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note pNote) {
                Intent lIntent = new Intent(MainActivity.this, AddEditNoteActivity.class);
                lIntent.putExtra(AddEditNoteActivity.EXTRA_ID,pNote.getId());
                lIntent.putExtra(AddEditNoteActivity.EXTRA_TITLE,pNote.getTitle());
                lIntent.putExtra(AddEditNoteActivity.EXTRA_DESCRIPTION,pNote.getDescription());
                lIntent.putExtra(AddEditNoteActivity.EXTRA_PRIORITY,pNote.getPriority());
                startActivityForResult(lIntent,EDIT_NOTE_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            String lTitle = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String lDescription = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int lPriority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note lNote = new Note(lTitle, lDescription, lPriority);
            mNoteViewModel.insert(lNote);

            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_NOTE_REQUEST && resultCode == RESULT_OK) {
            int lId = data.getIntExtra(AddEditNoteActivity.EXTRA_ID,-1);
            if (lId == -1) {
                Toast.makeText(this, "Note can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String lTitle = data.getStringExtra(AddEditNoteActivity.EXTRA_TITLE);
            String lDescription = data.getStringExtra(AddEditNoteActivity.EXTRA_DESCRIPTION);
            int lPriority = data.getIntExtra(AddEditNoteActivity.EXTRA_PRIORITY, 1);

            Note lNote = new Note(lTitle, lDescription, lPriority);
            lNote.setId(lId);
            mNoteViewModel.update(lNote);

            Toast.makeText(this, "Note updated", Toast.LENGTH_SHORT).show();

        } else  {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater lMenuInflater = getMenuInflater();
        lMenuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_notes:
                mNoteViewModel.deleteAllNotes();
                Toast.makeText(this, "All notes deleted", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
