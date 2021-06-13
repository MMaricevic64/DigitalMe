package com.example.digitalme.nav_fragments.cards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;

import com.example.digitalme.R;


public class CardsFragment extends Fragment {

    private CardsViewModel cardsViewModel;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(CardsViewModel.class);
        rootView = inflater.inflate(R.layout.card_type_item, container, false);

        //Toast.makeText(getContext(),"Probaaaaaaaaaaa", Toast.LENGTH_LONG).show();

        return rootView;
    }
}

