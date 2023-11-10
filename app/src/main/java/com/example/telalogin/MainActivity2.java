package com.example.telalogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.chip.ChipGroup;

public class MainActivity2 extends AppCompatActivity {
    Button btnVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        TextView textEmail = findViewById(R.id.textEmail);

        Intent intent = getIntent();
        if (intent != null) {
            String email = intent.getStringExtra("email_digitado");
            textEmail.setText("Olá: " + email);
        }
    }

}