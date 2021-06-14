package com.example.digitalme.nav_fragments.cards;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.digitalme.R;
import com.google.zxing.Result;

public class QRScanner extends AppCompatActivity{
    public static final int ADD_NEW_CARD = 1;
    CodeScanner codeScanner;
    CodeScannerView codeScannerView;
    String decodedString;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner_activity);

        codeScannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this,codeScannerView);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                decodedString = result.getText();
                Intent intent = new Intent(getApplicationContext(),BusinessCardAdd.class);
                intent.putExtra("BUSINESS_CARD_INFO", decodedString);
                startActivityForResult(intent, ADD_NEW_CARD);
            }
        });
    }

    @Override
    protected void onResume(){
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_NEW_CARD && resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

}
