package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Information extends AppCompatActivity {

    EditText nameC;
    EditText phoneC;
    EditText addressC;
    Button confirm;

    String name;
    String phone;
    String address;

    String saveCurrentDate;
    String saveCurrentTime;
    String productkey;

    String Pricetotal;
   String ResturantNameOrder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);


        nameC= findViewById(R.id.cName);
        phoneC= findViewById(R.id.cphone);
        addressC= findViewById(R.id.cAdress);
        Pricetotal= getIntent().getStringExtra("totalAmount");
        ResturantNameOrder= getIntent().getStringExtra("namerest");
        confirm= findViewById(R.id.confirmOrder);



        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checkinfo();
            }
        });
    }





    private void Checkinfo() {

        name= nameC.getText().toString();
        phone= phoneC.getText().toString();
        address= addressC.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Your Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Enter Your Phone Number", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter Your Address", Toast.LENGTH_SHORT).show();
        }
        else {

            confirm();
        }

    }

    private void confirm() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productkey = saveCurrentDate + saveCurrentTime;

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Orders").child(ResturantNameOrder).child(Prevelant.onlineUsers.userId);

        HashMap<String,Object> ordermap= new HashMap<>();

        ordermap.put("TotalPrice",Pricetotal);
        ordermap.put("RestaurantName",ResturantNameOrder);
        ordermap.put("Id",Prevelant.onlineUsers.userId);
        ordermap.put("Name",name);
        ordermap.put("Phone",phone);
        ordermap.put("Address",address);
        ordermap.put("Date",saveCurrentDate);
        ordermap.put("Time",saveCurrentTime);
        ordermap.put("State","Not Confirmed");

        databaseReference.updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){
                    FirebaseDatabase.getInstance().getReference().child("CartList")
                            .child("userView").child(Prevelant.onlineUsers.userId).removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                        Toast.makeText(Information.this, "Order Placed.", Toast.LENGTH_SHORT).show();
//                                      String stateUser = "positive";
//
//                                        Userstate userstate = new Userstate(stateUser);
//
//                                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//                                        databaseReference1.child("UserState").updateChildren((Map<String, Object>) userstate);

                                        String stateUser2 = "positive";

                                        Userstate userstate = new Userstate(stateUser2);

                                        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
                                        databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);

                                        Intent intent = new Intent(Information.this,Home.class);
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
//                                        finish();
                                    }
                                }
                            });
                }
            }
        });



    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Information.this,Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}