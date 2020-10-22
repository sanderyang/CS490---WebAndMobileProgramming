package com.example.basiclogin;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button variables
        Button loginButton = findViewById(R.id.loginButton);

        //Text variables
        final EditText userNameText = findViewById(R.id.userText);
        final EditText userPasswordText = findViewById(R.id.passwordText);

        //Function for login Button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String invalid = "Invalid login";
                String username = userNameText.getText().toString();
                String password = userPasswordText.getText().toString();

                //Function that checks for valid input.
                if (!username.isEmpty() && !password.isEmpty()){
                    if (username.equals("Admin") && password.equals("Admin")){
                        Intent redirect = new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(redirect);
                    }
                    else{
                        alertBox(invalid);
                    }
                }
            }
        });
    }

    //Function for an alert box due to invalid credentials.
    protected void alertBox(String message)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setMessage(message);
        alert.setCancelable(true);

        alert.setPositiveButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert2 = alert.create();
        alert2.show();
    }
}









