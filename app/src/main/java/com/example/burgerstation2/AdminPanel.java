package com.example.burgerstation2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AdminPanel extends AppCompatActivity {




    String productkey;
    String saveCurrentDate;
    String saveCurrentTime;




    TextView Rname;
    TextView Rcity;
    TextView Radress;
    TextView Rpostal;
    TextView type;

    Button next;

    public static String nameRes;
    String city;
    String address;
    String DeliveryT;
    String typeD;
    ImageView cover;
    ProgressDialog  progressDialog;

    public DatabaseReference databaseReference;

    public StorageReference coverstore;
    String dowamlodimgurl;

    public static final int gallary =1;
  public Uri imageUri;

   FirebaseFirestore firebaseFirestore;
   DocumentReference documentReference;
   DocumentReference documentReference2;
   CollectionReference collectionReference;
   CollectionReference collectionReference2;

   TextView headig1;
   TextView heading2;
   Button add;
   Button save;

    EditText burgerN;
    EditText burgerD;
    EditText burgerP;

    RelativeLayout relativeLayout;

    String itemN;
    String itemP;
    String itemD;

    String data1;
    String data2;
    String data3;
    String data4;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toast.makeText(AdminPanel.this, "Welcome Admins", Toast.LENGTH_SHORT).show();



        Rname= findViewById(R.id.restaurantName);
        Rcity= findViewById(R.id.city);
        Radress= findViewById(R.id.adress);
        Rpostal= findViewById(R.id.postalcode);
        type= findViewById(R.id.texttt);
        cover= findViewById(R.id.cover);
        next= findViewById(R.id.nextAdmin);
        headig1= findViewById(R.id.text1);
        heading2=findViewById(R.id.text);
        add= findViewById(R.id.ADD);
        save=findViewById(R.id.save);


        burgerN= findViewById(R.id.itemname);
        burgerD= findViewById(R.id.itemdeDescription);
        burgerP= findViewById(R.id.itemPrice);


        firebaseFirestore= FirebaseFirestore.getInstance();

        coverstore = FirebaseStorage.getInstance().getReference().child("Cover");


//        documentReference = firebaseFirestore.collection("resturant").document(name);
  //     documentReference2 = firebaseFirestore.collection("resturant").document(name).collection("Menu").document();

//                collectionReference = firebaseFirestore.collection("resturant").document().collection("Info");
//                collectionReference2 = firebaseFirestore.collection("resturant").document().collection("Menu");

        cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadcover();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValidateInformation();




            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                burgerN.getText().clear();
                burgerD.getText().clear();
                burgerP.getText().clear();
                burgerN.setVisibility(View.VISIBLE);
                burgerD.setVisibility(View.VISIBLE);
                burgerP.setVisibility(View.VISIBLE);

                add.setVisibility(View.INVISIBLE);
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateInformation2();

                add.setVisibility(View.VISIBLE);
                burgerN.setVisibility(View.INVISIBLE);
                burgerD.setVisibility(View.INVISIBLE);
                burgerP.setVisibility(View.INVISIBLE);
            }
        });



    }

    private void uploadcover() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction( Intent.ACTION_GET_CONTENT);
        cover.setVisibility(View.GONE);

        startActivityForResult(intent,gallary);
        cover.setVisibility(View.VISIBLE);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode== gallary && resultCode== RESULT_OK && data!=null){


            imageUri= data.getData();
            cover.setImageURI(imageUri);



        }else{
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            Log.i("img0","error2");
        }
    }

    private void ValidateInformation2() {

        itemN = burgerN.getText().toString();
        itemD = burgerD.getText().toString();
        itemP = burgerP.getText().toString();

        if (TextUtils.isEmpty(itemN)){

            Toast.makeText(this, "Enter Burger Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(itemD)){

            Toast.makeText(this, "Enter Burger Description", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(itemP)){

            Toast.makeText(this, "Enter Burger Price", Toast.LENGTH_SHORT).show();
        }
        else  {


          saveMenu();
        }



    }



    private void ValidateInformation() {

        nameRes = Rname.getText().toString();
        city=Rcity.getText().toString();
        address=Radress.getText().toString();
        DeliveryT=Rpostal.getText().toString();
        typeD=type.getText().toString();

        if (TextUtils.isEmpty(nameRes)){
            Toast.makeText(this, "Enter Restaurant Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(city)){
            Toast.makeText(this, "Enter City Name", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(address)){
            Toast.makeText(this, "Enter Restaurant Address", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DeliveryT)){
            Toast.makeText(this, "Enter PostalCode", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(typeD)){
            Toast.makeText(this, "Enter Delivery Type", Toast.LENGTH_SHORT).show();
        }
        else {

            Saveimgurl();
            Rname.setVisibility(View.GONE);
            Rcity.setVisibility(View.GONE);
            Radress.setVisibility(View.GONE);
            Rpostal.setVisibility(View.GONE);
            type.setVisibility(View.GONE);
            cover.setVisibility(View.GONE);
            headig1.setVisibility(View.GONE);
            next.setVisibility(View.GONE);

            heading2.setVisibility(View.VISIBLE);
            add.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
        }


    }

    private void Saveimgurl(){



        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        productkey = saveCurrentDate + saveCurrentTime;

        final StorageReference filePath = coverstore.child(imageUri.getLastPathSegment() + productkey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageUri);

        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminPanel.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();
                Log.i("img1","Product Image uploaded Successfully");

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        dowamlodimgurl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            dowamlodimgurl = task.getResult().toString();

                            Toast.makeText(AdminPanel.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();
                            Log.i("img2",dowamlodimgurl);


                        }

                        documentReference= firebaseFirestore.collection("resturant").document(nameRes);
                        ResturantInformation resturantInformation = new ResturantInformation();
                        resturantInformation.setRestuarantId(documentReference.getId());
                        resturantInformation.setName(nameRes);
                        resturantInformation.setCity(city);
                        resturantInformation.setAddress(address);
                        resturantInformation.setType(typeD);
                        resturantInformation.setPostal(DeliveryT);
                        resturantInformation.setImgUri(dowamlodimgurl);

                        documentReference.set(resturantInformation).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(AdminPanel.this, "success", Toast.LENGTH_SHORT).show();
                                    }
                            }
                        });







                    }

                });



            }
        });



    }

    private void saveMenu() {




        documentReference2= firebaseFirestore.collection("resturant").document(nameRes).collection("menu").document();
        MenuItemList menuItemList = new MenuItemList();

        menuItemList.setMenuID(documentReference2.getId());
        menuItemList.setItemName(itemN);
        menuItemList.setItemDes(itemD);
        menuItemList.setItemPrice(itemP);

        documentReference2.set(menuItemList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(AdminPanel.this, "success", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





}
