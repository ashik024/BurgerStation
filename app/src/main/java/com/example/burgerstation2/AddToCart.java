package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddToCart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView textView1;
    TextView textView2;

    String foodtname;
    String foodprice;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Spinner spinner;
    EditText editText;
    Button addcart;
    String foodID;
    String ResName;

    String saveCurrentDate;
    String saveCurrentTime;
    String foodidnum;
    String spice;
    String quantity;
    String advice;
    ValueEventListener valueEventListener;




    String State;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        textView1= findViewById(R.id.namef);
        textView2= findViewById(R.id.pricef);
        spinner= findViewById(R.id.spinnerf);
        radioGroup= findViewById(R.id.radiogroup);
        editText= findViewById(R.id.advice);
        addcart= findViewById(R.id.addcart);



        ArrayAdapter<CharSequence> arrayAdapter= ArrayAdapter.createFromResource(this,R.array.numberitem,android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);

        foodtname= getIntent().getStringExtra("foodname");
        foodprice= getIntent().getStringExtra("foodprice");
        foodID= getIntent().getStringExtra("foodid");
        ResName=getIntent().getStringExtra("Nres");
        textView1.setText(foodtname);
        textView2.setText(foodprice+"BDT");
        advice=editText.getText().toString();
        State="notOrdered";

        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                final DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("UserState").child(Prevelant.onlineUsers.userId);

                databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Userstate userstate= dataSnapshot.getValue(Userstate.class);
                        if (userstate.getUserState().equals("positive")){

                            Toast.makeText(AddToCart.this, "You Already Placed An Oder. Wait Till It Gets Delivered.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(AddToCart.this,Home.class);
                            startActivity(intent);
                        }else if(userstate.getUserState().equals("negative") ) {



                            cartactivity();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                    if (State.equals("orderNotConfirmed")||State.equals("orderConfirmed")){
//
//                        Toast.makeText(AddToCart.this, "You Already Placed An Oder. Wait Till It Gets Delivered. ", Toast.LENGTH_SHORT).show();
//
//                    }if (State.equals("notOrdered"))
//                {
//
//
//                        cartactivity();
//                    }

            }
        });

    }



    @Override
    protected void onStart() {
        super.onStart();

//        deltenode();
//        deltenode2();
            checkstate();
    }

    private void cartactivity() {

        int radioid= radioGroup.getCheckedRadioButtonId();

        if (radioid<=0){

            Toast.makeText(this, "Choose Your SpiceLevel", Toast.LENGTH_SHORT).show();

        }else {
            radioButton = findViewById(radioid);
            spice = (String) radioButton.getText();




            final DatabaseReference cartdata = FirebaseDatabase.getInstance().getReference().child("CartList");






            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
            saveCurrentTime = currentTime.format(calendar.getTime());

            foodidnum = saveCurrentDate + saveCurrentTime;


            final HashMap<String, Object> cartmap = new HashMap<>();
            cartmap.put("ProductId", foodidnum);
            cartmap.put("ResturantName", ResName);
            cartmap.put("ProductName", foodtname);
            cartmap.put("OrderDate", saveCurrentDate);
            cartmap.put("OrderTime", saveCurrentTime);
            cartmap.put("ProductPrice", foodprice);
            cartmap.put("SpiceLevel", spice);
            cartmap.put("ProductQuantity", quantity);
            cartmap.put("Discount", advice);
            cartmap.put("Advice", "");



            cartdata.child("userView").child(Prevelant.onlineUsers.userId).child(foodidnum)
                    .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {


                        cartdata.child("AdminView").child(Prevelant.onlineUsers.userId).child(foodidnum)
                                .updateChildren(cartmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(AddToCart.this, "Added To the Cart", Toast.LENGTH_SHORT).show();
                                finish();


                            }
                        });

                    }
                }
            });
        }


    }

    public void Check(View view){
        int radioid= radioGroup.getCheckedRadioButtonId();


            radioButton = findViewById(radioid);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        quantity= parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void checkstate() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevelant.onlineUsers.userId).child(ResName);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String sate = dataSnapshot.child("State").getValue().toString();

                    if (sate.equals("Not Confirmed")){

                        State= "orderNotConfirmed";

                    } else if (sate.equals("Confirmed")){

                        State= "orderConfirmed";

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void deltenode() {
        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView").child(Prevelant.onlineUsers.userId);


        databaseReference4.removeValue();
    }
    private void deltenode2() {
        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("userView").child(Prevelant.onlineUsers.userId);


        databaseReference4.removeValue();
    }


}