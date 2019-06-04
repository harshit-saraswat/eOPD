package com.example.codezero.eopd;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {


    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_dashboard, container, false);

        LinearLayout showData = (LinearLayout) view.findViewById(R.id.showData);
        LinearLayout addData = (LinearLayout) view.findViewById(R.id.addData);
        LinearLayout scanData = (LinearLayout) view.findViewById(R.id.scanData);

        scanData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),ScanDataActivity.class);
                startActivity(intent);
            }
        });

        showData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),ShowDataActivity.class);
                startActivity(intent);
            }
        });

        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(view.getContext(),AddDataActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
