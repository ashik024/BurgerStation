package com.example.burgerstation2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Cartiteamlist> arrayList =new ArrayList<>();

    private Context mContext;
    public  int overallprice,productPrice,price,quantity;

    String overalltotalprice;
    String resname;


    DatabaseReference firebaseDatabase;
    DatabaseReference firebaseDatabase2;




    public CartAdapter(Context context, ArrayList<Cartiteamlist> cartiteamlist) {
        arrayList = cartiteamlist;
        mContext = context;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.infocart, parent, false);

        holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        if(holder instanceof ViewHolder){
            ((ViewHolder)holder).fN.setText(arrayList.get(position).getProductName());
            ((ViewHolder)holder).fA.setText("Quantity- "+ arrayList.get(position).getProductQuantity());
            ((ViewHolder)holder).fP.setText(arrayList.get(position).getProductPrice()+"BDT");



             price = Integer.parseInt(arrayList.get(position).getProductPrice());
             quantity = Integer.parseInt(arrayList.get(position).getProductQuantity());
            resname =arrayList.get(position).getResturantName();
            productPrice= price*quantity;



            ((ViewHolder)holder).tfP.setText("Total= "+productPrice+"BDT");

            overallprice = overallprice+ productPrice;
         overalltotalprice= Integer.toString(overallprice);

            final Intent intent = new Intent("custom-message");

            intent.putExtra("quantity",overalltotalprice);
            intent.putExtra("nameoftheres",resname);

            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);


            ((ViewHolder)holder).del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FirebaseDatabase.getInstance().getReference().child("CartList")
                            .child("userView").child(Prevelant.onlineUsers.userId)
                            .child(arrayList.get(position).getProductId())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){
                                Toast.makeText(mContext, "Item Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(mContext,Cart.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                mContext.startActivity(intent1);
                            }
                        }
                    });

                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView fN;
        public TextView fP;
        public TextView fA;
        public TextView tfP;
        public ImageView del;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fN= itemView.findViewById(R.id.foodnamecart);
            fP= itemView.findViewById(R.id.foodpricecart);
            fA= itemView.findViewById(R.id.foodamountcart);
            tfP= itemView.findViewById(R.id.foodtotalpricecart);
            del= itemView.findViewById(R.id.del);







        }
    }
}
