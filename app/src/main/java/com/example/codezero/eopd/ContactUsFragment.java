package com.example.codezero.eopd;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactUsFragment extends Fragment {

    EditText etName, etAadhaar, etEmail, etQuery;
    Button sendBtn;

    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        //Recognise the views and buttons.
        etName = (EditText) view.findViewById(R.id.etname);
        etAadhaar = (EditText) view.findViewById(R.id.etaadhaar);
        etEmail = (EditText) view.findViewById(R.id.etemail);
        etQuery = (EditText) view.findViewById(R.id.etquery);
        sendBtn = (Button) view.findViewById(R.id.sendbutton);

        //Send button on click listener to send an email intent.
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String data = "Name: " + etName.getText().toString();
                data += "\nContact No.: " + etAadhaar.getText().toString();
                data += "\nEmail: " + etEmail.getText().toString();
                data += "\nQuery: " + etQuery.getText().toString();

                if (etAadhaar.getText().toString().length() > 10||etAadhaar.getText().toString().length()<10) {
                    Toast.makeText(getContext(), "Contact No. should be equal to 10 digits", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setData(Uri.parse("mailto:"));
                    intent.setType("*/*");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Contacting for a query.");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "saraswat2204@gmail.com" });
                    intent.putExtra(Intent.EXTRA_TEXT, data);
                    startActivity(Intent.createChooser(intent, "Send Mail"));
                }
            }
        });

        return view;
    }

}
