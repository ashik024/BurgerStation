package com.example.burgerstation2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class OrderDetailsAdmin extends AppCompatActivity {

    String id;
    String nameRes;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    DatabaseReference firebaseDatabase;
//    OrderDetailsAdapter orderDetailsAdapter;
    ArrayList<Cartiteamlist> arrayList =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details_admin);

       id= getIntent().getStringExtra("userid");
        nameRes= getIntent().getStringExtra("resid");
        Log.i("infooooo",id);

        recyclerView= (RecyclerView) findViewById(R.id.recyadminproduts);


        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("CartList")
                .child("AdminView").child(id);
    }

    protected void onStart() {
        super.onStart();


//        if (orderDetailsAdapter==null){
//            orderDetailsAdapter= new OrderDetailsAdapter(this,arrayList);
//        }
//        recyclerView= (RecyclerView) findViewById(R.id.recyadminproduts);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

//        firebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot post: dataSnapshot.getChildren()){
//                  Cartiteamlist cartiteamlist = post.getValue(Cartiteamlist.class);
//                  arrayList.add(cartiteamlist);
//                }
//                orderDetailsAdapter = new OrderDetailsAdapter(OrderDetailsAdmin.this,arrayList);
//                recyclerView.setAdapter(orderDetailsAdapter);
//                orderDetailsAdapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        FirebaseRecyclerOptions<Cartiteamlist> options=
                new FirebaseRecyclerOptions.Builder<Cartiteamlist>()
                        .setQuery(firebaseDatabase,Cartiteamlist.class)
                        .build();

        FirebaseRecyclerAdapter<Cartiteamlist,CartiteamlistViewHolder>adapter=
                new FirebaseRecyclerAdapter<Cartiteamlist, CartiteamlistViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CartiteamlistViewHolder holder, int position, @NonNull Cartiteamlist model) {

                        holder.nameP.setText("Name: "+model.getProductName());
                        holder.dateP.setText("Date: "+model.getOrderDate());
                        holder.timeP.setText("Time: "+model.getOrderTime());
                        holder.quantityP.setText("Quantity: "+model.getProductQuantity());
                        holder.priceP.setText("Price: "+model.getProductPrice());
                        holder.spiceP.setText("Spice: "+model.getSpiceLevel());
                        holder.adviceP.setText("Discount: "+model.getDiscount());

                    }

                    @NonNull
                    @Override
                    public CartiteamlistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.orderdetailsproducts, parent, false);

                        return new CartiteamlistViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.notifyDataSetChanged();



    }

    public static class CartiteamlistViewHolder extends RecyclerView.ViewHolder{

        public TextView nameP;
        public TextView dateP;
        public TextView timeP;
        public TextView quantityP;
        public TextView priceP;
        public TextView spiceP;
        public TextView adviceP;
        public CartiteamlistViewHolder(@NonNull View itemView) {
            super(itemView);

            nameP=itemView.findViewById(R.id.Name);
            dateP=itemView.findViewById(R.id.Date);
            timeP=itemView.findViewById(R.id.Time);
            quantityP=itemView.findViewById(R.id.Quantity);
            priceP=itemView.findViewById(R.id.Price);
            spiceP=itemView.findViewById(R.id.Spice);
            adviceP=itemView.findViewById(R.id.Advice);

        }
    }


}