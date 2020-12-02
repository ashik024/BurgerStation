package com.example.burgerstation2;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class AddMenu extends AppCompatActivity {

    TextView Fname;
    TextView Fdescription;
    TextView Rprice;

    Button add;
    Button save;
    List<String> size;
    AppCompatSpinner appCompatSpinner;

    LinearLayout relative;
    String productkey;
    String saveCurrentDate;
    String saveCurrentTime;
    public DatabaseReference databaseReference;
    String spinner;

    String sizeeBurger;
    String namebur;
    String des;
    String price;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);


        add = findViewById(R.id.ADD);
        save = findViewById(R.id.save);
        relative = findViewById(R.id.rela2);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Restaurant").child("Menu");


        size= new ArrayList<>();


        size.add("Mini");
        size.add("Regular");
        size.add("Large");


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                addView();
                Toast.makeText(AddMenu.this, "Complete The Field And Press save.", Toast.LENGTH_LONG).show();
                add.setVisibility(View.INVISIBLE);



            }

        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeproductkey();
                ValidateInformation();


                add.setVisibility(View.VISIBLE);

            }
        });



    }



    private void addView() {

       final View v = getLayoutInflater().inflate(R.layout.additems,null,false);

        Fname = v.findViewById(R.id.itemname);
        Fdescription = v.findViewById(R.id.itemdeDescription);
        Rprice = v.findViewById(R.id.itemPrice);
         appCompatSpinner = (AppCompatSpinner)v.findViewById(R.id.spinner);




         ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,size);
         appCompatSpinner.setAdapter(arrayAdapter);

        relative.addView(v);





    }


    private void ValidateInformation() {

        namebur= Fname.getText().toString();
        des= Fdescription.getText().toString();
        price= Rprice.getText().toString()+"BDT";

        if (TextUtils.isEmpty(namebur)){
            Toast.makeText(this, "Enter Item Name.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(des)){
            Toast.makeText(this, "Enter Item Description.", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(price)){
            Toast.makeText(this, "Enter Item Price.", Toast.LENGTH_SHORT).show();
        }else{
            SaveMenu();
        }

    }


    private void makeproductkey() {



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productkey = saveCurrentDate + saveCurrentTime;

        Log.i("info",productkey);



    }


    private void SaveMenu() {

        HashMap<String,Object> menuMap= new HashMap<>();
        menuMap.put("MenuId",productkey);
        menuMap.put("Item Name",namebur);
        menuMap.put("Description",des);
        menuMap.put("Size",sizeeBurger);
        menuMap.put("Price",price);

        //String id = databaseReference.push().getKey();
       // MenuAdd menuAdd= new MenuAdd(id,name,des,price);



        databaseReference.child(productkey).updateChildren(menuMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful()){
                    Toast.makeText(AddMenu.this, "Menu Added", Toast.LENGTH_SHORT).show();

                }
            }
        });


    }


}