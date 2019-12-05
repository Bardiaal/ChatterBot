package com.example.chatterbot.data;

public class Message
{
    private boolean outcoming;
    private String message;

    public Message(boolean outcoming, String message)
    {
        this.outcoming = outcoming;
        this.message = message;
    }

    public boolean isOutcoming()
    {
        return outcoming;
    }

    public String getMessage()
    {
        return message;
    }

}
