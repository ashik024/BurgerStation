package com.example.burgerstation2;//package com.example.burgerstation;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//
//public class OrderDetailsAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private ArrayList<Cartiteamlist> arrayList =new ArrayList<>();
//
//    private Context mContext;
//
//    public OrderDetailsAdapter(Context context, ArrayList<Cartiteamlist> cartiteamlists) {
//        arrayList= cartiteamlists;
//        mContext = context;
//    }
//
//
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder;
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.orderdetailsproducts, parent, false);
//
//        holder = new ViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//
//        if(holder instanceof OrderAdapter.ViewHolder) {
//            ((ViewHolder)holder).nameP.setText("Name: "+arrayList.get(position).getProductName());
//            ((ViewHolder)holder).dateP.setText("Date: "+arrayList.get(position).getOrderDate());
//            ((ViewHolder)holder).timeP.setText("Time: "+arrayList.get(position).getOrderTime());
//            ((ViewHolder)holder).quantityP.setText("Quantity: "+arrayList.get(position).getProductQuantity());
//            ((ViewHolder)holder).priceP.setText("Price: "+arrayList.get(position).getProductPrice());
//            ((ViewHolder)holder).spiceP.setText("Name: "+arrayList.get(position).getSpiceLevel());
//            ((ViewHolder)holder).adviceP.setText("Name: "+arrayList.get(position).getSpiceLevel());
//
//
//
//    }
//
//    }
//getSpiceLevel
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//        public TextView nameP;
//        public TextView dateP;
//        public TextView timeP;
//        public TextView quantityP;
//        public TextView priceP;
//        public TextView spiceP;
//        public TextView adviceP;
//
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            nameP=itemView.findViewById(R.id.Name);
//            dateP=itemView.findViewById(R.id.Date);
//            timeP=itemView.findViewById(R.id.Time);
//            quantityP=itemView.findViewById(R.id.Quantity);
//            priceP=itemView.findViewById(R.id.Price);
//            spiceP=itemView.findViewById(R.id.Spice);
//            adviceP=itemView.findViewById(R.id.Advice);
//
//
//
//        }
//    }
//}
