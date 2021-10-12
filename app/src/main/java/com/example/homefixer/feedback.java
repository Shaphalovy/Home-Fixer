package com.example.homefixer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class feedback extends AppCompatActivity  implements View.OnClickListener {
    private EditText nameEditText,feedBackEditText;
    private Button sendButton,clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        nameEditText = findViewById(R.id.nameEditText);
        feedBackEditText = findViewById(R.id.feedbackEditText);

        sendButton = findViewById(R.id.sendButton);
        clearButton = findViewById(R.id.clearButton);

        sendButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        String name = nameEditText.getText().toString();
        String feedback = feedBackEditText.getText().toString();

        if(view.getId()==R.id.sendButton){

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/email");
            intent.putExtra(Intent.EXTRA_EMAIL,new String[]{"paholoby@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT,"FeedBack from APP");
            intent.putExtra(Intent.EXTRA_TEXT,"Name: "+name+"\n Message: "+feedback);
            startActivity(Intent.createChooser(intent,"Feedback with: "));
        }
        if(view.getId()==R.id.clearButton){
            nameEditText.setText("");
            feedBackEditText.setText("");
        }
    }
}