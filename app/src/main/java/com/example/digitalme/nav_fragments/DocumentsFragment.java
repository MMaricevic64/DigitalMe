package com.example.digitalme.nav_fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.digitalme.R;

public class DocumentsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_documents, container, false);
        final TextView textView = root.findViewById(R.id.text_documents);
        textView.setText("This is documents fragment");
        return root;
    }
}