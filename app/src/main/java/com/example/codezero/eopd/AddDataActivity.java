package com.example.codezero.eopd;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class AddDataActivity extends AppCompatActivity {

    TextView newUserTV, oldUserTV;
    EditText pinET;
    String pin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        oldUserTV = (TextView) findViewById(R.id.oldUserTV);
        newUserTV = (TextView) findViewById(R.id.newUserTV);

        pinET = (EditText) findViewById(R.id.pin);

        oldUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pin = pinET.getText().toString();

                if (pin.length() > 4) {
                    Toast.makeText(AddDataActivity.this, "Pin length shouldn't exceed 4 characters.", Toast.LENGTH_SHORT).show();
                }

                if (pin.equals("1234")) {
                    Intent intent = new Intent(AddDataActivity.this, OldUserActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddDataActivity.this, "Wrong Doctor Pin", Toast.LENGTH_SHORT).show();
                }

            }
        });

        newUserTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pin = pinET.getText().toString();

                if (pin.length() > 4) {
                    Toast.makeText(AddDataActivity.this, "Pin length shouldn't exceed 4 characters.", Toast.LENGTH_SHORT).show();
                }
                if (pin.equals("1234")) {
                    Intent intent = new Intent(AddDataActivity.this, NewUserActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddDataActivity.this, "Wrong Doctor Pin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
