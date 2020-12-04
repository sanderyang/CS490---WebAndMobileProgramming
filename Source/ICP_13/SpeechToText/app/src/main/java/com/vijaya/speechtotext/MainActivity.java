package com.vijaya.speechtotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    private ImageButton mSpeakBtn;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVoiceInputTv = findViewById(R.id.voiceInput);
        mSpeakBtn = findViewById(R.id.btnSpeak);

        preferences = getSharedPreferences("prefs", 0);
        editor = preferences.edit();

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS){
                    int language = textToSpeech.setLanguage(Locale.US);
                    textToSpeech.speak("Hello.", TextToSpeech.QUEUE_FLUSH, null);
                    mVoiceInputTv.setText(Html.fromHtml("Hello."));
                }
                else{
                    Log.e("TTS", "Unable to initialize the Google text-to-speech.");
                }
            }
        });

        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result != null && result.size() > 0) {
                        if (result.get(0).equalsIgnoreCase("hello")){
                            textToSpeech.speak("What is your name", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("What is your name?"));
                        } else if (result.get(0).contains("name")){
                            String userName = result.get(0).substring(result.get(0).lastIndexOf(' ') + 1);
                            editor.putString("userName", userName).apply();
                            textToSpeech.speak("Hello " + userName, TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("Hello " + userName));
                        } else if (result.get(0).contains("not feeling well")){
                            textToSpeech.speak("I can understand. Please tell your symptoms in short.", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("I can understand. Please tell your symptoms in short."));
                        } else if (result.get(0).contains("thank you")){
                            textToSpeech.speak("Thank you too " + preferences.getString("userName", "") + " Take care.", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("Thank you too " + preferences.getString("userName", "") + ". Take care."));
                        } else if (result.get(0).contains("time")){
                            SimpleDateFormat sdfDate = new SimpleDateFormat("hh:mm");
                            Date now = new Date();
                            String[] strDate = sdfDate.format(now).split(":");
                            if (strDate[1].contains("00")){
                                strDate[1] = "o'clock";
                            }
                            textToSpeech.speak("The time is " + sdfDate.format(now), TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("The time is " + strDate[0] + ":" + strDate[1]));
                        } else if (result.get(0).contains("take")){
                            textToSpeech.speak("I think you have a fever. Please take this medicine.", TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("I think you have a fever. Please take this medicine."));
                        } else{
                            textToSpeech.speak("I am sorry. I only have limited capabilities. Please ask any of the questions on the screen.",
                                    TextToSpeech.QUEUE_FLUSH, null);
                            mVoiceInputTv.setText(Html.fromHtml("I am not feeling well. What should I do? <br> What time is it? <br>" +
                                    "What medicines should I take?"));
                        }
                    }
                }
                break;
            }

        }
    }
}