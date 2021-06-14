package com.example.digitalme.nav_fragments.cards;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.digitalme.R;

public class BusinessCardAdd extends AppCompatActivity {
    public static final String BUSINESS_CARD_OWNER =
            "com.example.digitalme.nav_fragments.cards.BUSINESS_CARD_OWNER";
    public static final String BUSINESS_CARD_LOCATION =
            "com.example.digitalme.nav_fragments.cards.BUSINESS_CARD_LOCATION";
    public static final String BUSINESS_CARD_EMAIL =
            "com.example.digitalme.nav_fragments.cards.BUSINESS_CARD_EMAIL";
    public static final String BUSINESS_CARD_PHONE =
            "com.example.digitalme.nav_fragments.cards.BUSINESS_CARD_PHONE";
    public static final String BUSINESS_CARD_COMPANY =
            "com.example.digitalme.nav_fragments.cards.BUSINESS_CARD_COMPANY";
    Button saveBusinessCard;
    String decodedString;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.business_card_item_preview);

        Intent intent = getIntent();
        decodedString = intent.getStringExtra("BUSINESS_CARD_INFO");

        //Get all elements
        TextView business_card_owner = findViewById(R.id.business_add_card_owner);
        TextView business_card_location = findViewById(R.id.business_add_card_location);
        TextView business_card_email = findViewById(R.id.business_add_card_email);
        TextView business_card_phone = findViewById(R.id.business_add_card_phone);
        TextView business_card_company = findViewById(R.id.business_add_card_company);

        //Decode string into array
        String[] separated = decodedString.split("&");

        //Fill elements
        business_card_owner.setText(separated[0]);
        business_card_location.setText(separated[1]);
        business_card_email.setText(separated[2]);
        business_card_phone.setText(separated[3]);
        business_card_company.setText(separated[4]);

        saveBusinessCard = findViewById(R.id.saveBusinessCard);
        saveBusinessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent();
                data.putExtra(BUSINESS_CARD_OWNER, business_card_owner.getText().toString());
                data.putExtra(BUSINESS_CARD_LOCATION, business_card_location.getText().toString());
                data.putExtra(BUSINESS_CARD_EMAIL, business_card_email.getText().toString());
                data.putExtra(BUSINESS_CARD_PHONE, business_card_phone.getText().toString());
                data.putExtra(BUSINESS_CARD_COMPANY, business_card_company.getText().toString());
                setResult(RESULT_OK, data);
                finish();
            }
        });
    }
}
