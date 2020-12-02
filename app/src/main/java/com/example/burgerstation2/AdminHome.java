package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AdminHome extends AppCompatActivity {
    Button Logout;
    Button orders;
    Button signupres;
    Button maintain;
    String resname="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Logout = findViewById(R.id.Logout);
        signupres = findViewById(R.id.signupres);
        orders= findViewById(R.id.checkorderse);
        maintain= findViewById(R.id.maintainItems);

        resname= getIntent().getExtras().get("namerestu").toString();
        Log.i("info",resname);


        signupres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminHome.this,AdminPanel.class);
                startActivity(intent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminHome.this,MainActivity.class);
                startActivity(intent);
            }
        });
        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(AdminHome.this,CheckOrders.class);
                intent2.putExtra("resNN",resname);
                startActivity(intent2);

            }
        });

        maintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminHome.this,Home.class);
                intent.putExtra("resN",resname);
                startActivity(intent);
            }
        });




    }
}