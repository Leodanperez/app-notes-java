package com.leo.appnotas.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.leo.appnotas.R;

public class MainActivity extends AppCompatActivity {

    private ImageView imageAddNoteMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageAddNoteMain = findViewById(R.id.imageAddNoteMain);

        imageAddNoteMain.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            finish();
        });
    }
}