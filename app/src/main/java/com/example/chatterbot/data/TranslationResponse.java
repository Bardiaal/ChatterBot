package com.example.chatterbot.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TranslationResponse
{
    @SerializedName("detectedLanguage")
    @Expose
    private DetectedLanguage detectedLanguage;

    @SerializedName("translations")
    @Expose
    private List<Translation> translations;

    public TranslationResponse(DetectedLanguage detectedLanguage, List<Translation> translations)
    {
        this.detectedLanguage = detectedLanguage;
        this.translations = translations;
    }

    public DetectedLanguage getDetectedLanguage()
    {
        return detectedLanguage;
    }

    public void setDetectedLanguage(DetectedLanguage detectedLanguage)
    {
        this.detectedLanguage = detectedLanguage;
    }

    public List<Translation> getTranslations()
    {
        return translations;
    }

    public void setTranslations(List<Translation> translations)
    {
        this.translations = translations;
    }
}
