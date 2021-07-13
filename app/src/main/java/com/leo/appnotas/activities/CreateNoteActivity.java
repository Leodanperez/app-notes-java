package com.leo.appnotas.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.leo.appnotas.database.NotesDatabase;
import com.leo.appnotas.entities.Note;
import com.leo.appnotas.R;

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

        // Seleccionar color y url de la imagen
        selectedNoteColor = "#333333";
        selectedImagePath = "";

        findViewById(R.id.imageRemoveWebURL).setOnClickListener(view -> {
            textWebUrl.setText(null);
            layoutWebURL.setVisibility(View.GONE);
        });

        findViewById(R.id.imageRemoveImage).setOnClickListener(view -> {
            imageNote.setImageBitmap(null);
            imageNote.setVisibility(View.GONE);
            findViewById(R.id.imageRemoveImage).setVisibility(View.GONE);
            selectedImagePath = "";
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                onBackPressed();
                break;
            case R.id.imageSave:
                saveNote();
                break;
        }
    }

    private void saveNote() {
        System.out.println("Llego hasta aqui");
        if (mInputNoteTitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "El titulo no puede estar vacio!", Toast.LENGTH_SHORT).show();
            return;
        } else if (mInputNoteSubtitle.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "El subtitulo no puede estar vacio!", Toast.LENGTH_SHORT).show();
            return;
        }
        final Note note = new Note();
        note.setTitle(mInputNoteTitle.getText().toString());
        note.setSubtitle(mInputNoteSubtitle.getText().toString());
        note.setNoteText(mInputNoteText.getText().toString());
        note.setDateTime(mTextDateTime.getText().toString());
        note.setColor(selectedNoteColor);
        note.setImagePath(selectedImagePath);

        if (layoutWebURL.getVisibility() == View.VISIBLE) {
            note.setWebLink(textWebUrl.getText().toString());
        }

        class saveNoteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                NotesDatabase.getDatabase(getApplicationContext()).noteDao().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        }
        new saveNoteTask().execute();
    }

    private void initMiscellaneous() {
        final LinearLayout layout = findViewById(R.id.layoutMiscellaneous);
        final BottomSheetBehavior<LinearLayout> bottomSheetBehavior = BottomSheetBehavior.from(layout);
        layout.findViewById(R.id.txtSelector).setOnClickListener(view -> {
            if (bottomSheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            }
        });

        final ImageView imageColor1 = layout.findViewById(R.id.imageColor1);
        final ImageView imageColor2 = layout.findViewById(R.id.imageColor2);
        final ImageView imageColor3 = layout.findViewById(R.id.imageColor3);
        final ImageView imageColor4 = layout.findViewById(R.id.imageColor4);
        final ImageView imageColor5 = layout.findViewById(R.id.imageColor5);

        layout.findViewById(R.id.viewColor1).setOnClickListener(view -> {
            selectedNoteColor = "#333333";
            imageColor1.setImageResource(R.drawable.ic_done);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
        });

        layout.findViewById(R.id.viewColor2).setOnClickListener(view -> {
            selectedNoteColor = "#FDBE3B";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(R.drawable.ic_done);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
        });

        layout.findViewById(R.id.viewColor3).setOnClickListener(view -> {
            selectedNoteColor = "#FF4842";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(R.drawable.ic_done);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(0);
        });

        layout.findViewById(R.id.viewColor4).setOnClickListener(view -> {
            selectedNoteColor = "#3A52FC";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(R.drawable.ic_done);
            imageColor5.setImageResource(0);
        });

        layout.findViewById(R.id.viewColor1).setOnClickListener(view -> {
            selectedNoteColor = "#000000";
            imageColor1.setImageResource(0);
            imageColor2.setImageResource(0);
            imageColor3.setImageResource(0);
            imageColor4.setImageResource(0);
            imageColor5.setImageResource(R.drawable.ic_done);
        });

        if (alreadyAvailableNote != null && alreadyAvailableNote.getColor() != null && !alreadyAvailableNote.getColor().trim().isEmpty()) {
            switch (alreadyAvailableNote.getColor()) {
                case "#FDBE3B":
                    layout.findViewById(R.id.viewColor2).performClick();
                    break;
                case "#FF4842":
                    layout.findViewById(R.id.viewColor3).performClick();
                    break;
                case "#3A52FC":
                    layout.findViewById(R.id.viewColor4).performClick();
                    break;
                case "#000000":
                    layout.findViewById(R.id.viewColor5).performClick();
                    break;
            }
        }

        layout.findViewById(R.id.layoutAddImage).setOnClickListener(view -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(CreateNoteActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
            } else {
                selectImage();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }
}