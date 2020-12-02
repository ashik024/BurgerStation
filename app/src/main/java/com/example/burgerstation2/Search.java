package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class Search extends AppCompatActivity {

    Button search;
    EditText editText;
    RecyclerView recyclerView;
    String serchtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        search= findViewById(R.id.search);
        editText= findViewById(R.id.edit22);
        recyclerView= findViewById(R.id.searchItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(Search.this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                serchtext= editText.getText().toString();
                onStart();

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

        CollectionReference collectionReference= firebaseFirestore.collection("resturant");

        FirestoreRecyclerOptions<ResturantInformation> options =
                new FirestoreRecyclerOptions.Builder<ResturantInformation>().
                        setQuery(collectionReference.orderBy("name").startAt(serchtext),ResturantInformation.class).build();

        FirestoreRecyclerAdapter<ResturantInformation,ResViewHolder> adapter =
                new FirestoreRecyclerAdapter<ResturantInformation, ResViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ResViewHolder holder, int position, @NonNull final ResturantInformation model)
                    {

                        holder.nameR.setText(model.getName());
                        holder.typeR.setText(model.getType());
                        Glide.with(getApplicationContext()).load(model.getImgUri()).into(holder.cover);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(),MenuResuturants.class);
                                intent.putExtra("resid",model.getName());
                                startActivity(intent);
                            }
                        });


                    }

                    @NonNull
                    @Override
                    public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(
                                R.layout.resturant2, parent, false);

                        ResViewHolder holder = new ResViewHolder(view);

                        return holder;
                    }
                };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{

        public TextView nameR;
        public TextView typeR;
        public ImageView cover;

        public ResViewHolder(@NonNull View itemView) {
            super(itemView);

            nameR= itemView.findViewById(R.id.ResturamtName2);
            typeR= itemView.findViewById(R.id.dilevryType2);
            cover= itemView.findViewById(R.id.coverImage2);
        }
    }
    }

