package com.example.digitalme.nav_fragments.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.digitalme.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class CardDisplay extends AppCompatActivity {
    public static final int ADD_NEW_CREDIT_CARD = 1;
    public static final int ADD_NEW_SHOOPING_CARD = 1;

    private CardsDisplayViewModel cardDisplayViewModel;
    int ID_card_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_display);

        //Get document type ID
        Intent intent = getIntent();
        ID_card_type = intent.getIntExtra("ID_TYPE_CARD",0);

        //Initiate viewModel
        cardDisplayViewModel = new ViewModelProvider(this, androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CardsDisplayViewModel.class);

        //Adding new card
        FloatingActionButton buttonAddDocument = findViewById(R.id.button_add_card);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID_card_type == 0) { //Add credit card
                    Intent intent = new Intent(getApplicationContext(), AddCard.class);
                    intent.putExtra("ID_TYPE_CARD", ID_card_type);
                    startActivityForResult(intent, ADD_NEW_CREDIT_CARD);
                }
                else if(ID_card_type == 1){ //Add shooping card
                    Intent intent = new Intent(getApplicationContext(), AddCard.class);
                    intent.putExtra("ID_TYPE_CARD", ID_card_type);
                    startActivityForResult(intent, ADD_NEW_SHOOPING_CARD);
                }
            }
        });

        //Create recyclerView
        RecyclerView recyclerView = findViewById(R.id.card_gallery);
        final CardDisplayAdapter adapter = new CardDisplayAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        //Initialize adapter



        //Submit list to adapter
        cardDisplayViewModel.getAllCards(ID_card_type).observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                adapter.submitList(cards);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cardDisplayViewModel.delete(adapter.getCardAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(),"Card deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_CREDIT_CARD && resultCode == RESULT_OK) {
            //Get credit card info and insert in database
            String credit_card_description = data.getStringExtra(AddCard.CREDIT_CARD_DESCRIPTION);
            String credit_card_number = data.getStringExtra(AddCard.CREDIT_CARD_NUMBER);
            String credit_card_cvv = data.getStringExtra(AddCard.CREDIT_CARD_CVV);
            String credit_card_expiration = data.getStringExtra(AddCard.CREDIT_CARD_EXPIRATION);
            String credit_card_owner = data.getStringExtra(AddCard.CREDIT_CARD_OWNER);
            String credit_card_account_number = data.getStringExtra(AddCard.CREDIT_CARD_ACCOUNT_NUMBER);
            String credit_card_iban = data.getStringExtra(AddCard.CREDIT_CARD_IBAN);

            Card newCreditCard = new Card(credit_card_description,credit_card_number,credit_card_cvv,
                    credit_card_expiration,credit_card_owner,credit_card_account_number,credit_card_iban,ID_card_type);
            cardDisplayViewModel.insert(newCreditCard);
        }
        else if(requestCode == ADD_NEW_SHOOPING_CARD && resultCode == RESULT_OK){ //Get shooping card info and insert in database

        }
    }

}
