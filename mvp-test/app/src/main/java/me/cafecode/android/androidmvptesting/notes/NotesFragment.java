package me.cafecode.android.androidmvptesting.notes;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import me.cafecode.android.androidmvptesting.R;
import me.cafecode.android.androidmvptesting.data.Note;
import me.cafecode.android.androidmvptesting.data.NoteRepositories;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotesFragment extends Fragment implements NotesContract.View {

    private NotesPresenter mActionListener;

    public NotesFragment() {
        // Required empty public constructor
    }

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActionListener = new NotesPresenter(NoteRepositories.instance(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void showNotes(List<Note> notes) {

    }

    @Override
    public void showAddNewNote() {

    }

}
