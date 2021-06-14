package com.example.digitalme.nav_fragments.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.documents.DocumentDisplay;
import com.example.digitalme.nav_fragments.documents.DocumentType;
import com.example.digitalme.nav_fragments.documents.DocumentTypeAdapter;

import java.util.List;


public class CardsFragment extends Fragment {

    public static final int DISPLAY_CARDS = 1;
    private CardsViewModel cardsViewModel;
    private View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cardsViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getActivity().getApplication())).get(CardsViewModel.class);
        rootView = inflater.inflate(R.layout.fragment_cards, container, false);

        //Create recyclerView
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view_card_types);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //Initialize adapter
        final CardTypeAdapter adapter = new CardTypeAdapter();
        recyclerView.setAdapter(adapter);

        //Fill in adapter
        cardsViewModel.getAllCardTypes().observe(getViewLifecycleOwner(), new Observer<List<CardType>>() {
            @Override
            public void onChanged(List<CardType> cardTypes) {
                if(cardTypes.size() == 0){
                    //Add Card types to database
                    CardType creditCardType = new CardType("Kreditne kartice");
                    CardType shoppingCardType = new CardType("Shopping kartice");
                    CardType eVisitCardType = new CardType("e-Vizitke");
                    cardsViewModel.insert(creditCardType);
                    cardsViewModel.insert(shoppingCardType);
                    cardsViewModel.insert(eVisitCardType);
                }
                    adapter.submitList(cardTypes);
            }
        });

        //Set on click listener for each item in recyclerView
        adapter.setOnItemClickListener(new CardTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CardType cardType) {
                //Otvori novi intent i posalji ID itema
                Intent intent = new Intent(getActivity(), CardDisplay.class);
                intent.putExtra("ID_TYPE_CARD", cardType.getId());
                startActivityForResult(intent, DISPLAY_CARDS);
            }
        });

        return rootView;
    }
}

