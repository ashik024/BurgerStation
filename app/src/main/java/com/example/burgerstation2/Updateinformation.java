package com.example.burgerstation2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Updateinformation extends AppCompatActivity {
    String name;
    String description;
    String Price;
    String idMenu;
    String idRes;

    EditText nameOfF;
    EditText nameOfD;
    EditText nameOfP;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateinformation);

        nameOfF= findViewById(R.id.nameofthefood);
        nameOfD= findViewById(R.id.desofthefood);
        nameOfP= findViewById(R.id.priceofthefood);
        save= findViewById(R.id.saveinfo);


        name= getIntent().getStringExtra("name");
        description= getIntent().getStringExtra("des");
        Price= getIntent().getStringExtra("price");
        idMenu= getIntent().getStringExtra("idmenu");
        idRes= getIntent().getStringExtra("idofresturant");


        nameOfF.setText(name);
        nameOfD.setText(description);
        nameOfP.setText(Price);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Saveinfo();
            }
        });

    }

    private void Saveinfo() {



        String editedFname= nameOfF.getText().toString();
        String editedFdes= nameOfD.getText().toString();
        String editedFprice= nameOfP.getText().toString();

        if (editedFname.equals("")){
            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();

        }else if(editedFdes.equals("")) {

            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();
        }else if(editedFprice.equals("")) {

            Toast.makeText(this, "Complete All Field", Toast.LENGTH_SHORT).show();
        }else  {


        }

       FirebaseFirestore firebaseFirestore= FirebaseFirestore.getInstance();
        DocumentReference documentReference2= firebaseFirestore.collection("resturant").document(idRes)
                .collection("menu").document(idMenu);

        MenuItemList menuItemList = new MenuItemList();



        menuItemList.setItemName(editedFname);
        menuItemList.setItemDes(editedFdes);
        menuItemList.setItemPrice(editedFprice);


        documentReference2.set(menuItemList).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    Toast.makeText(Updateinformation.this, "updated", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Updateinformation.this,Editmenu.class);
                    startActivity(intent);
                }

            }
        });
    }
}