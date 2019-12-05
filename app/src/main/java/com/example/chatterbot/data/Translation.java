package com.example.chatterbot.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Translation
{
    @SerializedName("text")
    @Expose
    private String text;

    @SerializedName("to")
    @Expose
    private String to;

    public Translation(String text, String to)
    {
        this.text = text;
        this.to = to;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getTo()
    {
        return to;
    }

    public void setTo(String to)
    {
        this.to = to;
    }
}
