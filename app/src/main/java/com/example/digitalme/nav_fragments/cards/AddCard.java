package com.example.digitalme.nav_fragments.cards;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalme.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddCard extends AppCompatActivity {
    public static final String CREDIT_CARD_DESCRIPTION =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_DESCRIPTION";
    public static final String CREDIT_CARD_NUMBER =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_NUMBER";
    public static final String CREDIT_CARD_CVV =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_CVV";
    public static final String CREDIT_CARD_EXPIRATION =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_EXPIRATION";
    public static final String CREDIT_CARD_OWNER =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_OWNER";
    public static final String CREDIT_CARD_ACCOUNT_NUMBER =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_ACCOUNT_NUMBER";
    public static final String CREDIT_CARD_IBAN =
            "com.example.digitalme.nav_fragments.notes.CREDIT_CARD_IBAN";
    public static final String SHOPPING_CARD_DESCRIPTION =
            "com.example.digitalme.nav_fragments.notes.SHOPPING_CARD_DESCRIPTION";
    public static final String SHOPPING_CARD_NUMBER =
            "com.example.digitalme.nav_fragments.notes.SHOPPING_CARD_NUMBER";
    public static final String SHOPPING_CARD_OWNER =
            "com.example.digitalme.nav_fragments.notes.SHOPPING_CARD_OWNER";
    int ID_card_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get type of card
        Intent intent = getIntent();
        ID_card_type = intent.getIntExtra("ID_TYPE_CARD",0);

        if(ID_card_type == 1) { //Add credit card
            setContentView(R.layout.activity_add_credit_card);

            //Get all elements
            TextInputEditText credit_card_description = findViewById(R.id.credit_card_desc_input_field);
            TextInputEditText credit_card_number = findViewById(R.id.credit_card_number_input_field);
            TextInputEditText credit_card_cvv = findViewById(R.id.credit_card_cvv_input_field);
            TextInputEditText credit_card_expiration = findViewById(R.id.credit_card_expiration_input_field);
            TextInputEditText credit_card_owner = findViewById(R.id.credit_card_owner_input_field);
            TextInputEditText credit_card_account_number = findViewById(R.id.credit_card_account_number_input_field);
            TextInputEditText credit_card_iban = findViewById(R.id.credit_card_iban_input_field);
            Button saveCreditCard = findViewById(R.id.saveCreditCard);

            saveCreditCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create new credit card and add to database
                    //Check if all inputs are not empty
                    if(TextUtils.isEmpty(credit_card_description.getText().toString())){
                        credit_card_description.setError("Your Card description field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_number.getText().toString())){
                        credit_card_number.setError("Your Card number field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_cvv.getText().toString())){
                        credit_card_cvv.setError("Your Card cvv field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_expiration.getText().toString())){
                        credit_card_expiration.setError("Your Card expiration time field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_owner.getText().toString())){
                        credit_card_owner.setError("Your Card owner field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_account_number.getText().toString())){
                        credit_card_account_number.setError("Your Card Account Number field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(credit_card_iban.getText().toString())){
                        credit_card_iban.setError("Your Card IBAN can't be empty!");
                        return;
                    }
                    else{
                        Intent data = new Intent();
                        data.putExtra(CREDIT_CARD_DESCRIPTION, credit_card_description.getText().toString());
                        data.putExtra(CREDIT_CARD_NUMBER, credit_card_number.getText().toString());
                        data.putExtra(CREDIT_CARD_CVV, credit_card_cvv.getText().toString());
                        data.putExtra(CREDIT_CARD_EXPIRATION, credit_card_expiration.getText().toString());
                        data.putExtra(CREDIT_CARD_OWNER, credit_card_owner.getText().toString());
                        data.putExtra(CREDIT_CARD_ACCOUNT_NUMBER, credit_card_account_number.getText().toString());
                        data.putExtra(CREDIT_CARD_IBAN, credit_card_iban.getText().toString());
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }
            });

        }
        else{ //Add shooping card
            setContentView(R.layout.activity_add_shopping_card);

            //Get all elements
            TextInputEditText shopping_card_description = findViewById(R.id.shopping_card_desc_input_field);
            TextInputEditText shopping_card_number = findViewById(R.id.shopping_card_number_input_field);
            TextInputEditText shopping_card_owner = findViewById(R.id.shopping_card_owner_input_field);
            Button saveShoppingCard = findViewById(R.id.saveShoppingCard);

            saveShoppingCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Create new credit card and add to database
                    //Check if all inputs are not empty
                    if(TextUtils.isEmpty(shopping_card_description.getText().toString())){
                        shopping_card_description.setError("Your Card description field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(shopping_card_number.getText().toString())){
                        shopping_card_number.setError("Your Card number field can't be empty!");
                        return;
                    }
                    else if(TextUtils.isEmpty(shopping_card_owner.getText().toString())){
                        shopping_card_owner.setError("Your Card owner field can't be empty!");
                        return;
                    }
                    else{
                        Intent data = new Intent();
                        data.putExtra(SHOPPING_CARD_DESCRIPTION, shopping_card_description.getText().toString());
                        data.putExtra(SHOPPING_CARD_NUMBER, shopping_card_number.getText().toString());
                        data.putExtra(SHOPPING_CARD_OWNER, shopping_card_owner.getText().toString());
                        setResult(RESULT_OK, data);
                        finish();
                    }
                }
            });
        }

    }
}
