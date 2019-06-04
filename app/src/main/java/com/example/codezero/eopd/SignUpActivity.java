package com.example.codezero.eopd;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText nameET, contactET, addressET, usernameET, passwordET;
    TextView dobTV;
    Spinner genderSP, bloodGroupSP;
    Calendar mDOB;
    int day, month, year;

    String type,name, dob, gender, bloodGroup, contact, address, username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameET = (EditText) findViewById(R.id.nameEditText);
        contactET = (EditText) findViewById(R.id.contactEditText);
        addressET = (EditText) findViewById(R.id.addressEditText);
        usernameET = (EditText) findViewById(R.id.usernameEditText);
        passwordET = (EditText) findViewById(R.id.passwordEditText);

        dobTV = (TextView) findViewById(R.id.dobTextView);

        genderSP = (Spinner) findViewById(R.id.genderSpinner);
        bloodGroupSP = (Spinner) findViewById(R.id.bloodGroupSpinner);

        //Setting the DOB Dialog Box.
        mDOB = Calendar.getInstance();
        day = mDOB.get(Calendar.DAY_OF_MONTH);
        month = mDOB.get(Calendar.MONTH);
        year = mDOB.get(Calendar.YEAR);
        month += 1;

        dobTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog mDatePickerDialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        int month = monthOfYear + 1;
                        dob = dayOfMonth + "/" + month + "/" + year;
                        dobTV.setText(dob);
                    }
                }, year, month, day);
                mDatePickerDialog.show();
            }
        });

        //Setting the spinner for gender list.
        genderSP = (Spinner) findViewById(R.id.genderSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSP.setAdapter(adapter);
        genderSP.setOnItemSelectedListener(this);

        //Setting the spinner for blood group list.
        bloodGroupSP = (Spinner) findViewById(R.id.bloodGroupSpinner);
        adapter = ArrayAdapter.createFromResource(this, R.array.bloodGroup, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroupSP.setAdapter(adapter);
        bloodGroupSP.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;

        switch (spinner.getId()) {
            case R.id.genderSpinner:
                gender = adapterView.getItemAtPosition(i).toString();
                break;
            case R.id.bloodGroupSpinner:
                bloodGroup = adapterView.getItemAtPosition(i).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public void onSignUp(View view){

        name = nameET.getText().toString();
        contact = contactET.getText().toString();
        address = addressET.getText().toString();
        username = usernameET.getText().toString();
        password = passwordET.getText().toString();

        type = "register";

        if (name.length() == 0 || dob.length() == 0 || gender.length() == 0 || contact.length() == 0 || address.length() == 0 || bloodGroup.length() == 0 || username.length() == 0 || password.length() == 0) {

            Toast.makeText(SignUpActivity.this, "One or more fields may be empty.Please fill them.", Toast.LENGTH_LONG).show();

        } else if (contact.length() > 10 || contact.length() < 10) {

            Toast.makeText(SignUpActivity.this, "Contact number should be equal to 10 digits.", Toast.LENGTH_LONG).show();

        } else {
            Log.v("blablabla:",type+name);
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(type, name, dob, gender, bloodGroup, contact, address, username, password);
        }

    }

}
