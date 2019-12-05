package com.example.chatterbot.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.chatterbot.data.Message;
import com.example.chatterbot.repository.Repository;

public class MainViewModel extends AndroidViewModel
{
    private Repository translatorRepository;
    private OnTranslationResult onTranslationResultListener;
    private boolean waitingResponse;
    private boolean waitingBotTranslation;
    private String translateCountryCode = "es";

    private RecyclerViewAdapter recyclerViewAdapter;

    public MainViewModel(@NonNull Application application)
    {
        super(application);

        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerViewAdapter.addMessage(new Message(false, "¡Hola!"));

        translatorRepository = new Repository();
        translatorRepository.setOnTranslationResultListener(new Repository.OnTranslationResult()
        {
            @Override
            public void OnTranslationResult(boolean ok, String text, String countryCode)
            {
                if(onTranslationResultListener != null)
                    onTranslationResultListener.OnTranslationResult(ok, text, countryCode);
            }
        });
    }

    public RecyclerViewAdapter getRecyclerViewAdapter()
    {
        return recyclerViewAdapter;
    }

    public String getTranslateCountryCode()
    {
        return translateCountryCode;
    }

    public void setTranslateCountryCode(String translateCountryCode)
    {
        this.translateCountryCode = translateCountryCode;
    }

    public void setOnTranslationResultListener(OnTranslationResult onTranslationResultListener)
    {
        this.onTranslationResultListener = onTranslationResultListener;
    }

    public void translate(String fromLang, String text, String to)
    {
        translatorRepository.translate(fromLang, text, to);
    }

    public boolean isWaitingResponse()
    {
        return waitingResponse;
    }

    public void setWaitingResponse(boolean waitingResponse)
    {
        this.waitingResponse = waitingResponse;
    }

    public interface OnTranslationResult
    {
        void OnTranslationResult(boolean ok, String text, String countryCode);
    }

    public boolean isWaitingBotTranslation()
    {
        return waitingBotTranslation;
    }

    public void setWaitingBotTranslation(boolean waitingBotTranslation)
    {
        this.waitingBotTranslation = waitingBotTranslation;
    }

}
