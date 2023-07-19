package com.karome.notebook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karome.notebook.R;
import com.karome.notebook.viewmodel.DatabaseHelper;

public class AddNotesActivity extends AppCompatActivity {
    private static final String TAG = "AddNotesActivity";
    private EditText title, description;
    private Button addNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notes);

        title = findViewById(R.id.title_edit);
        description = findViewById(R.id.description_edit);
        addNote = findViewById(R.id.add_note);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                    DatabaseHelper database = new DatabaseHelper(AddNotesActivity.this);
                    database.addNotes(title.getText().toString(), description.getText().toString());

                    Intent intent = new Intent(AddNotesActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(AddNotesActivity.this, "Необходимо заполнить оба поля", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}