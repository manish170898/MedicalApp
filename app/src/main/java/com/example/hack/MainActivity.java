package com.example.hack;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    EditText etUsername,etPassword;
    public FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener  authStateListener;
    private GoogleSignInClient googleSignInClient;

    private int RC_SIGN_IN = 7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername = (EditText)findViewById(R.id.Username);
        etPassword = (EditText)findViewById(R.id.Password);

        firebaseAuth = FirebaseAuth.getInstance();

        findViewById(R.id.signInGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("Login", "Google sign in failed", e);
                //
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("Login", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Login", "signInWithCredential:success");
                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                            final String userid = firebaseAuth.getCurrentUser().getUid();

                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(userid).exists()){
                                        Intent intent = new Intent(MainActivity.this,LoginPatient.class);
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        startActivity(new Intent(MainActivity.this,LoginFourm.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Login", "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication Failed!", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    public void SignUp(View view) {
        Intent intent= new Intent(view.getContext(),SignUp.class);
        startActivity(intent);

    }

    public void LogIn(View view) {
        String Username = etUsername.getText().toString();
        String Password = etPassword.getText().toString();

        if (Username.isEmpty() && Password.isEmpty()){
            Toast.makeText(MainActivity.this,"Fields Are Empty",Toast.LENGTH_LONG).show();
        }
        else if(Username.isEmpty()){
            etUsername.setError("Please Enter Your Username");
            etUsername.requestFocus();
        }
        else if(Password.isEmpty()){
            etPassword.setError("Please Enter Password");
            etPassword.requestFocus();
        }
        else if(!(Username.isEmpty() && Password.isEmpty())) {
            firebaseAuth.signInWithEmailAndPassword(Username,Password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Login Error !", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users");
                        final String userid = firebaseAuth.getCurrentUser().getUid();

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.child(userid).exists()){
                                    Intent intent = new Intent(MainActivity.this,LoginPatient.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    startActivity(new Intent(MainActivity.this,LoginFourm.class));
                                    finish();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
        else {
            Toast.makeText(MainActivity.this, "Error Occured!", Toast.LENGTH_LONG).show();
        }

    }

    public void display(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
