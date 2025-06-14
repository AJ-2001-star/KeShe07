package com.example.mentalhealth.test.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ConclusionDao {
    private MyDatabaseHelper dbHelper;

    public ConclusionDao(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // 插入结论
    public void insert(Conclusion conclusion) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("questionnaireId", conclusion.getQuestionnaireId());
        values.put("minScore", conclusion.getMinScore());
        values.put("maxScore", conclusion.getMaxScore());
        values.put("conclusion", conclusion.getConclusion());

        db.insert("conclusions", null, values);
        db.close();
    }


    // 查询所有结论
//    public List<Conclusion> getAllConclusions() {
//        List<Conclusion> list = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM conclusions", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                Conclusion c = new Conclusion(
//                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
//                        cursor.getInt(cursor.getColumnIndexOrThrow("rank")),
//                        cursor.getString(cursor.getColumnIndexOrThrow("conclusion"))
//                );
//                list.add(c);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return list;
//    }

//    public List<Conclusion> getConclusionsByQuestionnaireId(String questionnaireId) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        List<Conclusion> conclusionList = new ArrayList<>();
//
//        String[] projection = {
//                Conclusion.COLUMN_ID,
//                Conclusion.COLUMN_QUESTIONNAIRE_ID,
//                Conclusion.COLUMN_RANK,
//                Conclusion.COLUMN_CONCLUSION
//        };
//
//        String selection = Conclusion.COLUMN_QUESTIONNAIRE_ID + " = ?";
//        String[] selectionArgs = { String.valueOf(questionnaireId) };
//
//        Cursor cursor = db.query(
//                Conclusion.TABLE_NAME,
//                projection,
//                selection,
//                selectionArgs,
//                null, null, null
//        );
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(cursor.getColumnIndexOrThrow(Conclusion.COLUMN_ID));
//                int rank = cursor.getInt(cursor.getColumnIndexOrThrow(Conclusion.COLUMN_RANK));
//                String conclusionText = cursor.getString(cursor.getColumnIndexOrThrow(Conclusion.COLUMN_CONCLUSION));
//
//                conclusionList.add(new Conclusion(id, String.valueOf(questionnaireId), rank, conclusionText));
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        return conclusionList;
//    }

    // 根据问卷 ID 和 rank 查询结论
//    public Conclusion getConclusionByQuestionnaireIdAndRank(String qId, int rank) {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.rawQuery(
//                "SELECT * FROM conclusions WHERE questionnaireId = ? AND rank = ?",
//                new String[]{qId, String.valueOf(rank)}
//        );
//
//        Conclusion conclusion = null;
//        if (cursor.moveToFirst()) {
//            conclusion = new Conclusion(
//                    cursor.getInt(cursor.getColumnIndexOrThrow("id")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
//                    cursor.getInt(cursor.getColumnIndexOrThrow("rank")),
//                    cursor.getString(cursor.getColumnIndexOrThrow("conclusion"))
//            );
//        }
//        cursor.close();
//        return conclusion;
//    }

    //查询方法（按分数范围）
    public Conclusion getConclusionByScoreRange(String qId, int totalScore) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM conclusions WHERE questionnaireId = ? AND ? BETWEEN minScore AND maxScore",
                new String[]{qId, String.valueOf(totalScore)}
        );

        Conclusion conclusion = null;
        if (cursor.moveToFirst()) {
            conclusion = new Conclusion(
                    cursor.getString(cursor.getColumnIndexOrThrow("questionnaireId")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("minScore")),
                    cursor.getInt(cursor.getColumnIndexOrThrow("maxScore")),
                    cursor.getString(cursor.getColumnIndexOrThrow("conclusion"))
            );
        }
        cursor.close();
        return conclusion;
    }



}