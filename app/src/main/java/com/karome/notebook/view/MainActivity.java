package com.karome.notebook.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.karome.notebook.R;
import com.karome.notebook.model.Notebook;
import com.karome.notebook.viewmodel.Adapter;
import com.karome.notebook.viewmodel.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private FloatingActionButton fabAdd;
    private Button deleteAllNotesBtn;
    private List<Notebook> notesList;
    private DatabaseHelper database;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);
        deleteAllNotesBtn = findViewById(R.id.delete_all_notes);

        notesList = new ArrayList<>();
        database = new DatabaseHelper(this);

        fetchAllNotes();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNotesActivity.class));
            }
        });

        deleteAllNotesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, DeleteActivity.class));
            }
        });
    }

    // метод считывания всех записей в бд
    public void fetchAllNotes() {
        Cursor cursor = database.readNotes();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Заметок пока нет", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                notesList.add(new Notebook(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
            }
        }
    }
}