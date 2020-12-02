package com.example.burgerstation2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<OrderList> arrayList =new ArrayList<>();

    private Context mContext;




    public OrderAdapter(Context context, ArrayList<OrderList> orderLists) {
        arrayList= orderLists;
        mContext = context;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.orderedorder, parent, false);

        holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolder) {
            ((ViewHolder)holder).nameO.setText("Name: "+arrayList.get(position).getName());
            ((ViewHolder)holder).dateO.setText("Date: "+arrayList.get(position).getDate());
            ((ViewHolder)holder).phoneO.setText("Phone: "+arrayList.get(position).getPhone());
            ((ViewHolder)holder).addressO.setText("Address: "+arrayList.get(position).getAddress());
            ((ViewHolder)holder).priceO.setText("Total Price: "+arrayList.get(position).getTotalPrice()+"BDT");



        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nameO;
        public TextView dateO;
        public TextView phoneO;
        public TextView addressO;
        public TextView priceO;

        public Button odetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nameO= itemView.findViewById(R.id.ordername);
            dateO= itemView.findViewById(R.id.orderdate);
            phoneO= itemView.findViewById(R.id.orderphone);
            addressO= itemView.findViewById(R.id.address);
            priceO= itemView.findViewById(R.id.orderprice);
            odetails=itemView.findViewById(R.id.orderdetails);


        }
    }
}
