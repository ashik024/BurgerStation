package com.example.burgerstation2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import static com.example.burgerstation2.MenuResuturants.resturantid;

public class Cart extends AppCompatActivity {

    TextView total;
    Button checkout;
    Button recivedorder;
    TextView textView;
    LottieAnimationView imageView;

    CartAdapter cartAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Cartiteamlist> arrayList =new ArrayList<>();


    DatabaseReference firebaseDatabase;

    String Price;
    String nameRes;
    String resturantname2;
    public   String sate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);




        textView= findViewById(R.id.textview);
        total= findViewById(R.id.totalAmount);
        checkout= findViewById(R.id.chectout);
        imageView= findViewById(R.id.img);
        recivedorder= findViewById(R.id.OrderRecived);


        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));










        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("CartList")
                .child("userView").child(Prevelant.onlineUsers.userId);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart.this, Information.class);
                intent.putExtra("totalAmount",Price);
                intent.putExtra("namerest",nameRes);

                startActivity(intent);
            }
        });

        recivedorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                                               String state= "negative";
                                               Userstate userstate = new Userstate(state);
                                               DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
                                               databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);
                                                deltenode();
                                               finish();

            }
        });


    }
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
             Price = intent.getStringExtra("quantity");
            nameRes= intent.getStringExtra("nameoftheres");

            total.setText("Total Amount="+Price+ " BDT");

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
//        if (nameRes!= null){
            checkstate();

//        }
//        else if (nameRes==null && sate=="Not Confirmed")
//        {
//            checkstate();
//            Log.d("jjjj",sate);
//
//        }
//
//        else{
//
//            Toast.makeText(this, "No order is placed.", Toast.LENGTH_SHORT).show();
//        }





        if (cartAdapter==null){
            cartAdapter= new CartAdapter(this,arrayList);
        }
        recyclerView= (RecyclerView) findViewById(R.id.recycart);
        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        firebaseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot post: dataSnapshot.getChildren()){
                    Cartiteamlist cartiteamlist = post.getValue(Cartiteamlist.class);
                    arrayList.add(cartiteamlist);
                }
                cartAdapter= new CartAdapter(Cart.this,arrayList);
                recyclerView.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void checkstate() {
        final FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
        CollectionReference collectionReference= firebaseFirestore.collection("resturant");

       collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
           @Override
           public void onSuccess(DocumentSnapshot documentSnapshot) {

               ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);

              resturantname2 = resturantInformation.getName();
               Log.i("ingo",resturantname2);

               DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(resturantname2).child(Prevelant.onlineUsers.userId);


               databaseReference.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                       if (dataSnapshot.exists()){

                            sate = dataSnapshot.child("State").getValue().toString();

                           if (sate.equals("Not Confirmed")){

                               textView.setText("Your order is sent to the Restaurant.Soon It will be Confirmed.");
                               recyclerView.setVisibility(View.GONE);
                               checkout.setVisibility(View.GONE);
                               total.setVisibility(View.GONE);
                               imageView.setVisibility(View.VISIBLE);
                           } else if (sate.equals("Confirmed")){

                               textView.setText("Your order is Shipped.Soon you will receive it");
                               recyclerView.setVisibility(View.GONE);
                               checkout.setVisibility(View.GONE);
                               total.setVisibility(View.GONE);
                               imageView.setVisibility(View.VISIBLE);
                               recivedorder.setVisibility(View.VISIBLE);
                               DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView");
                               databaseReference4.child(Prevelant.onlineUsers.userId).removeValue();
                               databaseReference4.child(Prevelant.onlineUsers.userId).keepSynced(false);





//                               recivedorder.setOnClickListener(new View.OnClickListener() {
//                                   @Override
//                                   public void onClick(View v) {

//                                       String state= "negative";
//                                       Userstate userstate = new Userstate(state);
//                                       DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//                                       databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);
//                                       finish();

//                                       DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Orders").child(resturantname2).child(Prevelant.onlineUsers.userId);
//                                       databaseReference3.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                           @Override
//                                           public void onComplete(@NonNull Task<Void> task) {
//
//                                               String state= "negative";
//                                               Userstate userstate = new Userstate(state);
//                                               DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//                                               databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);
//                                               finish();
//
//                                           }
//                                       });



//                                       Intent intent = new Intent( Cart.this,Home.class);
//                                       startActivity(intent);
//                                       DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView");
//                                       databaseReference4.child(Prevelant.onlineUsers.userId).setValue(null);



                                   

//                                       DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView");
//                                       databaseReference4.child(Prevelant.onlineUsers.userId).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                           @Override
//                                           public void onComplete(@NonNull Task<Void> task) {
//
//                                               if (task.isSuccessful()){
//
//                                                   DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Orders").child(resturantname2).child(Prevelant.onlineUsers.userId);
//                                                   databaseReference3.setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                       @Override
//                                                       public void onComplete(@NonNull Task<Void> task) {
//
//                                                           if (task.isSuccessful()){
//
//                                                               String state= "negative";
//                                                               Userstate userstate = new Userstate(state);
//                                                               DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//                                                               databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                                   @Override
//                                                                   public void onComplete(@NonNull Task<Void> task) {
//                                                                       if (task.isSuccessful()){
//                                                                           Intent intent = new Intent(Cart.this,Home.class);
//                                                                           startActivity(intent);
//                                                                       }
//
//                                                                   }
//                                                               });
//                                                           }
//
//                                                       }
//                                                   });
//
//                                               }
//
//                                           }
//                                       });
//
//                                       FirebaseDatabase.getInstance().getReference().child("CartList")
//                                               .child("AdminView").child(Prevelant.onlineUsers.userId).setValue(null)
//                                               .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                                   @Override
//                                                   public void onComplete(@NonNull Task<Void> task) {
//
//                                                       String state= "negative";
//                                                       Userstate userstate = new Userstate(state);
//                                                       DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//                                                       databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);
//
//                                                       Intent intent = new Intent(Cart.this,Home.class);
//                                                       startActivity(intent);
//
//                                                   }
//                                               });
//
//                                   }
//                               });

                           }

                       }
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError databaseError) {

                   }
               });





            }
        });


  }

    public void onBackPressed() {

        Intent intent = new Intent(Cart.this,Home.class);

        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

       arrayList.clear();
    }

    private void deltenode() {
        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView").child(Prevelant.onlineUsers.userId);


            databaseReference4.removeValue();
        }




}