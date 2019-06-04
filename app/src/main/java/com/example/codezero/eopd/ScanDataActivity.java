package com.example.codezero.eopd;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScanDataActivity extends AppCompatActivity {

    Button scanButton;
    TextView scannedDataTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_data);

        scanButton = (Button) findViewById(R.id.scanButton);
        scannedDataTV=(TextView)findViewById(R.id.scannedDataTV);
        final Activity activity = this;

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(activity);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                integrator.setPrompt("Scan");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(false);
                integrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "You cancelled the scanning....", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Scanned Successfully!!!", Toast.LENGTH_SHORT).show();
                String resData = result.getContents();
                scannedDataTV.setText(resData);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
