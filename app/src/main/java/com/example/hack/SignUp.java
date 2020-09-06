package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    EditText etEmail,etPassword;

    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etEmail = (EditText)findViewById(R.id.etext3);
        etPassword = (EditText)findViewById(R.id.etext4);

        auth = FirebaseAuth.getInstance();
    }

    public void SignUpB(View view) {
        String Email = etEmail.getText().toString();
        String Password = etPassword.getText().toString();

        if (Email.isEmpty() && Password.isEmpty()){
            Toast.makeText(SignUp.this,"Fields Are Empty",Toast.LENGTH_LONG).show();
        }
        else if(Email.isEmpty()){
            etEmail.setError("Please Enter Email Id");
            etEmail.requestFocus();
        }
        else if(Password.isEmpty()){
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
        }
        else if(!(Email.isEmpty() && Password.isEmpty())){
            auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(SignUp.this,"Sign Up Not Successfull !! Please Try Again!!",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(SignUp.this,"Successfully Sign Up !!",Toast.LENGTH_LONG).show();

                        Intent i = new Intent(SignUp.this,MainActivity.class);
                        startActivity(i);
                    }
                }
            });
        }
        else{
            Toast.makeText(SignUp.this,"Error Occured!!",Toast.LENGTH_LONG).show();
        }
    }


}
