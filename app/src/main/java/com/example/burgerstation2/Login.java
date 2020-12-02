package com.example.burgerstation2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.burgerstation2.Model.Users;
import com.example.burgerstation2.Prevelant.Prevelant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {


    TextView UserIdlogin;
    TextView passwordLogin;
    Button signupbtnLogin;
    ProgressDialog progressDialog2;
    com.rey.material.widget.CheckBox checkBox;
    TextView admin;
    TextView user;
    String parentdbname="Users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        UserIdlogin =(TextView) findViewById(R.id.email);
        passwordLogin =(TextView) findViewById(R.id.password);
        signupbtnLogin =(Button) findViewById(R.id.login2);
        admin= findViewById(R.id.admin);
        user= findViewById(R.id.user);

        checkBox= (com.rey.material.widget.CheckBox) findViewById(R.id.checkbox);
        Paper.init(this);

        progressDialog2= new ProgressDialog(this);



        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setVisibility(View.INVISIBLE);
                signupbtnLogin.setText("LOGIN ADMIN");
                user.setVisibility(View.VISIBLE);
                parentdbname= "Admin";
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin.setVisibility(View.VISIBLE);
                signupbtnLogin.setText("LOGIN");
                user.setVisibility(View.INVISIBLE);
                parentdbname= "Users";
            }
        });

        signupbtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginAccount();
            }
        });
    }

    private void loginAccount() {


   
        String UserId= UserIdlogin.getText().toString();
        String password= passwordLogin.getText().toString();

        if (TextUtils.isEmpty(UserId)){
            Toast.makeText(this, "Enter Your Email.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Your Password.", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog2.setTitle("Login Account");
            progressDialog2.setMessage("Checking Your Information");
            progressDialog2.setCanceledOnTouchOutside(false);
            progressDialog2.show();

            validateUserLogin(UserId,password);

        }
    }

    private void validateUserLogin(final String UserId, final String password) {

        if (checkBox.isChecked()){

            Paper.book().write(Prevelant.userIdCode,UserId);
            Paper.book().write(Prevelant.userPasswordCode,password);

        }

        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

               if ((snapshot.child(parentdbname).child(UserId).exists())){

                   Users usersData = snapshot.child(parentdbname).child(UserId).getValue(Users.class);

                   if(usersData.getUserId().equals(UserId)){

                       if(usersData.getPassword().equals(password)){
                          if (parentdbname.equals("Admin")){
                              progressDialog2.dismiss();

                              Intent intent = new Intent(Login.this,AdminHome.class);
                              intent.putExtra("namerestu",UserId);
                              startActivity(intent);
                          } else if (parentdbname.equals("Users")){
                              progressDialog2.dismiss();
                              Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                              Intent intent = new Intent(Login.this,Home.class);
                              Prevelant.onlineUsers= usersData;
//                              intent.putExtra("uName",Prevelant.onlineUsers.getName());
                              startActivity(intent);
                          }
                       }
                   }else {
                       progressDialog2.dismiss();
                       Toast.makeText(Login.this, "Incorrect UserId Or Password", Toast.LENGTH_SHORT).show();

                   }



               }else
               {
                   progressDialog2.dismiss();
                   Toast.makeText(Login.this, "This UserId isn't Registered", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}