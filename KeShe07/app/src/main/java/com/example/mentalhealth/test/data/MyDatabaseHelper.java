package com.example.mentalhealth.test.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "mental_health.db";
    private static final int DATABASE_VERSION = 3;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建 conclusions 表
        String CREATE_CONCLUSIONS_TABLE = "CREATE TABLE conclusions (" +
                "id INTEGER PRIMARY KEY," +
                "questionnaireId TEXT NOT NULL," +
                "minScore INTEGER NOT NULL," +   // 新增
                "maxScore INTEGER NOT NULL," +   // 新增
                "conclusion TEXT NOT NULL" +
                ")";

        // 创建 questions 表
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE questions (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "questionnaireId TEXT NOT NULL," +
                "questionText TEXT NOT NULL," +
                "optionA TEXT NOT NULL," +
                "optionB TEXT NOT NULL," +
                "optionC TEXT NOT NULL," +
                "optionD TEXT NOT NULL," +
                "scoreA INTEGER NOT NULL," +
                "scoreB INTEGER NOT NULL," +
                "scoreC INTEGER NOT NULL," +
                "scoreD INTEGER NOT NULL" +
                ")";

        // 创建 questionnaires 表
        String CREATE_QUESTIONNAIRES_TABLE = "CREATE TABLE questionnaires (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "questionnaireId TEXT NOT NULL UNIQUE," + // 唯一标识符如"a1"
                "title TEXT NOT NULL," +
                "questionCount INTEGER NOT NULL" +
                ")";

        db.execSQL(CREATE_CONCLUSIONS_TABLE);
        db.execSQL(CREATE_QUESTIONS_TABLE);
        db.execSQL(CREATE_QUESTIONNAIRES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS conclusions");
        db.execSQL("DROP TABLE IF EXISTS questions");
        db.execSQL("DROP TABLE IF EXISTS questionnaires");
        onCreate(db);
    }
}