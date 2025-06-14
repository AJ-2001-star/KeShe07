package com.example.mentalhealth.test.data;

public class Questionnaire {
    private int id;
    private String questionnaireId;
    private String title;
    private int questionCount;

    public Questionnaire(String questionnaireId, String title, int questionCount) {
        this.questionnaireId = questionnaireId;
        this.title = title;
        this.questionCount = questionCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public String getTitle() {
        return title;
    }

    public int getQuestionCount() {
        return questionCount;
    }
}