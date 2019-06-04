package com.example.codezero.eopd;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainViewActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    android.support.v4.app.FragmentTransaction fragmentTransaction;
    NavigationView navigationView;
    View headerView;
    TextView nameTextView,usernameTextView,dobTextView,bloodGroupTextView,contactTextView;

    String JSON_STRING;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);

        String username = getIntent().getExtras().getString("username");
        String type = "getData";

        BackgroundTask backgroundTask = new BackgroundTask();
        backgroundTask.execute(type, username);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerView = navigationView.getHeaderView(0);

        nameTextView = (TextView) headerView.findViewById(R.id.nameTextView);
        usernameTextView = (TextView) headerView.findViewById(R.id.usernameTextView);
        dobTextView = (TextView) headerView.findViewById(R.id.dobTextView);
        bloodGroupTextView = (TextView) headerView.findViewById(R.id.bloodGroupTextView);
        contactTextView = (TextView) headerView.findViewById(R.id.contactTextView);

        //Navigation Drawer work.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //Load the Dashboard fragment.
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.main_container, new DashboardFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("Dashboard Page");

        //Set On click events for items in our navigation view.
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {

                    case R.id.db:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new DashboardFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Dashboard");
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.aboutUs:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new AboutUsFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("About Us");
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.getHelp:
                        Toast.makeText(navigationView.getContext(), "Finding nearest hospitals", Toast.LENGTH_LONG).show();
                        // Search for hospitals nearby
                        Uri gmmIntentUri = Uri.parse("geo:0,0?q=hospitals");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                        break;
                    case R.id.contactUs:
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.main_container, new ContactUsFragment());
                        fragmentTransaction.commit();
                        getSupportActionBar().setTitle("Contact Us");
                        item.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        Toast.makeText(navigationView.getContext(), "Loging Out...", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(navigationView.getContext(), SplashScreen.class);
                        startActivity(intent);
                        break;
                }

                return true;
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //BackgroundTask class to get JSON for and thus getting data from the Server.
    class BackgroundTask extends AsyncTask<String, Void, String> {

        String json_url;

        @Override
        protected String doInBackground(String... params) {

            String type = params[0];
            if (type.equals("getData")) {
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

            json_url = "http://192.168.43.213/jsongetdata.php";
        }

        @Override
        protected void onPostExecute(String result) {

            nameTextView.setText(result);
            if (result.contains("username")) {
                try {
                    JSONObject root = new JSONObject(result);
                    JSONArray userArray = root.getJSONArray("server_response");

                    String name = "Name: ";
                    String username = "Username: ";
                    String dob = "DOB: ";
                    String bloodGroup = "Blood Group: ";
                    String contact = "Contact: ";


                    for (int i = 0; i < userArray.length(); i++) {
                        JSONObject jsonObject = userArray.getJSONObject(i);
                        name += jsonObject.getString("name");
                        username += jsonObject.getString("username");
                        dob += jsonObject.getString("dob");
                        bloodGroup += jsonObject.getString("bloodgroup");
                        contact += jsonObject.getString("contact");
                    }
                    nameTextView.setText(name);
                    usernameTextView.setText(username);
                    dobTextView.setText(dob);
                    bloodGroupTextView.setText(bloodGroup);
                    contactTextView.setText(contact);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }


    }
}
