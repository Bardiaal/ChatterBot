package com.example.chatterbot;

import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.chatterbot.apibot.ChatterBot;
import com.example.chatterbot.apibot.ChatterBotFactory;
import com.example.chatterbot.apibot.ChatterBotSession;
import com.example.chatterbot.apibot.ChatterBotType;
import com.example.chatterbot.data.Message;
import com.example.chatterbot.view.MainViewModel;
import com.example.chatterbot.view.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private MainViewModel mainViewModel;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        init();
    }

    private void init()
    {
        //View Models
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerViewAdapter = mainViewModel.getRecyclerViewAdapter();

        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount());


        final EditText etText = findViewById(R.id.etText);
        FloatingActionButton btSend = findViewById(R.id.btSend);
        btSend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String text = etText.getText().toString();
                if(text.length() > 0 && !mainViewModel.isWaitingResponse())
                {
                    addMessage(true, text);
                    etText.setText("");

                    mainViewModel.setWaitingResponse(true);
                    mainViewModel.translate("es", text, "en");
                }
            }
        });

        mainViewModel.setOnTranslationResultListener(new MainViewModel.OnTranslationResult()
        {
            @Override
            public void OnTranslationResult(boolean ok, String text, String countryCode)
            {
                if(ok) {
                    if(mainViewModel.isWaitingBotTranslation()) {
                        addMessage(false, text);
                        mainViewModel.setWaitingResponse(false);
                        mainViewModel.setWaitingBotTranslation(false);
                    }
                    else {
                        mainViewModel.setTranslateCountryCode(countryCode);
                        new BotChat().execute(text);
                    }
                }
                else {
                    addMessage(false, "¡Error!");
                    mainViewModel.setWaitingResponse(false);
                    mainViewModel.setWaitingBotTranslation(false);
                }
            }
        });
    }

    private void addMessage(boolean outcoming, String text)
    {
        recyclerViewAdapter.addMessage(new Message(outcoming, text));
        recyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount());

    }

    private class BotChat extends AsyncTask<String, Void, String>
    {
        @Override
        protected String doInBackground(String... strings)
        {
            return chat(strings[0]);
        }

        @Override
        protected void onPostExecute(String s)
        {
            mainViewModel.translate("en", s, "es");
            mainViewModel.setWaitingBotTranslation(true);
        }
    }

    private String chat(String message) {
        String response = "";
        try {
            ChatterBotFactory factory = new ChatterBotFactory();
            ChatterBot bot = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession botSession = bot.createSession();
            response = botSession.think(message);
        }
        catch(Exception e) {
            AlertDialog alertDialog;
            AlertDialog.Builder alertDialog_builder = new AlertDialog.Builder(this)
                    .setTitle("No hay conexión")
                    .setMessage("No se ha podido enviar la petición de respuesta")
                    .setPositiveButton("OK", null);
            alertDialog = alertDialog_builder.create();
            alertDialog.show();
        }
        return response;
    }
}
