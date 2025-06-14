package com.example.mentalhealth.test.data;

public class Question {
    private int id;
    private String questionnaireId;
    private String questionText;

    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private int scoreA;
    private int scoreB;
    private int scoreC;
    private int scoreD;

    public Question(String questionnaireId, String questionText,
                    String optionA, String optionB, String optionC, String optionD,
                    int scoreA, int scoreB, int scoreC, int scoreD) {
        this.questionnaireId = questionnaireId;
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.scoreA = scoreA;
        this.scoreB = scoreB;
        this.scoreC = scoreC;
        this.scoreD = scoreD;
    }

    public int getId() {
        return id;
    }

    public void setQuestionnaireId(String questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getScoreA() {
        return scoreA;
    }

    public void setScoreA(int scoreA) {
        this.scoreA = scoreA;
    }

    public int getScoreB() {
        return scoreB;
    }

    public void setScoreB(int scoreB) {
        this.scoreB = scoreB;
    }

    public int getScoreC() {
        return scoreC;
    }

    public void setScoreC(int scoreC) {
        this.scoreC = scoreC;
    }

    public int getScoreD() {
        return scoreD;
    }

    public void setScoreD(int scoreD) {
        this.scoreD = scoreD;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestionnaireId() {
        return questionnaireId;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return new String[]{optionA, optionB, optionC, optionD};
    }

    public int getScoreForOption(int index) {
        switch (index) {
            case 0: return scoreA;
            case 1: return scoreB;
            case 2: return scoreC;
            case 3: return scoreD;
            default: return 0;
        }
    }

    public int getMaxOptionScore() {
        return Math.max(Math.max(scoreA, scoreB), Math.max(scoreC, scoreD));
    }
}
