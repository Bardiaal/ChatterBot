package com.example.chatterbot.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetectedLanguage
{
    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("score")
    @Expose
    private int score;

    public DetectedLanguage(String language, int score)
    {
        this.language = language;
        this.score = score;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public int getScore()
    {
        return score;
    }

    public void setScore(int score)
    {
        this.score = score;
    }
}
