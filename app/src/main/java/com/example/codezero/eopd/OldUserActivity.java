package com.example.codezero.eopd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OldUserActivity extends AppCompatActivity {

    EditText usernameET,hospitalET,testET,diagnosisET,surgeryET,medicineET;
    String username,hospital,test,diagnosis,surgery,medicine,details;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_user);

       /* username = getIntent().getExtras().getString("username");
        details = getIntent().getExtras().getString("details");
*/
        usernameET=(EditText)findViewById(R.id.username_ET);
        hospitalET=(EditText)findViewById(R.id.hospital_ET);
        testET=(EditText)findViewById(R.id.test_ET);
        diagnosisET=(EditText)findViewById(R.id.diagnosis_ET);
        surgeryET=(EditText)findViewById(R.id.surgeries_ET);
        medicineET=(EditText)findViewById(R.id.medicines_ET);

        submitButton=(Button)findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                username=usernameET.getText().toString();
                hospital=hospitalET.getText().toString();
                test=testET.getText().toString();
                diagnosis=diagnosisET.getText().toString();
                surgery=surgeryET.getText().toString();
                medicine=medicineET.getText().toString();

                details+="\n******************\nHospital Name:"+hospital;
                details+="\nTests Done:"+test;
                details+="\nDiagnosis Made:"+diagnosis;
                details+="\nSurgeries:"+surgery;
                details+="\nMedicines:"+medicine;

                String type="postMed";
                if (hospital.length() == 0 || test.length() == 0 || diagnosis.length() == 0 || surgery.length() == 0 || medicine.length() == 0) {

                    Toast.makeText(OldUserActivity.this, "One or more fields may be empty.Please fill them.", Toast.LENGTH_LONG).show();

                } else {
                    Log.v("blablabla:",type+username);
                    BackgroundWorker backgroundWorker = new BackgroundWorker(view.getContext());
                    backgroundWorker.execute(type, username,details);
                }

            }
        });
    }
}
