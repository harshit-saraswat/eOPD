package com.example.codezero.eopd;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class ShowDataActivity extends AppCompatActivity {

    EditText usernameET;
    Button generateButton;
    ImageView qrCodeIV;
    String username;
    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        usernameET=(EditText)findViewById(R.id.usernameET);
        generateButton=(Button)findViewById(R.id.generateCode);
        qrCodeIV=(ImageView)findViewById(R.id.qrCode);

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    username=usernameET.getText().toString();
                    String type = "getMed";
                    BackgroundTask backgroundTask = new BackgroundTask();
                    backgroundTask.execute(type, username);
            }
        });
    }


    //BackgroundTask class to get JSON for and thus getting data from the Server.
    class BackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;

        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            if (type.equals("getMed")) {
                try {

                    String username = params[1];


                    URL url = new URL(json_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                    StringBuilder stringBuilder = new StringBuilder();

                    while ((JSON_STRING = bufferedReader.readLine()) != null) {

                        stringBuilder.append(JSON_STRING + "\n");

                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    Log.v("String:",stringBuilder.toString().trim());
                    return stringBuilder.toString().trim();

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {

            json_url = "http://192.168.43.213/getmeddata.php";
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.contains("username")) {
                try {
                    JSONObject root = new JSONObject(result);
                    JSONArray userArray = root.getJSONArray("server_response");

                    String name = "Name: ";
                    String username = "Username: ";
                    String gender = "Gender: ";
                    String age = "Age: ";
                    String bloodGroup = "Blood Group: ";
                    String details = "Details: ";


                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject jsonObject = userArray.getJSONObject(i);
                        name += jsonObject.getString("name");
                        username += jsonObject.getString("username");
                        gender += jsonObject.getString("gender");
                        age += jsonObject.getString("age");
                        bloodGroup += jsonObject.getString("bloodgroup");
                        details += jsonObject.getString("details");
                    }

                    String text2QR="";
                    text2QR+=name;
                    text2QR+="\n"+username;
                    text2QR+="\n"+gender;
                    text2QR+="\n"+age;
                    text2QR+="\n"+bloodGroup;
                    text2QR+="\n"+details;
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text2QR, BarcodeFormat.QR_CODE, 320, 320);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrCodeIV.setImageBitmap(bitmap);
                    }catch (WriterException e){
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                Toast.makeText(ShowDataActivity.this, "No user found...", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }


    }
}
