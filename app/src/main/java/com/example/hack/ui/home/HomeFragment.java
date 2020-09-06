package com.example.hack.ui.home;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.hack.R;
import com.example.hack.ui.home.StateDiseaes.AndamanNicobar;
import com.example.hack.ui.home.StateDiseaes.AndhraPradesh;
import com.example.hack.ui.home.StateDiseaes.ArunachalPradesh;
import com.example.hack.ui.home.StateDiseaes.Assam;
import com.example.hack.ui.home.StateDiseaes.Bihar;
import com.example.hack.ui.home.StateDiseaes.Chandigarh;
import com.example.hack.ui.home.StateDiseaes.Chhattisgarh;
import com.example.hack.ui.home.StateDiseaes.DadarNagarHaveli;
import com.example.hack.ui.home.StateDiseaes.DamanDiu;
import com.example.hack.ui.home.StateDiseaes.Delhi;
import com.example.hack.ui.home.StateDiseaes.Goa;
import com.example.hack.ui.home.StateDiseaes.Gujrat;
import com.example.hack.ui.home.StateDiseaes.Haryana;
import com.example.hack.ui.home.StateDiseaes.HimachalPradesh;
import com.example.hack.ui.home.StateDiseaes.JammuKashmir;
import com.example.hack.ui.home.StateDiseaes.Jharkhand;
import com.example.hack.ui.home.StateDiseaes.Karnataka;
import com.example.hack.ui.home.StateDiseaes.Kerala;
import com.example.hack.ui.home.StateDiseaes.Lakshadweep;
import com.example.hack.ui.home.StateDiseaes.MadhyaPradesh;
import com.example.hack.ui.home.StateDiseaes.Maharashtra;
import com.example.hack.ui.home.StateDiseaes.Manipur;
import com.example.hack.ui.home.StateDiseaes.Meghalaya;
import com.example.hack.ui.home.StateDiseaes.Mizoram;
import com.example.hack.ui.home.StateDiseaes.Nagaland;
import com.example.hack.ui.home.StateDiseaes.Odisha;
import com.example.hack.ui.home.StateDiseaes.Pondicherry;
import com.example.hack.ui.home.StateDiseaes.Punjab;
import com.example.hack.ui.home.StateDiseaes.Rajasthan;
import com.example.hack.ui.home.StateDiseaes.Sikkim;
import com.example.hack.ui.home.StateDiseaes.TamilNadu;
import com.example.hack.ui.home.StateDiseaes.Telengana;
import com.example.hack.ui.home.StateDiseaes.Tripura;
import com.example.hack.ui.home.StateDiseaes.UttarPradesh;
import com.example.hack.ui.home.StateDiseaes.Uttarakhand;
import com.example.hack.ui.home.StateDiseaes.WestBengal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeFragment extends Fragment {

    String state_user;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, final Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);


        Button emergency = root.findViewById(R.id.Emergency);
        Button preventive = root.findViewById(R.id.preventive);
        Button facts = root.findViewById(R.id.facts);
        Button tips =  root.findViewById(R.id.tips);
        Button diet = root.findViewById(R.id.diet);
        final Button diseses = root.findViewById(R.id.diseases);

        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "9871925428";
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+number));
                startActivity(intent);
            }
        });

        preventive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Preventive.class);
                startActivity(intent);
            }
        });

        tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Tips.class));
            }
        });
        facts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),Facts.class));
            }
        });

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user;
        user = firebaseAuth.getCurrentUser();
        final String uid = user.getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String state = dataSnapshot.child(uid).child("State").getValue(String.class);
                state_user = state;
                diseses.setText("Diseases in " + state );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(),databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        diseses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (state_user.equals("Andhra Pradesh")) {
                    startActivity(new Intent(getActivity(), AndhraPradesh.class));
                } else if (state_user.equals("Arunachal Pradesh")) {
                    startActivity(new Intent(getActivity(), ArunachalPradesh.class));
                } else if (state_user.equals("Assam")) {
                    startActivity(new Intent(getActivity(), Assam.class));
                } else if (state_user.equals("Bihar")) {
                    startActivity(new Intent(getActivity(), Bihar.class));
                } else if (state_user.equals("Chhattisgarh")) {
                    startActivity(new Intent(getActivity(), Chhattisgarh.class));
                } else if (state_user.equals("Goa")) {
                    startActivity(new Intent(getActivity(), Goa.class));
                } else if (state_user.equals("Gujrat")) {
                    startActivity(new Intent(getActivity(), Gujrat.class));
                } else if (state_user.equals("Haryana")) {
                    startActivity(new Intent(getActivity(), Haryana.class));
                } else if (state_user.equals("Himachal Pradesh") || state_user.equals("HP") || state_user.equals("Hp")) {
                    startActivity(new Intent(getActivity(), HimachalPradesh.class));
                } else if (state_user.equals("Jammu") || state_user.equals("Kashmir") || state_user.equals("Jammu and Kashmir") || state_user.equals("Jammu & Kashmir")) {
                    startActivity(new Intent(getActivity(), JammuKashmir.class));
                } else if (state_user.equals("Jharkhand")) {
                    startActivity(new Intent(getActivity(), Jharkhand.class));
                } else if (state_user.equals("Karnataka")) {
                    startActivity(new Intent(getActivity(), Karnataka.class));
                } else if (state_user.equals("Kerala")) {
                    startActivity(new Intent(getActivity(), Kerala.class));
                } else if (state_user.equals("Madhya Pradesh") || state_user.equals("MP") || state_user.equals("Mp")) {
                    startActivity(new Intent(getActivity(), MadhyaPradesh.class));
                } else if (state_user.equals("Maharashtra")) {
                    startActivity(new Intent(getActivity(), Maharashtra.class));
                } else if (state_user.equals("Manipur")) {
                    startActivity(new Intent(getActivity(), Manipur.class));
                } else if (state_user.equals("Meghalaya")) {
                    startActivity(new Intent(getActivity(), Meghalaya.class));
                } else if (state_user.equals("Mizoram")) {
                    startActivity(new Intent(getActivity(), Mizoram.class));
                } else if (state_user.equals("Nagaland")) {
                    startActivity(new Intent(getActivity(), Nagaland.class));
                } else if (state_user.equals("Odisha")) {
                    startActivity(new Intent(getActivity(), Odisha.class));
                } else if (state_user.equals("Punjab")) {
                    startActivity(new Intent(getActivity(), Punjab.class));
                } else if (state_user.equals("Rajasthan")) {
                    startActivity(new Intent(getActivity(), Rajasthan.class));
                } else if (state_user.equals("Sikkim")) {
                    startActivity(new Intent(getActivity(), Sikkim.class));
                } else if (state_user.equals("Tamil Nadu")) {
                    startActivity(new Intent(getActivity(), TamilNadu.class));
                } else if (state_user.equals("Telengana")) {
                    startActivity(new Intent(getActivity(), Telengana.class));
                } else if (state_user.equals("Tripura")) {
                    startActivity(new Intent(getActivity(), Tripura.class));
                } else if (state_user.equals("Uttar Pradesh") || state_user.equals("UP") || state_user.equals("Up")) {
                    startActivity(new Intent(getActivity(), UttarPradesh.class));
                } else if (state_user.equals("Uttarakhand")) {
                    startActivity(new Intent(getActivity(), Uttarakhand.class));
                } else if (state_user.equals("West Bengal") || state_user.equals("Bengal") || state_user.equals("WB") || state_user.equals("Wb")) {
                    startActivity(new Intent(getActivity(), WestBengal.class));
                } else if (state_user.equals("Andaman and Nicobar Islands") || state_user.equals("Andaman and Nicobar")) {
                    startActivity(new Intent(getActivity(), AndamanNicobar.class));
                } else if (state_user.equals("Chandigarh")) {
                    startActivity(new Intent(getActivity(), Chandigarh.class));
                } else if (state_user.equals("Dadar and nagar haveli")) {
                    startActivity(new Intent(getActivity(), DadarNagarHaveli.class));
                } else if (state_user.equals("Daman and Diu")) {
                    startActivity(new Intent(getActivity(), DamanDiu.class));
                } else if (state_user.equals("Delhi")) {
                    startActivity(new Intent(getActivity(), Delhi.class));
                } else if (state_user.equals("Lakshadweep")) {
                    startActivity(new Intent(getActivity(), Lakshadweep.class));
                } else if (state_user.equals("Pondicherry")) {
                    startActivity(new Intent(getActivity(), Pondicherry.class));
                }
            }
        });


        return root;
    }
}