package com.example.digitalme.nav_fragments.documents;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.digitalme.R;

public class DocumentFullScreen extends AppCompatActivity {

    String document_uri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_full_screen_layout);

        //Get document URI
        Intent intent = getIntent();
        document_uri = intent.getStringExtra("DOCUMENT_URI");

        //Display it in imageView
        ImageView full_screen_img = findViewById(R.id.document_full_screen_image);
        Glide.with(getApplicationContext()).load(document_uri).into(full_screen_img);
    }
}
