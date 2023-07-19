package com.karome.notebook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karome.notebook.R;
import com.karome.notebook.viewmodel.DatabaseHelper;

public class UpdateActivity extends AppCompatActivity {
    private static final String TAG = "UpdateActivity";
    private EditText title, description;
    private Button addButton, deleteButton;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        addButton = findViewById(R.id.updateButton);
        addButton.setOnClickListener(listener);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(listener);

        Intent intent = getIntent();

        title.setText(intent.getStringExtra("title"));
        description.setText(intent.getStringExtra("description"));
        id = intent.getStringExtra("id");
        Log.d(TAG, "Received intent title= " + title.getText() + " Received description= " + description.getText() + " Received id= " + id);
    }

    public View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())) {
                DatabaseHelper database = new DatabaseHelper(getApplicationContext());

                switch (view.getId()) {
                    case R.id.updateButton:
                        Log.d(TAG, "onClick: updating notes");
                        database.updateNotes(title.getText().toString(), description.getText().toString(), id);
                        break;
                    case R.id.deleteButton:
                        Log.d(TAG, "onClick: deleting notes");
                        database.deleteSingleNote(id);
                        break;
                }
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            } else {
                Toast.makeText(getApplicationContext(), "Изменений не внесено", Toast.LENGTH_SHORT).show();
            }
        }
    };
}