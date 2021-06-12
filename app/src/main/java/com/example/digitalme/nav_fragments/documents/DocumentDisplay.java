package com.example.digitalme.nav_fragments.documents;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.digitalme.R;
import com.example.digitalme.nav_fragments.notes.AddEditNoteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DocumentDisplay extends AppCompatActivity {
    private DocumentsImageViewModel documentsImageViewModel;
    int ID_document_type;
    int PERMISSION_READ_EXTERNAL_STORAGE = 1;
    public static final int DISPLAY_DOCUMENT_FULL_SCREEN = 2;
    int SELECT_PHOTO = 2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.document_display);

        //Get document type ID
        Intent intent = getIntent();
        ID_document_type = intent.getIntExtra("ID_TYPE_DOCUMENT",0);

        //Initiate viewModel
        documentsImageViewModel = new ViewModelProvider(this, androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(DocumentsImageViewModel.class);

        //Adding new document
        FloatingActionButton buttonAddDocument = findViewById(R.id.button_add_document);
        buttonAddDocument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) { //Check for permission
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, //If there is no permission ask it
                            PERMISSION_READ_EXTERNAL_STORAGE);
                }
                else{
                    Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, SELECT_PHOTO);
                }
            }
        });

        //Instance recyclerView
        RecyclerView recyclerView = findViewById(R.id.document_galery);
        recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        recyclerView.setHasFixedSize(true);

        //Make adapter for recyclerView
        final DocumentsDisplayAdapter adapter = new DocumentsDisplayAdapter(this);
        recyclerView.setAdapter(adapter);

        //Submit list to adapter
        documentsImageViewModel.getAllDocument(ID_document_type).observe(this, new Observer<List<Document>>() {
            @Override
            public void onChanged(List<Document> document) {
                adapter.submitList(document);
            }
        });

        adapter.setOnItemLongClickListener(new DocumentsDisplayAdapter.LongImageListener() {
            @Override
            public void onLongImageClick(Document document) {
                documentsImageViewModel.delete(document);
            }
        });

        adapter.setOnItemClickListener(new DocumentsDisplayAdapter.ImageListener() {
            @Override
            public void onImageClick(Document document) {
                Intent intent = new Intent(getApplicationContext(), DocumentFullScreen.class);
                intent.putExtra("DOCUMENT_URI", document.getPicture_uri());
                startActivityForResult(intent, DISPLAY_DOCUMENT_FULL_SCREEN);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISSION_READ_EXTERNAL_STORAGE){
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) //Permission granted for camera
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, SELECT_PHOTO);
            }
            else{
                Toast.makeText(getApplicationContext(), "You must allow permission for external storage!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri document_uri;
        if (requestCode == SELECT_PHOTO && resultCode == this.RESULT_OK) {
            //Get Uri of loaded picture
            document_uri = data.getData();
            //Create document and insert it to database
            Document newDocument = new Document(document_uri.toString(),ID_document_type);
            documentsImageViewModel.insert(newDocument);
        }
    }


}
