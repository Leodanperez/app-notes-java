package com.leo.appnotas;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imageBack, imageView;
    private EditText mInputNoteTitle, mInputNoteSubtitle, mInputNoteText;
    private TextView mTextDateTime;
    private View viewSubtitleIndicator;
    private ImageView imageNote;
    private TextView textWebUrl;
    private LinearLayout layoutWebURL;

    private String selectedNoteColor;
    private String selectedImagePath;

    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    private static final int REQUEST_CODE_SELECT_IMAGE = 2;

    private AlertDialog dialogAddURL;
    private AlertDialog dialogDeleteNote;

    private Note alreadyAvailableNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        imageBack = findViewById(R.id.imageBack);
        imageView = findViewById(R.id.imageSave);

        mInputNoteTitle = findViewById(R.id.inputNoteTitle);
        mInputNoteSubtitle = findViewById(R.id.inputNoteSubTitle);
        mInputNoteText = findViewById(R.id.inputNote);
        mTextDateTime = findViewById(R.id.textDateTime);
        viewSubtitleIndicator = findViewById(R.id.viewSubTitleIndicator);
        imageNote = findViewById(R.id.imageNote);
        textWebUrl = findViewById(R.id.textWebURL);
        layoutWebURL = findViewById(R.id.layoutWebUrl);

        mTextDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault())
                .format(new Date())
        );

        imageBack.setOnClickListener(this);
        imageView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}