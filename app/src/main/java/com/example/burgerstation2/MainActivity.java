package com.example.burgerstation2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.burgerstation2.Model.Users;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button login;
    Button signup;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login= findViewById(R.id.login);
        signup= findViewById(R.id.signin);
        progressDialog2= new ProgressDialog(this);
        Paper.init(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,Login.class);
                startActivity(intent);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent =new Intent(MainActivity.this,SignUp.class);
                startActivity(intent);
            }
        });

         String userIdCode= Paper.book().read(Prevelant.userIdCode);
        String userPasswordCode= Paper.book().read(Prevelant.userPasswordCode);

        if (userIdCode!=""  && userPasswordCode !=""){

            if (!TextUtils.isEmpty(userIdCode) && !TextUtils.isEmpty(userPasswordCode)){

                validateUserLogin(userIdCode,userPasswordCode);
                progressDialog2.setTitle("Already Logged In");
                progressDialog2.setMessage("Please Wait...");
                progressDialog2.setCanceledOnTouchOutside(false);
                progressDialog2.show();
            }

        }

    }

    private void validateUserLogin(final String UserId, final String password) {

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if ((snapshot.child("Users").child(UserId).exists())){

                    Users usersData = snapshot.child("Users").child(UserId).getValue(Users.class);

                    if(usersData.getUserId().equals(UserId)){

                        if(usersData.getPassword().equals(password)){
                            progressDialog2.dismiss();
                            Intent intent = new Intent(MainActivity.this,Home.class);
                            Prevelant.onlineUsers= usersData;
                            startActivity(intent);
                        }
                    }else {
                        progressDialog2.dismiss();
                        Toast.makeText(MainActivity.this, "Incorrect UserId Or Password", Toast.LENGTH_SHORT).show();

                    }



                }else
                {
                    progressDialog2.dismiss();
                    Toast.makeText(MainActivity.this, "This UserId isn't Registered", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}