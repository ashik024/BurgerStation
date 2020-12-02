package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Editmenu extends AppCompatActivity {

    TextView nameRes;
    EditText nameType;
    EditText nameDes;
    ImageView imageRes;
    EditText addres;
    String resturantid="";
    FloatingActionButton floatingActionButton;

    FirebaseFirestore  firebaseFirestore;
    DocumentReference documentReference;
    CollectionReference collectionReference;

    String nfood;
    String dfood;
    String pfood;
    String Idfood;



    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editmenu);

        resturantid = getIntent().getStringExtra("resid");

        nameRes = findViewById(R.id.resname);
        nameType = findViewById(R.id.resdelivary);
        nameDes = findViewById(R.id.resaddress);
        imageRes = findViewById(R.id.imageres2);
        addres = findViewById(R.id.resaddres2);
        floatingActionButton= findViewById(R.id.floatingbtn22);


        recyclerView= (RecyclerView) findViewById(R.id.rescyMenu2);
        recyclerView.setHasFixedSize(true);
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        editresturantdetails(resturantid);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applychange();
            }
        });
    }

    private void applychange() {

        String nameR = nameRes.getText().toString();
        String typeR = nameType.getText().toString();
        String desR = nameDes.getText().toString();
        String addressR = addres.getText().toString();






        if (nameR.equals("")){
            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();

        }else if(typeR.equals("")){
            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();
        }
        else if(desR.equals(""))
        {
            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();
        }else if(addressR.equals(""))
        {
            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();
        }else{

             firebaseFirestore= FirebaseFirestore.getInstance();
            documentReference = firebaseFirestore.collection("resturant").document(resturantid);

            ResturantInformation resturantInformation = new ResturantInformation();
            resturantInformation.setRestuarantId(documentReference.getId());
            resturantInformation.setName(nameR);
            resturantInformation.setAddress(addressR);
            resturantInformation.setType(typeR);
            resturantInformation.setPostal(desR);


            documentReference.set(resturantInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(Editmenu.this, "Success", Toast.LENGTH_SHORT).show();


//                        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                                Toast.makeText(Editmenu.this, "Success2", Toast.LENGTH_SHORT).show();
//                            }
//                        });





                    }
                }
            });

        }
    }

    private void editresturantdetails(String resturantid) {

        FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
        CollectionReference collectionReference= firebaseFirestore.collection("resturant");


        collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {


                ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);

                String resname = resturantInformation.getName();
                String restype = resturantInformation.getType();
                String resdes = resturantInformation.getPostal();
                String resaddr = resturantInformation.getAddress();
                String resimg = resturantInformation.getImgUri();

                nameRes.setText(resname);
                nameType.setText(restype);
                nameDes.setText(resdes);
                addres.setText(resaddr);
                Picasso.get().load(resimg).into(imageRes);





            }
        });

        getmenu();

    }

    private void getmenu() {


        firebaseFirestore= FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("resturant").document(resturantid).collection("menu");




        FirestoreRecyclerOptions<MenuItemList> options =
                new FirestoreRecyclerOptions.Builder<MenuItemList>().
                        setQuery(collectionReference,MenuItemList.class).build();

        FirestoreRecyclerAdapter<MenuItemList, Menuviewholder> adapter =
                new FirestoreRecyclerAdapter<MenuItemList, Menuviewholder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final Menuviewholder holder, int position, @NonNull final MenuItemList model)
                    {

                        holder.namefood.setText(model.getItemName());
                        holder.desfood.setText(model.getItemDes());
                        holder.pricefood.setText(model.getItemPrice());


                        nfood = holder.namefood.getText().toString();
                        dfood = holder.desfood.getText().toString();
                        pfood =holder.pricefood.getText().toString();




                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(Editmenu.this,Updateinformation.class);
                                intent.putExtra("name",model.getItemName());
                                intent.putExtra("des",model.getItemDes());
                                intent.putExtra("price",model.getItemPrice());
                                intent.putExtra("idmenu",model.getMenuID());
                                intent.putExtra("idofresturant",resturantid);
                                startActivity(intent);
                            }
                        });


//                        holder.update.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                int r= holder.getAdapterPosition();
//
//                                String position= Integer.toString(r);
//
//                                firebaseFirestore= FirebaseFirestore.getInstance();
//                                DocumentReference documentReference2= firebaseFirestore.collection("resturant").document(resturantid)
//                                        .collection("menu").document(model.getMenuID());
//
//                                MenuItemList menuItemList = new MenuItemList();
//
//
//                                documentReference2.delete();
//                                menuItemList.setItemName(nfood);
//                                menuItemList.setItemDes(dfood);
//                                menuItemList.setItemPrice(pfood);
//                                menuItemList.setItemPrice(model.getMenuID());
//
//                                documentReference2.set(menuItemList).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        if (task.isSuccessful()) {
//                                            Toast.makeText(Editmenu.this, "updated", Toast.LENGTH_SHORT).show();
//                                        }
//
//                                    }
//                                });
//
//                            }
//                        });





                    }

                    @NonNull
                    @Override
                    public Menuviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.editmenufood, parent, false);
                        Menuviewholder holder = new Menuviewholder(view);

                        return holder;
                    }
                };


        recyclerView.setAdapter(adapter);
        adapter.startListening();



    }



    public static class Menuviewholder extends RecyclerView.ViewHolder{

        public TextView namefood;
        public TextView desfood;
        public TextView pricefood;


        public Menuviewholder(@NonNull View itemView) {
            super(itemView);

            namefood= itemView.findViewById(R.id.foodname);
            desfood= itemView.findViewById(R.id.fooddes);
            pricefood= itemView.findViewById(R.id.foodprice);

        }
    }


}