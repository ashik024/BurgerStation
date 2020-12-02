package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.rey.material.widget.FloatingActionButton;

public class onBoardingFrag3 extends Fragment {
    TextView skip3;
    FloatingActionButton flt;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragmen3,container,false);

        skip3 =root.findViewById(R.id.skip3);
        flt= root.findViewById(R.id.floatingbtn0);

        skip3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        flt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        return root;
    }
}
