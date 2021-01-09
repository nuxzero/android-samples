package me.cafecode.android.androidmvptesting.notes;

import java.util.List;

import me.cafecode.android.androidmvptesting.data.Note;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface NotesContract {

    interface View {

        void showNotes(List<Note> notes);

        void showAddNewNote();
    }

    interface ActionsListener {

        void addNewNote();

        void loadNotes();
    }

}
