package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class CheckOrders extends AppCompatActivity {

    OrderAdapter orderAdapter;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<OrderList> arrayList =new ArrayList<>();
    DatabaseReference firebaseDatabase;
    String restauname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_orders);

        recyclerView= (RecyclerView) findViewById(R.id.recyadmin);
//     recyclerView.setHasFixedSize(true);

        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        restauname= getIntent().getStringExtra("resNN");

        firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Orders").child(restauname);
    }

    @Override
    protected void onStart() {
        super.onStart();


//        if (orderAdapter==null){
//            orderAdapter= new OrderAdapter(this,arrayList);
//        }
//        recyclerView= (RecyclerView) findViewById(R.id.recyadmin);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager= new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);

//        firebaseDatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot post: dataSnapshot.getChildren()){
//                    OrderList orderList = post.getValue(OrderList.class);
//                    arrayList.add(orderList);
//                }
//              orderAdapter = new OrderAdapter(CheckOrders.this,arrayList);
//                recyclerView.setAdapter(orderAdapter);
//                orderAdapter.notifyDataSetChanged();
//
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });


        FirebaseRecyclerOptions<OrderList> options=
                new FirebaseRecyclerOptions.Builder<OrderList>()
                .setQuery(firebaseDatabase,OrderList.class)
                .build();

        FirebaseRecyclerAdapter<OrderList,OrderListViewHolder>adapter =
                new FirebaseRecyclerAdapter<OrderList, OrderListViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final OrderListViewHolder holder, final int position, @NonNull OrderList model)
                    {

                        holder.nameO.setText("Name: "+model.getName());
                        holder.dateO.setText("Date: "+model.getDate());
                        holder.phoneO.setText("Phone: "+model.getPhone());
                        holder.addressO.setText("Address: "+model.getAddress());
                        holder.priceO.setText("Total Price: "+model.getTotalPrice()+"BDT");

                        final String userordered= model.getId();


                        holder.odetails.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String UID= getRef(position).getKey();
                                Intent intent = new Intent(CheckOrders.this,OrderDetailsAdmin.class);
                                intent.putExtra("userid",UID);
                                intent.putExtra("resid",restauname);
                                startActivity(intent);
                            }
                        });
                        holder.ship.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                        .child("Orders").child(restauname).child(userordered);

                                databaseReference.child("State").setValue("Confirmed");

                                   holder.odetails.setVisibility(View.INVISIBLE);
                                holder.ship.setVisibility(View.INVISIBLE);
                                holder.state.setVisibility(View.VISIBLE);

                                DatabaseReference databaseReference3 = FirebaseDatabase.getInstance().getReference().child("Orders").child(restauname).child(userordered);
                                       databaseReference3.setValue(null);




                            }
                        });



                    }

                    @NonNull
                    @Override
                    public OrderListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.orderedorder, parent, false);
                        return new OrderListViewHolder(view);
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public static class OrderListViewHolder extends RecyclerView.ViewHolder{


        public TextView nameO;
        public TextView dateO;
        public TextView phoneO;
        public TextView addressO;
        public TextView priceO;

        public Button odetails;
        public Button ship;
        public TextView state;

        public OrderListViewHolder(@NonNull View itemView) {
            super(itemView);

            nameO= itemView.findViewById(R.id.ordername);
            dateO= itemView.findViewById(R.id.orderdate);
            phoneO= itemView.findViewById(R.id.orderphone);
            addressO= itemView.findViewById(R.id.address);
            priceO= itemView.findViewById(R.id.orderprice);
            odetails=itemView.findViewById(R.id.orderdetails);
            ship=itemView.findViewById(R.id.shiporder);
            state=itemView.findViewById(R.id.state);

        }
    }
}