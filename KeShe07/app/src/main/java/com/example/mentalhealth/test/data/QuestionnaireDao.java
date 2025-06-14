package com.example.mentalhealth.test.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireDao {
    private MyDatabaseHelper dbHelper;

    public QuestionnaireDao(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // 插入问卷
    public long insert(Questionnaire questionnaire) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("questionnaireId", questionnaire.getQuestionnaireId());
        values.put("title", questionnaire.getTitle());
        values.put("questionCount", questionnaire.getQuestionCount());
        return db.insert("questionnaires", null, values);
    }

    // 查询所有问卷
    public List<Questionnaire> getAllQuestionnaires() {
        List<Questionnaire> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questionnaires", null);

        if (cursor.moveToFirst()) {
            do {
                Questionnaire q = new Questionnaire(
                        cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
                        cursor.getString(cursor.getColumnIndexOrThrow("title")),
                        cursor.getInt(cursor.getColumnIndexOrThrow("questionCount"))
                );
                q.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
                list.add(q);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // 根据 questionnaireId 查询
    public Questionnaire getByQuestionnaireId(String qId) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM questionnaires WHERE questionnaireId=?", new String[]{qId});

        Questionnaire q = null;
        if (cursor.moveToFirst()) {
            q = new Questionnaire(
                    cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
                    cursor.getString(cursor.getColumnIndexOrThrow("title")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("questionCount"))
            );
            q.setId(cursor.getInt(cursor.getColumnIndexOrThrow("id")));
        }
        cursor.close();
        db.close();
        return q;
    }

    // 删除某条记录
    public void delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("questionnaires", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
}