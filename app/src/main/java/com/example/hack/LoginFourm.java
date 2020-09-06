package com.example.hack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class LoginFourm extends AppCompatActivity {

    EditText etUsername,etFullName,etAge,etRole,etGender,etPhoneNumber,etEmail,etState,etPincode;

    public FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fourm);

        etEmail = (EditText)findViewById(R.id.etext7);
        etUsername = (EditText)findViewById(R.id.etext1);
        etFullName = (EditText)findViewById(R.id.etext2);
        etAge = (EditText)findViewById(R.id.etext5);
        etRole = (EditText)findViewById(R.id.etext6);
        etGender = (EditText)findViewById(R.id.etext3);
        etPhoneNumber = (EditText)findViewById(R.id.etext4);
        etState = (EditText)findViewById(R.id.etext8);
        etPincode = (EditText)findViewById(R.id.etext9);

        auth = FirebaseAuth.getInstance();

    }


    public void SignUpB(View view) {
        final String Username = etUsername.getText().toString();
        final String FullName = etFullName.getText().toString();
        final String Gender = etGender.getText().toString();
        final String Email = etEmail.getText().toString();
        final String PhoneNumber = etPhoneNumber.getText().toString();
        final String Age = etAge.getText().toString();
        final String Role = etRole.getText().toString();
        final String State = etState.getText().toString();
        final String Pincode = etPincode.getText().toString();

        if (Username.isEmpty() && State.isEmpty() && Pincode.isEmpty() && Email.isEmpty() && FullName.isEmpty() && Age.isEmpty() && Role.isEmpty()&& Gender.isEmpty() && PhoneNumber.isEmpty()) {
            Toast.makeText(LoginFourm.this, "Fields Are Empty", Toast.LENGTH_LONG).show();
        } else if (Username.isEmpty()) {
            etUsername.setError("Please Enter Your Username");
            etUsername.requestFocus();
        }else if (Email.isEmpty()){
            etEmail.setError("Please Enter Email");
            etEmail.requestFocus();
        } else if (FullName.isEmpty()) {
            etFullName.setError("Please Enter Your Full Name");
            etFullName.requestFocus();
        } else if (Age.isEmpty()) {
            etAge.setError("Please Enter Your Age");
            etAge.requestFocus();
        } else if (Role.isEmpty()) {
            etRole.setError("Please Enter Your Role");
            etRole.requestFocus();
        } else if (Gender.isEmpty()) {
            etGender.setError("Please Enter Your Gender");
            etGender.requestFocus();
        } else if (PhoneNumber.isEmpty()) {
            etPhoneNumber.setError("Please Enter Your Phone Number");
            etPhoneNumber.requestFocus();

        }else if(State.isEmpty()){
            etState.setError("Enter Your State");
            etState.requestFocus();
        }else if(Pincode.isEmpty()){
            etPincode.setError("Enter Pincode");
            etPincode.requestFocus();
        } else if (!(Username.isEmpty() && State.isEmpty() && Pincode.isEmpty() && Email.isEmpty() && FullName.isEmpty() && Age.isEmpty() && Role.isEmpty() && Gender.isEmpty() && PhoneNumber.isEmpty() )){

            final String userid = auth.getCurrentUser().getUid();
            DatabaseReference CURRENT_USER_DB =FirebaseDatabase.getInstance().getReference().child("Users").child(userid);

            Map newPost = new HashMap();

            newPost.put("Username",Username);
            newPost.put("FullName",FullName);
            newPost.put("Gender",Gender);
            newPost.put("Email",Email);
            newPost.put("PhoneNumber",PhoneNumber);
            newPost.put("Age",Age);
            newPost.put("Role",Role);
            newPost.put("State",State);
            newPost.put("Pincode",Pincode);

            CURRENT_USER_DB.setValue(newPost);
            Toast.makeText(LoginFourm.this, "Details Saved Successfully!!", Toast.LENGTH_SHORT).show();

            if(Role.equals("Doctor")){
                Intent i = new Intent(LoginFourm.this,LoginDoctor.class);
                startActivity(i);
                finish();
            }
            if(Role.equals("Patient")){
                Intent i = new Intent(LoginFourm.this, LoginPatient.class);
                startActivity(i);
                finish();
            }
        }
        else{
            Toast.makeText(LoginFourm.this,"Error Occured!!",Toast.LENGTH_LONG).show();
        }
    }


}
