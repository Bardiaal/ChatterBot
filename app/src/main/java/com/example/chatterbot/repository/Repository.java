package com.example.chatterbot.repository;

import com.example.chatterbot.data.DetectedLanguage;
import com.example.chatterbot.data.Translation;
import com.example.chatterbot.data.TranslationResponse;
import com.example.chatterbot.rest.Client;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Repository
{
    private Client translatorClient;
    private OnTranslationResult onTranslationResultListener;

    public Repository()
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.bing.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        translatorClient = retrofit.create(Client.class);
    }

    public void translate(String fromLang, String text, String to)
    {
        Call<List<TranslationResponse>> call = translatorClient.postTranslator(fromLang, text, to);
        call.enqueue(new Callback<List<TranslationResponse>>()
        {
            @Override
            public void onResponse(Call<List<TranslationResponse>> call, Response<List<TranslationResponse>> response)
            {
                if(onTranslationResultListener != null)
                {
                    DetectedLanguage detectedLanguage = response.body().get(0).getDetectedLanguage();
                    Translation translation = response.body().get(0).getTranslations().get(0);

                    onTranslationResultListener.OnTranslationResult(true, translation.getText(), detectedLanguage.getLanguage());
                }
            }

            @Override
            public void onFailure(Call<List<TranslationResponse>> call, Throwable t)
            {
                if(onTranslationResultListener != null)
                    onTranslationResultListener.OnTranslationResult(false, "", "");
            }
        });
    }

    public void setOnTranslationResultListener(OnTranslationResult onTranslationResultListener)
    {
        this.onTranslationResultListener = onTranslationResultListener;
    }

    public interface OnTranslationResult
    {
        void OnTranslationResult(boolean ok, String text, String countryCode);
    }
}
