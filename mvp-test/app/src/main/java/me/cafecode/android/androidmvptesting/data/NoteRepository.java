package me.cafecode.android.androidmvptesting.data;

import java.util.List;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

public interface NoteRepository {

    void addNote(Note note);

    List<Note> getNotes();

}
