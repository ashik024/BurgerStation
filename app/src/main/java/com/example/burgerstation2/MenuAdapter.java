package com.example.burgerstation2;//package com.example.burgerstation;
//
//import android.content.Context;
//import android.content.Intent;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.cardview.widget.CardView;
//import androidx.fragment.app.FragmentManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
//import com.example.burgerstation.Prevelant.Prevelant;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.FirebaseFirestore;
//import com.squareup.picasso.Picasso;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//
//import static android.content.Intent.getIntent;
//import static com.example.burgerstation.MenuResuturants.resturantid;
//
//
//public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
//
//    String productkey;
//    String saveCurrentDate;
//    String resturantname;
//
//
//
//    private static final String TAG = "NoteRecyclerViewAdapter";
//
//    private ArrayList<MenuItemList> arrayList =new ArrayList<>();
//
//    private Context mContext;
//    private int mSelectedNoteIndex;
//
//    public MenuAdapter(Context context, ArrayList<MenuItemList> menuItemLists) {
//        arrayList = menuItemLists;
//        mContext = context;
//    }
//
//    @NonNull
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder holder;
//        View view = LayoutInflater.from(parent.getContext()).inflate(
//                R.layout.menufood, parent, false);
//
//        holder = new ViewHolder(view);
//
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
//
//        if(holder instanceof ViewHolder){
//            ((ViewHolder)holder).foodN.setText(arrayList.get(position).getItemName());
//            ((ViewHolder)holder).foodD.setText(arrayList.get(position).getItemDes());
//            ((ViewHolder)holder).foodP.setText(arrayList.get(position).getItemPrice()+"BDT");
//
//            FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
//            CollectionReference collectionReference= firebaseFirestore.collection("resturant");
//
//
//            collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//
//                    ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);
//
//                    resturantname= resturantInformation.getName();
//
//
//
//
//
//
//
//                }
//            });
//
//            ((ViewHolder)holder).cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//
//                    FirebaseFirestore firebaseFirestore =FirebaseFirestore.getInstance();
//                    CollectionReference collectionReference= firebaseFirestore.collection("resturant");
//                    collectionReference.document(resturantid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                        @Override
//                        public void onSuccess(DocumentSnapshot documentSnapshot) {
//
//
//                            ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);
//                            Orderstate orderstate = documentSnapshot.toObject(Orderstate.class);
//
//
//                            String resturantname2 = resturantInformation.getName();
//
//                            final String order= orderstate.getOrderstate();
//                            Log.i("ingo",resturantname2);
//
//                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(resturantname2).child(Prevelant.onlineUsers.userId);
//
//
//                            databaseReference.addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                    if (dataSnapshot.exists()){
//
////                                        String sate = dataSnapshot.child("State").getValue().toString();
//
////                                        if (sate.equals(("Not Confirmed"))||(sate.equals("Confirmed")))
////                                        {
////
////
//                                            Toast.makeText(mContext, "Your Already Have An Order Processing. ", Toast.LENGTH_SHORT).show();
//
//
//                                        } else  {
//
//                                            Intent intent = new Intent(mContext.getApplicationContext(),AddToCart.class);
//                                            intent.putExtra("foodname",arrayList.get(position).getItemName());
//                                            intent.putExtra("foodprice",arrayList.get(position).getItemPrice());
//                                            intent.putExtra("foodid",arrayList.get(position).getMenuID());
//                                            intent.putExtra("Nres",resturantname);
//
//                                            mContext.startActivity(intent);
//
//                                        }
//
//                                    }
////                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                                }
//                            });
//
//
//
//
//
//                        }
//                    });
//
//
//
//
//
//
//
//                }
//            });
//
//
//
//
//        }
//    }
//
//
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
//        public TextView foodN;
//        public TextView foodD;
//        public TextView foodP;
//        public CardView cardView;
//
//
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//            foodN= itemView.findViewById(R.id.foodname);
//            foodD= itemView.findViewById(R.id.fooddes);
//            foodP= itemView.findViewById(R.id.foodprice);
//            cardView= itemView.findViewById(R.id.card);
//
//        }
//
//
//    }
//    private void checkstate() {
//        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Orders").child(Prevelant.onlineUsers.userId);
//
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()){
//
//                    String sate = dataSnapshot.child("State").getValue().toString();
//
//                    if (sate.equals("Not Confirmed")){
//
//
//                    } else if (sate.equals("Confirmed")){
//
//
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }
//}
//
