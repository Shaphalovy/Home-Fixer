package com.example.homefixer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailEditText,passwordEditText;
    private Button loginButton;
    private TextView signupTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEditText = findViewById(R.id.signInEmailId);
        passwordEditText = findViewById(R.id.signInPasswordId);
        loginButton = findViewById(R.id.loginButtonId);
        signupTextview = findViewById(R.id.signUpHereTextViewId);


        loginButton.setOnClickListener(this);
        signupTextview.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){


            case R.id.loginButtonId:

                Intent intent1 = new Intent(MainActivity.this,Timeline.class);
                startActivity(intent1);
                break;






            case R.id.signUpHereTextViewId:
                Intent intent2 = new Intent(MainActivity.this,SignUp.class);
                startActivity(intent2);
                break;
        }
    }
}