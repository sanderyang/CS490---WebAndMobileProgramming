package com.example.application;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class StorageActivity extends AppCompatActivity {
    EditText txt_content;
    EditText contenttoDisplay;
    String FILENAME = "MyAppStorage";
    String textReceived;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        txt_content = (EditText) findViewById(R.id.id_txt_mycontent);
        contenttoDisplay = (EditText) findViewById(R.id.id_txt_display);
    }

    public void saveTofile(View v) throws IOException {

        String textContent = txt_content.getText().toString();
        FileOutputStream fileOutputStream = null;
        getApplicationContext();
        fileOutputStream = openFileOutput(FILENAME, MODE_APPEND);
        fileOutputStream.write(textContent.getBytes());
        Toast.makeText(this, "Content Saved to File.", Toast.LENGTH_SHORT).show();
        fileOutputStream.close();
    }

    public void retrieveFromFile(View v) throws IOException {

        String textContentReceived = "";
        FileInputStream fileInputStream = null;
        fileInputStream = openFileInput(FILENAME);
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        StringBuilder strBuilder = new StringBuilder();
        while ((textContentReceived = bufferedReader.readLine()) != null){
            strBuilder.append(textContentReceived);
        }
        fileInputStream.close();
        textReceived = strBuilder.toString();
        contenttoDisplay.setText(textReceived);
        contenttoDisplay.setVisibility(View.VISIBLE);
        contenttoDisplay.setText(txt_content.getText());
    }
}
