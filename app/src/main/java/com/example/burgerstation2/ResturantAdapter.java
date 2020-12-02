package com.example.burgerstation2;//package com.example.burgerstation;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.squareup.picasso.Picasso;
//
//import java.util.ArrayList;
//
//public class ResturantAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    private static final String TAG = "NoteRecyclerViewAdapter";
//
//    private  ArrayList<ResturantInformation> arrayList =new ArrayList<>();
//
//    private Context mContext;
//    private int mSelectedNoteIndex;
//
//    public ResturantAdapter(Context context, ArrayList<ResturantInformation> resturantInformations) {
//        arrayList = resturantInformations;
//        mContext = context;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder;
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.resturant, parent, false);
//
//        holder = new ViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
//
//        if(holder instanceof ViewHolder){
//            ((ViewHolder)holder).nameR.setText(arrayList.get(position).getName());
//            ((ViewHolder)holder).typeR.setText(arrayList.get(position).getType());
//            Picasso.get().load(arrayList.get(position).getImgUri()).into(((ViewHolder) holder).cover);
//
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(mContext.getApplicationContext(),MenuResuturants.class);
//                    intent.putExtra("resid",arrayList.get(position).getName());
//                  mContext.startActivity(intent);
//
//
//
//                }
//            });
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return arrayList.size();
//    }
//
//
//    @Override
//    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
//        super.onAttachedToRecyclerView(recyclerView);
//
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//
//
//        public TextView nameR;
//        public TextView typeR;
//        public ImageView cover;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            nameR= itemView.findViewById(R.id.ResturamtName);
//            typeR= itemView.findViewById(R.id.dilevryType);
//            cover= itemView.findViewById(R.id.coverImage);
//        }
//
//
//    }
//}
