package com.ocr.databaseexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_ID =
            "com.ocr.databaseexample.EXTRA_ID";
    public static final String EXTRA_TITLE =
            "com.ocr.databaseexample.EXTRA_TITLE";
    public static final String EXTRA_DESCRIPTION =
            "com.ocr.databaseexample.EXTRA_DESCRIPTION";
    public static final String EXTRA_PRIORITY =
            "com.ocr.databaseexample.EXTRA_PRIORITY";

    private EditText mEditTextTitle;
    private EditText mEditTextDescription;
    private NumberPicker mNumberPickerPriority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        mEditTextTitle = findViewById(R.id.edit_text_title);
        mEditTextDescription = findViewById(R.id.edit_text_description);
        mNumberPickerPriority = findViewById(R.id.number_picker_priority);

        mNumberPickerPriority.setMinValue(1);
        mNumberPickerPriority.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent lIntent= getIntent();
        if (lIntent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Note");
            mEditTextTitle.setText(lIntent.getStringExtra(EXTRA_TITLE));
            mEditTextDescription.setText(lIntent.getStringExtra(EXTRA_DESCRIPTION));
            mNumberPickerPriority.setValue(lIntent.getIntExtra(EXTRA_PRIORITY,1));
        } else {
            setTitle("Add note");
        }

    }

    private void saveNote() {
        String lTitle = mEditTextTitle.getText().toString();
        String lDescription = mEditTextDescription.getText().toString();
        int lPriority = mNumberPickerPriority.getValue();

        if (lTitle.trim().isEmpty() || lDescription.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a title and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TITLE,lTitle);
        data.putExtra(EXTRA_DESCRIPTION, lDescription);
        data.putExtra(EXTRA_PRIORITY,lPriority);

        int pId = getIntent().getIntExtra(EXTRA_ID,-1);
        if (pId != -1) {
            data.putExtra(EXTRA_ID, pId);
        }

        setResult(RESULT_OK, data);
        finish();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater lMenuInflater = getMenuInflater();
        lMenuInflater.inflate(R.menu.add_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                saveNote();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
