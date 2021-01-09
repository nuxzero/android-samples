package me.cafecode.android.androidmvptesting.notes;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import me.cafecode.android.androidmvptesting.data.Note;
import me.cafecode.android.androidmvptesting.data.NoteRepository;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Natthawut Hemathulin on 4/15/2017 AD.
 * Email: natthawut1991@gmail.com
 */

@RunWith(MockitoJUnitRunner.class)
public class NotesPresenterTest {

    private List<Note> NOTES = Arrays.asList(new Note(1, "Title 1", "Description 1"), new Note(2, "Title 2", "Description 2"));

    @Mock
    private NotesContract.View mNotesView;

    @Mock
    private NoteRepository mNoteRepository;

    private NotesPresenter mNotesPresenter;

    @Before
    public void setUp() {
        mNotesPresenter = new NotesPresenter(mNoteRepository, mNotesView);
    }

    @Test
    public void showNotes_showNotesIfThereIsNotes() {
        // Give
        when(mNoteRepository.getNotes()).thenReturn(NOTES);

        // When
        mNotesPresenter.loadNotes();

        // Then
        verify(mNotesView).showNotes(NOTES);
    }

    @Test
    public void addNewNote_showNewNoteView() {
        // Give

        // When
        mNotesPresenter.addNewNote();

        // Then
        verify(mNotesView).showAddNewNote();
    }

}
