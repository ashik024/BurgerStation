package com.example.burgerstation2;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class Home extends AppCompatActivity {

    NavigationView nav;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    DatabaseReference resReference;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ImageView search;

    FirebaseFirestore firebaseFirestore;
    CollectionReference collectionReference;

    String data2;
    Query query;
    ArrayList<ResturantInformation> arrayList =new ArrayList<>();
    DocumentSnapshot documentSnapshot;
    ImageView floatingActionButton;

    String type="";
    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;
    ScrollView scrollView;

    ImageSlider imageSlider;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        final SharedPreferences.Editor editor = wmbPreference.edit();


        if (isFirstRun){
            // Code to run once
            String state= "negative";


            Userstate userstate = new Userstate(state);
            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
            databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);

            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }


        imageSlider = findViewById(R.id.imgslide);
        scrollView= findViewById(R.id.scl);





        List<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("https://scontent.fdac20-1.fna.fbcdn.net/v/t1.0-9/86728805_2751665611593657_2108446870635282432_n.png?_nc_cat=100&ccb=2&_nc_sid=e3f864&_nc_ohc=5kr_JD7_pjEAX_QaJTl&_nc_ht=scontent.fdac20-1.fna&oh=9330dcf609f7f19b28430b14dae286aa&oe=5FE23295"));
        slideModels.add(new SlideModel("https://scontent.fdac20-1.fna.fbcdn.net/v/t1.0-9/126182162_3564694910292063_5452131638638198919_o.jpg?_nc_cat=105&ccb=2&_nc_sid=e3f864&_nc_ohc=qEjq9SIPUNcAX9f8aSq&_nc_ht=scontent.fdac20-1.fna&oh=53624c01076a85bb16f7cb92fba0c820&oe=5FE4C725"));
        slideModels.add(new SlideModel("https://scontent.fdac20-1.fna.fbcdn.net/v/t31.0-8/16716193_1192215677552829_6992881249069554993_o.jpg?_nc_cat=110&ccb=2&_nc_sid=19026a&_nc_ohc=XlPrdsuAPqEAX8AgyFd&_nc_ht=scontent.fdac20-1.fna&oh=6178c1546c731120b1af19ea58d9c92d&oe=5FE4DED1"));
        slideModels.add(new SlideModel("https://royalty-bd.s3.ap-southeast-1.amazonaws.com/dynamic-images/partner_gallery_image/6_1580107635.jpg"));
        imageSlider.setImageList(slideModels,true);

        cardView1= findViewById(R.id.popular);
        cardView2= findViewById(R.id.toprated);
        cardView3= findViewById(R.id.newresturant);
        cardView4= findViewById(R.id.sale);



        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Search.class);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Search.class);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Search.class);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Search.class);
                startActivity(intent);
            }
        });




        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Paper.init(this);

        nav=(NavigationView)findViewById(R.id.navmenu);
        View headerView= nav.getHeaderView(0);
        TextView userName= headerView.findViewById(R.id.user_profile_name);
        CircleImageView userImage= headerView.findViewById(R.id.user_profile_image);

//        String uname= getIntent().getStringExtra("uName");
        userName.setText(Prevelant.onlineUsers.getName());

//        if (type.isEmpty()){
//
//            userName.setText(Prevelant.onlineUsers.getName());
//        }


        search= findViewById(R.id.searchRes);

        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            type= getIntent().getExtras().get("resN").toString();
        }


        recyclerView= (RecyclerView) findViewById(R.id.recyHome);

        layoutManager= new LinearLayoutManager(Home.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Search.class);
                startActivity(intent);
            }
        });

        floatingActionButton= findViewById(R.id.floatingbtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Home.this,Cart.class);
                startActivity(intent);
            }
        });







        drawerLayout=(DrawerLayout)findViewById(R.id.drawer);

        toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
            {
                switch (menuItem.getItemId())
                {
                    case R.id.menu_home :
                        Toast.makeText(getApplicationContext(),"Cart Panel is Open",Toast.LENGTH_LONG).show();
                        Intent intent2 = new Intent(Home.this,Cart.class);
                        startActivity(intent2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_call :
                        Toast.makeText(getApplicationContext(),"Order Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.menu_setting :
                        Toast.makeText(getApplicationContext(),"Setting Panel is Open",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.logout :
                        Toast.makeText(getApplicationContext(),"Logged out",Toast.LENGTH_LONG).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        Paper.book().destroy();
                        editor.clear().commit();

                        Intent intent = new Intent(Home.this,MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();


                        break;
                }

                return true;
            }
        });



//        startrecycle();

    }

//    private void startrecycle() {
//
//        getdata();
//
//
//
//
//    if (adapter==null){
//        adapter= new ResturantAdapter(this,arrayList);
//    }
//        recyclerView= (RecyclerView) findViewById(R.id.recyHome);
//        recyclerView.setHasFixedSize(true);
//
//        layoutManager= new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapter);
//
//
//
//
//
//    }
//
//    private void getdata() {
//        firebaseFirestore= FirebaseFirestore.getInstance();
//        collectionReference = firebaseFirestore.collection("resturant");
//
//        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
//
//                    ResturantInformation resturantInformation = documentSnapshot.toObject(ResturantInformation.class);
//
//                    arrayList.add(resturantInformation);
//                }
//                adapter.notifyDataSetChanged();
//            }
//        });
//    }


    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference databaseReference4 = FirebaseDatabase.getInstance().getReference().child("CartList").child("AdminView");



//        String state= "negative";
//        Userstate userstate = new Userstate(state);
//        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference();
//        databaseReference1.child("UserState").child(Prevelant.onlineUsers.userId).setValue(userstate);


        if (!type.isEmpty()){

            search.setVisibility(View.GONE);
            floatingActionButton.setVisibility(View.GONE);
            FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

            CollectionReference collectionReference= firebaseFirestore.collection("resturant");
//            FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
//                    .setPersistenceEnabled(true)
//                    .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
//                    .build();
//            firebaseFirestore.setFirestoreSettings(settings);

            FirestoreRecyclerOptions<ResturantInformation> options =
                    new FirestoreRecyclerOptions.Builder<ResturantInformation>().
                            setQuery(collectionReference.orderBy("name").startAt(type),ResturantInformation.class).build();

            FirestoreRecyclerAdapter<ResturantInformation, ResViewHolder> adapter =
                    new FirestoreRecyclerAdapter<ResturantInformation, ResViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ResViewHolder holder, int position, @NonNull final ResturantInformation model) {

                            holder.nameR.setText(model.getName());
                            holder.typeR.setText(model.getType());
//                            Picasso.get().load(model.getImgUri()).into(holder.cover);
                           Glide.with(getApplicationContext()).load(model.getImgUri()).into(holder.cover);


                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Intent intent = new Intent(getApplicationContext(), Editmenu.class);
                                    intent.putExtra("resid", model.getName());
                                    startActivity(intent);

                                }
                            });

                        }

                        @NonNull
                        @Override
                        public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.resturant, parent, false);
                            ResViewHolder holder = new ResViewHolder(view);

                            return holder;
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();



        }else {

            FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();

            CollectionReference collectionReference= firebaseFirestore.collection("resturant");



            FirestoreRecyclerOptions<ResturantInformation> options =
                    new FirestoreRecyclerOptions.Builder<ResturantInformation>().
                            setQuery(collectionReference,ResturantInformation.class).build();

            FirestoreRecyclerAdapter<ResturantInformation, ResViewHolder> adapter =
                    new FirestoreRecyclerAdapter<ResturantInformation, ResViewHolder>(options) {
                        @Override
                        protected void onBindViewHolder(@NonNull ResViewHolder holder, int position, @NonNull final ResturantInformation model) {

                            holder.nameR.setText(model.getName());
                            holder.typeR.setText(model.getType());
//                            Picasso.get().load(model.getImgUri()).into(holder.cover);
                            Glide.with(getApplicationContext()).load(model.getImgUri()).into(holder.cover);

                            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {


                                    Intent intent = new Intent(getApplicationContext(), MenuResuturants.class);
                                    intent.putExtra("resid", model.getName());
                                    startActivity(intent);







                                }
                            });





                        }

                        @NonNull
                        @Override
                        public ResViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(
                                    R.layout.resturant, parent, false);
                            ResViewHolder holder = new ResViewHolder(view);

                            return holder;
                        }
                    };

            recyclerView.setAdapter(adapter);
            adapter.startListening();
        }

    }

    public static class ResViewHolder extends RecyclerView.ViewHolder{

        public TextView nameR;
        public TextView typeR;
        public ImageView cover;

        public ResViewHolder(@NonNull View itemView) {
            super(itemView);

            nameR= itemView.findViewById(R.id.ResturamtName);
            typeR= itemView.findViewById(R.id.dilevryType);
            cover= itemView.findViewById(R.id.coverImage);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        arrayList.clear();
    }


}