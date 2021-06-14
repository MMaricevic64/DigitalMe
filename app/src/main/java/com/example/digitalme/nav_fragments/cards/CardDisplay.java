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
    public static final int ADD_NEW_SHOOPING_CARD = 2;
    public static final int ADD_NEW_BUSINESS_CARD = 3;

    private CardsDisplayViewModel cardDisplayViewModel;
    private BusinessCardDisplayViewModel businessCardDisplayViewModel;

    int ID_card_type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_display);

        //Get document type ID
        Intent intent = getIntent();
        ID_card_type = intent.getIntExtra("ID_TYPE_CARD",0);

        //Initiate viewModel
        if(ID_card_type == 1 || ID_card_type == 2) {
            cardDisplayViewModel = new ViewModelProvider(this, androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(CardsDisplayViewModel.class);
        }
        else{
            businessCardDisplayViewModel = new ViewModelProvider(this, androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(BusinessCardDisplayViewModel.class);
        }

        //Adding new card
        FloatingActionButton buttonAddDocument = findViewById(R.id.button_add_card);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ID_card_type == 1) { //Add credit card
                    Intent intent = new Intent(getApplicationContext(), AddCard.class);
                    intent.putExtra("ID_TYPE_CARD", ID_card_type);
                    startActivityForResult(intent, ADD_NEW_CREDIT_CARD);
                }
                else if(ID_card_type == 2){ //Add shooping card
                    Intent intent = new Intent(getApplicationContext(), AddCard.class);
                    intent.putExtra("ID_TYPE_CARD", ID_card_type);
                    startActivityForResult(intent, ADD_NEW_SHOOPING_CARD);
                }
                else if(ID_card_type == 3){ //Add bussiness card
                    Intent intent = new Intent(getApplicationContext(),QRScanner.class);
                    startActivityForResult(intent, ADD_NEW_BUSINESS_CARD);
                }
            }
        });

        //Create recyclerView
        RecyclerView recyclerView = findViewById(R.id.card_gallery);
        if(ID_card_type == 1){
            final CardDisplayAdapter adapter = new CardDisplayAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

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
        else if (ID_card_type == 2){
            final ShoppingCardDisplayAdapter adapter = new ShoppingCardDisplayAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

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
        else if(ID_card_type == 3){
            final BusinessCardDisplayAdapter adapter = new BusinessCardDisplayAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

            //Submit list to adapter
            businessCardDisplayViewModel.getAllBusinessCards(ID_card_type).observe(this, new Observer<List<BusinessCard>>() {
                @Override
                public void onChanged(List<BusinessCard> businessCards) {
                    adapter.submitList(businessCards);
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
                    businessCardDisplayViewModel.delete(adapter.getBusinessCardAt(viewHolder.getAdapterPosition()));
                    Toast.makeText(getApplicationContext(),"Business card deleted", Toast.LENGTH_SHORT).show();
                }
            }).attachToRecyclerView(recyclerView);

        }

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
        else if(requestCode == ADD_NEW_SHOOPING_CARD && resultCode == RESULT_OK){
            //Get shooping card info and insert in database
            String shopping_card_description = data.getStringExtra(AddCard.SHOPPING_CARD_DESCRIPTION);
            String shopping_card_number = data.getStringExtra(AddCard.SHOPPING_CARD_NUMBER);
            String shopping_card_owner = data.getStringExtra(AddCard.SHOPPING_CARD_OWNER);

            Card newShoppingCard = new Card(shopping_card_description,shopping_card_number,"", "",
                    shopping_card_owner,"","",ID_card_type);
            cardDisplayViewModel.insert(newShoppingCard);
        }
        else if(requestCode == ADD_NEW_BUSINESS_CARD && resultCode == RESULT_OK){
            //Get business card info and insert in database
            String business_card_owner = data.getStringExtra(BusinessCardAdd.BUSINESS_CARD_OWNER);
            String business_card_location = data.getStringExtra(BusinessCardAdd.BUSINESS_CARD_LOCATION);
            String business_card_email = data.getStringExtra(BusinessCardAdd.BUSINESS_CARD_EMAIL);
            String business_card_phone = data.getStringExtra(BusinessCardAdd.BUSINESS_CARD_PHONE);
            String business_card_company = data.getStringExtra(BusinessCardAdd.BUSINESS_CARD_COMPANY);

            BusinessCard newBusinessCard = new BusinessCard(business_card_owner,business_card_location,
                    business_card_email,business_card_phone,business_card_company,ID_card_type);
            businessCardDisplayViewModel.insert(newBusinessCard);
        }
    }

}
