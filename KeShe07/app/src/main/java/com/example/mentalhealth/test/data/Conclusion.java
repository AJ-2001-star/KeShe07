package com.example.mentalhealth.test.data;

public class Conclusion {
    private int id;
    private String questionnaireId;
    private int minScore;
    private int maxScore;
    private String conclusion;

    public static final String TABLE_NAME = "conclusions";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_QUESTIONNAIRE_ID = "questionnaireId";
    public static final String COLUMN_MIN_SCORE = "minScore";
    public static final String COLUMN_MAX_SCORE = "maxScore";
    public static final String COLUMN_CONCLUSION = "conclusion";

    public Conclusion(String questionnaireId, int minScore, int maxScore, String conclusion) {
        this.questionnaireId = questionnaireId;
        this.minScore = minScore;
        this.maxScore = maxScore;
        this.conclusion = conclusion;
        this.id = -1;
    }

    public int getId() {
        return id;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public int getMinScore() {
        return minScore;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public String getConclusion() {
        return conclusion;
    }
}