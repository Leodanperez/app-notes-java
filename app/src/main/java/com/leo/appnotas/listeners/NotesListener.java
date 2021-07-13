package com.leo.appnotas.listeners;

import com.leo.appnotas.entities.Note;

public interface NotesListener {
    void onNoteClicked(Note note, int position);
}
