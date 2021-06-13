package com.example.digitalme.nav_fragments.cards;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.documents.DocumentsImageViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CardDisplay extends AppCompatActivity {
    private CardsDisplayViewModel cardViewModel;
    int ID_card_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_display);

        //Get document type ID
        Intent intent = getIntent();
        ID_card_type = intent.getIntExtra("ID_TYPE_CARD",0);

        //Initiate viewModel
        cardViewModel = new ViewModelProvider(this, androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CardsDisplayViewModel.class);

        //Adding new card
        FloatingActionButton buttonAddDocument = findViewById(R.id.button_add_card);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Call new intent for adding card
            }
        });

        //Create recyclerView
        RecyclerView recyclerView = findViewById(R.id.card_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        //Initialize adapter
        final CardDisplayAdapter adapter = new CardDisplayAdapter();
        recyclerView.setAdapter(adapter);

    }
}
