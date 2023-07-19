package com.karome.notebook.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.karome.notebook.R;
import com.karome.notebook.viewmodel.DatabaseHelper;

public class DeleteActivity extends AppCompatActivity {
    private static final String TAG = "DeleteActivity";

    private Button deleteAllNotesBtn, backToMainActivityBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        deleteAllNotesBtn = findViewById(R.id.delete_all_notes);
        deleteAllNotesBtn.setOnClickListener(listener);
        backToMainActivityBtn = findViewById(R.id.back_to_main_activity);
        backToMainActivityBtn.setOnClickListener(listener);
    }
    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            DatabaseHelper database = new DatabaseHelper(getApplicationContext());
            switch (view.getId()) {
                case R.id.delete_all_notes:
                    try {
                        database.deleteAllNotes();
                    } catch (NullPointerException e) {
                        Log.e(TAG, "onClick: NullPointException occurred" + e.getMessage());
                        e.printStackTrace();
                    }
                    startActivity(intent);
                    break;
                case R.id.back_to_main_activity:
                    Log.d(TAG, "onClick R.id.back_to_main_activity: Back to MainActivity");
                    startActivity(intent);
                    break;
            }
        }
    };
}