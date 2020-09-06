package com.example.hack.ui.notifications;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack.MainActivity;
import com.example.hack.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class NotificationsFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    private List<Upload> uploads;

    private Uri selectedImage;

    ImageView imageUpload;
    TextView name_user, role_user, gender_user, age_user, username_user, email_user, full_name_user, phone_number_user, state_user, pincode_user;

    private StorageReference storageReference;
    private DatabaseReference databaseReference;

    FirebaseAuth firebaseAuth1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        name_user = root.findViewById(R.id.name_user);
        role_user = root.findViewById(R.id.role_user);
        gender_user = root.findViewById(R.id.gender_user);
        age_user = root.findViewById(R.id.age_user);
        username_user = root.findViewById(R.id.username_user);
        email_user = root.findViewById(R.id.email_user);
        full_name_user = root.findViewById(R.id.full_name_user);
        phone_number_user = root.findViewById(R.id.phone_number_user);
        state_user = root.findViewById(R.id.state_user);
        pincode_user = root.findViewById(R.id.pincode_user);



        storageReference = FirebaseStorage.getInstance().getReference().child("ImageUploads");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ImageUploads").child(firebaseAuth.getCurrentUser().getUid());

        imageUpload = root.findViewById(R.id.profilepic_user);
        imageUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_IMAGE);
            }
        });

        Button button = root.findViewById(R.id.signout);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        firebaseAuth1 = FirebaseAuth.getInstance();
        final FirebaseUser user;
        user = firebaseAuth1.getCurrentUser();
        final String uid = user.getUid();
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Users");

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child(uid).child("Username").getValue(String.class);
                String fullname = dataSnapshot.child(uid).child("FullName").getValue(String.class);
                String email = dataSnapshot.child(uid).child("Email").getValue(String.class);
                String gender = dataSnapshot.child(uid).child("Gender").getValue(String.class);
                String age = dataSnapshot.child(uid).child("Age").getValue(String.class);
                String role = dataSnapshot.child(uid).child("Role").getValue(String.class);
                String phonenumber = dataSnapshot.child(uid).child("PhoneNumber").getValue(String.class);
                String state = dataSnapshot.child(uid).child("State").getValue(String.class);
                String pincode = dataSnapshot.child(uid).child("Pincode").getValue(String.class);

                username_user.setText(username);
                full_name_user.setText(fullname);
                email_user.setText(email);
                gender_user.setText(gender);
                age_user.setText(age);
                role_user.setText(role);
                phone_number_user.setText(phonenumber);
                name_user.setText(fullname);
                state_user.setText(state);
                pincode_user.setText(pincode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
/*
        DatabaseReference databaseReference2 = FirebaseDatabase.getInstance().getReference().child("ImageUploads");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String sa= dataSnapshot.child(uid).child("imageUrl").getValue(String.class);
                imageUpload.setImageURI(Uri.parse(sa));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
*/
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImage = data.getData();
            imageUpload.setImageURI(selectedImage);
            uploadFile();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        {
            if (selectedImage != null) {
                final StorageReference storageReference1 = storageReference.child(firebaseAuth.getCurrentUser().getUid() + "." + getFileExtension(selectedImage));
                storageReference1.putFile(selectedImage).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storageReference1.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();


                            Upload upload = new Upload(firebaseAuth.getCurrentUser().getUid(),
                                    downloadUri.toString());
                            databaseReference.setValue(upload);
                        } else {
                            Toast.makeText(getActivity(), "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }
}