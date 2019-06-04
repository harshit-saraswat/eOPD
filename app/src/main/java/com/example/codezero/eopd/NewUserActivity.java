package com.example.codezero.eopd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUserActivity extends AppCompatActivity {

    EditText nameet, usernameet,ageet,genderet,bloodgroupet,hospitalet,testet,diagnosiset,surgeryet,medicineet;
    String name,username,age,gender,bloodgroup,hospital,test,diagnosis,surgery,medicine,details;
    String type="postMedNew";

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        nameet=(EditText)findViewById(R.id.name_ET);
        usernameet=(EditText)findViewById(R.id.username_ET);
        ageet=(EditText)findViewById(R.id.age_ET);
        genderet=(EditText)findViewById(R.id.gender_ET);
        bloodgroupet=(EditText)findViewById(R.id.bloodgroup_ET);
        hospitalet=(EditText)findViewById(R.id.hospital_ET);
        testet=(EditText)findViewById(R.id.test_ET);
        diagnosiset=(EditText)findViewById(R.id.diagnosis_ET);
        surgeryet=(EditText)findViewById(R.id.surgeries_ET);
        medicineet=(EditText)findViewById(R.id.medicines_ET);

        submit=(Button)findViewById(R.id.submitButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=nameet.getText().toString();
                username=usernameet.getText().toString();
                age=ageet.getText().toString();
                gender=genderet.getText().toString();
                bloodgroup=bloodgroupet.getText().toString();
                hospital=hospitalet.getText().toString();
                test=testet.getText().toString();
                diagnosis=diagnosiset.getText().toString();
                surgery=surgeryet.getText().toString();
                medicine=medicineet.getText().toString();

                details="Hospital Name:"+hospital;
                details+="\nTests Done:"+test;
                details+="\nDiagnosis Made:"+diagnosis;
                details+="\nSurgeries:"+surgery;
                details+="\nMedicines:"+medicine;

                if (name.length() == 0 || username.length() == 0 || gender.length() == 0 || age.length() == 0 || bloodgroup.length() == 0 || hospital.length() == 0 || test.length() == 0|| surgery.length() == 0|| medicine.length() == 0|| diagnosis.length() == 0) {

                    Toast.makeText(NewUserActivity.this, "One or more fields may be empty.Please fill them.", Toast.LENGTH_LONG).show();

                } else {
                    Log.v("blablabla:",type+name);
                    BackgroundWorker backgroundWorker = new BackgroundWorker(view.getContext());
                    backgroundWorker.execute(type, name, username, gender,age, bloodgroup, details);
                }

            }
        });

    }
}
