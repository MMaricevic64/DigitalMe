package com.example.digitalme.nav_fragments.notes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.profile.ProfileViewModel;

public class NotesFragment extends Fragment {

    private NotesViewModel notesViewModel;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        notesViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(NotesViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_notes, container, false);

        return rootView;
    }
}