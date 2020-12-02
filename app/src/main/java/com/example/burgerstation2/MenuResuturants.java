package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MenuResuturants extends AppCompatActivity {

    TextView nameRes;
    TextView nameType;
    TextView nameDes;
    ImageView imageRes;
    TextView addres;
    public static String resturantid;
    FloatingActionButton floatingActionButton;

    String resturantname;


//    MenuAdapter menuAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<MenuItemList> arrayList = new ArrayList<>();


    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        resturantid = getIntent().getStringExtra("resid");


        setContentView(R.layout.activity_menu_resuturants);
        nameRes = findViewById(R.id.resname);
        nameType = findViewById(R.id.resdelivary);
        nameDes = findViewById(R.id.resaddress);
        imageRes = findViewById(R.id.imageres);
        addres = findViewById(R.id.resaddres2);
        floatingActionButton = findViewById(R.id.floatingbtn2);




        recyclerView= (RecyclerView) findViewById(R.id.rescyMenu);
//        recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        getresturantdetails(resturantid);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuResuturants.this, Cart.class);
                startActivity(intent);

            }
        });

    }


    private void getresturantdetails(String resturantid) {

        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        CollectionReference collectionReference = firebaseFirestore.collection("resturant");




        collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);

                nameRes.setText(resturantInformation.getName());
                nameType.setText(resturantInformation.getType());
                nameDes.setText(resturantInformation.getPostal());
                addres.setText(resturantInformation.getAddress());
                Glide.with(getApplicationContext()).load(resturantInformation.getImgUri()).into(imageRes);


            }
        });

        recycler();


    }

    private void recycler() {

//        getdata();
//
//
//
//
//        if (menuAdapter==null){
//            menuAdapter= new MenuAdapter(this,arrayList);
//        }
//        recyclerView= (RecyclerView) findViewById(R.id.rescyMenu);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(menuAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("resturant").document(resturantid).collection("menu");

//        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                .setPersistenceEnabled(true)
//                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
//                .build();
//        firebaseFirestore.setFirestoreSettings(settings);


        FirestoreRecyclerOptions<MenuItemList> options =
                new FirestoreRecyclerOptions.Builder<MenuItemList>().
                        setQuery(collectionReference, MenuItemList.class).build();

        FirestoreRecyclerAdapter<MenuItemList, itemviewholder> adapter =
                new FirestoreRecyclerAdapter<MenuItemList, itemviewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull itemviewholder holder, int position, @NonNull final MenuItemList model) {

                        holder.foodN.setText(model.getItemName());
                        holder.foodD.setText(model.getItemDes());
                        holder.foodP.setText(model.getItemPrice()+ " BDT");

                        FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
                        CollectionReference collectionReference= firebaseFirestore.collection("resturant");


                        collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {


                                ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);

                                resturantname= resturantInformation.getName();







                            }
                        });

                         holder.cardView.setOnClickListener(new View.OnClickListener() {
                         @Override
                        public void onClick(View v) {

                             Intent intent = new Intent(getApplicationContext(),AddToCart.class);
                             intent.putExtra("foodname",model.getItemName());
                             intent.putExtra("foodprice",model.getItemPrice());
                             intent.putExtra("foodid",model.getMenuID());
                             intent.putExtra("Nres",resturantname);
                             intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                             startActivity(intent);


//                             FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
//                             CollectionReference collectionReference= firebaseFirestore.collection("resturant");
//                             collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                 @Override
//                                 public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//
//                                     ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);
//                                     Orderstate orderstate = documentSnapshot.toObject(Orderstate.class);
//
//
//                                     String resturantname2 = resturantInformation.getName();
//
//                                     final String order= orderstate.getOrderstate();
//                                     Log.i("ingo",resturantname2);
//
//                                     DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(resturantname2).child(Prevelant.onlineUsers.userId);
//
//
//                                     databaseReference.addValueEventListener(new ValueEventListener() {
//                                         @Override
//                                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                             if (dataSnapshot.exists()){
//
//                                        String sate = dataSnapshot.child("State").getValue().toString();
//
//                                        if (sate.equals(("Not Confirmed"))||(sate.equals("Confirmed")))
//                                        {
//
//
//                                                 Toast.makeText(getApplicationContext(), "Your Already Have An Order Processing. ", Toast.LENGTH_SHORT).show();
//
//
//                                             } else  {
//
//                                                 Intent intent = new Intent(getApplicationContext(),AddToCart.class);
//                                                 intent.putExtra("foodname",model.getItemName());
//                                                 intent.putExtra("foodprice",model.getItemPrice());
//                                                 intent.putExtra("foodid",model.getMenuID());
//                                                 intent.putExtra("Nres",resturantname);
//                                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                                                startActivity(intent);
//
//
//
//                                             }
//
//                                         }
//                                }
//
//                                         @Override
//                                         public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                         }
//                                     });
//
//
//
//
//
//                                 }
//                             });
//
//
//

                         }
                     });


                    }

                    @NonNull
                    @Override
                    public itemviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.menufood, parent, false);

                       itemviewholder holder = new itemviewholder(view);
                       return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    public  class itemviewholder extends RecyclerView.ViewHolder{

        public TextView foodN;
        public TextView foodD;
        public TextView foodP;
        public CardView cardView;


        public itemviewholder(@NonNull View itemView) {
            super(itemView);
            foodN= itemView.findViewById(R.id.foodname);
            foodD= itemView.findViewById(R.id.fooddes);
            foodP= itemView.findViewById(R.id.foodprice);
            cardView= itemView.findViewById(R.id.card);
        }
    }



//    private void getdata() {
//        firebaseFirestore= FirebaseFirestore.getInstance();
//        collectionReference = firebaseFirestore.collection("resturant").document(resturantid).collection("menu");
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
//
//                    MenuItemList menuItemList = documentSnapshot.toObject(MenuItemList.class);
//
//                    arrayList.add(menuItemList);
//                }
//                menuAdapter.notifyDataSetChanged();
//
//
//            }
//        });
//    }

    private void checkstate() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevelant.onlineUsers.userId);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){

                    String sate = dataSnapshot.child("State").getValue().toString();

                    if (sate.equals("Not Confirmed")){


                    } else if (sate.equals("Confirmed")){



                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    }
