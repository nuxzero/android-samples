package me.cafecode.android.androidmvptesting.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class NoteRepositories implements NoteRepository {

    private static NoteRepository mNoteRepository = null;


    private List<Note> mNotes = null;

    public NoteRepositories() {
        mNotes = new ArrayList<>();
    }

    public synchronized static NoteRepository instance() {

        if (mNoteRepository == null) {
            mNoteRepository = new NoteRepositories();
        }
        return mNoteRepository;
    }

    @Override
    public void addNote(Note note) {
        mNotes.add(note);
    }

    @Override
    public List<Note> getNotes() {
        return mNotes;
    }
}
