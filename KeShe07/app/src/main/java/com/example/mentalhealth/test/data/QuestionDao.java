package com.example.mentalhealth.test.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuestionDao {
    private MyDatabaseHelper dbHelper;

    public QuestionDao(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // 插入题目
    public void insert(Question question) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("questionnaireId", question.getQuestionnaireId());
        values.put("questionText", question.getQuestionText());
        values.put("optionA", question.getOptionA());
        values.put("optionB", question.getOptionB());
        values.put("optionC", question.getOptionC());
        values.put("optionD", question.getOptionD());
        values.put("scoreA", question.getScoreA());
        values.put("scoreB", question.getScoreB());
        values.put("scoreC", question.getScoreC());
        values.put("scoreD", question.getScoreD());
        db.insert("questions", null, values);
        db.close();
    }

    // 根据 questionnaireId 查询所有题目
    public List<Question> getQuestionsByQuestionnaireId(String qId) {
        List<Question> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questions WHERE questionnaireId=?", new String[]{qId});

        if (cursor.moveToFirst()) {
            do {
                Question q = new Question(
                        cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("questionText")),
                        cursor.getString(cursor.getColumnIndexOrThrow("optionA")),
                        cursor.getString(cursor.getColumnIndexOrThrow("optionB")),
                        cursor.getString(cursor.getColumnIndexOrThrow("optionC")),
                        cursor.getString(cursor.getColumnIndexOrThrow("optionD")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("scoreA")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("scoreB")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("scoreC")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("scoreD"))
                );
                q.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                list.add(q);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}