package com.karome.notebook.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private Context context;

    private static final String DATABASE_NAME = "MyNotes";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "myNotes"; // поле названия таблицы в бд
    private static final String COLUMN_ID = "id"; // поле колонки для айди записей в бд
    private static final String COLUMN_TITLE = "title"; // поле колоники для заголовков записей в бд
    private static final String COLUMN_DESCRIPTION = "description"; // поле для описаний записей в бд

    // при использовании этой бд будет создаваться бд
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    // метод создания таблицы в бд
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " DESC_TEXT);";
        db.execSQL(query);
    }


    // метод обновления таблицы бд
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // запрос на удаление таблицы бд
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // определение метода на создание бд
        onCreate(db);
    }

    // метод записи в бд
    public void addNotes(String title, String description) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESCRIPTION, description);

        // индендификатор создался автоматически при использования метода insert
        long resultValue = db.insert(TABLE_NAME, null, contentValues);

        if (resultValue == -1) {
            Toast.makeText(context, "Данные в БД не добавлены", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Данные в БД добавлены", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readNotes() {
        // формирование запроса в бд
        String query = "SELECT * FROM " + TABLE_NAME;

        // данный метод получает бд для чтения
        SQLiteDatabase db = this.getReadableDatabase();

        // создание пустого курсора
        Cursor cursor = null;

        if (db != null) { // если бд существует
            cursor = db.rawQuery(query, null);
        } return cursor;
    }

    public void deleteAllNotes() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "DELETE FROM " + TABLE_NAME;
        db.execSQL(query);
    }

    public void updateNotes(String title, String description, String id) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_DESCRIPTION, description);

        // в метод update подается следующие данные: название бд, данные для обновления,
        // запись для проверки id и запись в текстовой массив id
        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Обновление не удалось", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Обновление удалось", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteSingleNote(String id) {
        // подключение к бд
        SQLiteDatabase db = this.getWritableDatabase();
        // в метод delete передаются: название бд, запись для проверки id и запись в текстовый массив id
        long result = db.delete(TABLE_NAME, "id=?", new String[]{id});

        if (result == -1) {
            Toast.makeText(context, "Удаление не удалось", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Удаление удалось", Toast.LENGTH_SHORT).show();
        }
    }
}
