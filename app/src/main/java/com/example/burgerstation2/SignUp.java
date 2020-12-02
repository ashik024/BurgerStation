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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {

    TextView nameSignUp;
    TextView emailSignUp;
    TextView useridSignUp;
    TextView passwordSignUp;
    Button signupbtnSignUp;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameSignUp = (TextView) findViewById(R.id.nameS);
        emailSignUp = (TextView)  findViewById(R.id.emailS);
        useridSignUp = (TextView)  findViewById(R.id.userIdS);
        passwordSignUp = (TextView)  findViewById(R.id.passwordS);
        signupbtnSignUp =(Button)  findViewById(R.id.login2S);
        progressDialog= new ProgressDialog(this);


        signupbtnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createAccount();
            }
        });



    }

    private void createAccount() {

        String name= nameSignUp.getText().toString();
        String email= emailSignUp.getText().toString();
        String useId= useridSignUp.getText().toString();
        String password= passwordSignUp.getText().toString();

        if (TextUtils.isEmpty(name)){
            Toast.makeText(this, "Enter Your Name.", Toast.LENGTH_SHORT).show();
        }
       else if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Your Email.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(useId)){
            Toast.makeText(this, "Enter User ID.", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Your Password.", Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.setTitle("Create Account");
            progressDialog.setMessage("Checking Your Information");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            validateUser(name,email,useId,password);

        }
    }

    private void validateUser(final String name, final String email, final String useId, final String password) {

            final DatabaseReference databaseReference;
            databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!(snapshot.child("Users").child(useId).exists())){

                        HashMap<String, Object> userData = new HashMap<>();
                        userData.put("name",name);
                        userData.put("email",email);
                        userData.put("userId",useId);
                        userData.put("password",password);

                        databaseReference.child("Users").child(useId).updateChildren(userData).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    progressDialog.dismiss();
                                    Toast.makeText(SignUp.this, "Account Created  Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else {

                                    Toast.makeText(SignUp.this, "Error.Please Try Again After Sometime", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(SignUp.this,MainActivity.class);
                                    startActivity(intent);

                                }


                            }
                        });



                    }else {
                        progressDialog.dismiss();
                        Toast.makeText(SignUp.this, "User ID Already Exist.Try Again With Unique ID ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignUp.this,MainActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}