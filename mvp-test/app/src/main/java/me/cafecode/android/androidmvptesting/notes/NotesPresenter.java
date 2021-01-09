package me.cafecode.android.androidmvptesting.notes;

import java.util.List;

import me.cafecode.android.androidmvptesting.data.Note;
import me.cafecode.android.androidmvptesting.data.NoteRepository;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public class NotesPresenter implements NotesContract.ActionsListener {

    private final NoteRepository mNoteRepository;
    private final NotesContract.View mNotesView;

    public NotesPresenter(NoteRepository noteRepository, NotesContract.View notesView) {
        mNoteRepository = noteRepository;
        mNotesView = notesView;
    }

    @Override
    public void addNewNote() {
        mNotesView.showAddNewNote();
    }

    @Override
    public void loadNotes() {
        List<Note> notes = mNoteRepository.getNotes();
        mNotesView.showNotes(notes);
    }

}
