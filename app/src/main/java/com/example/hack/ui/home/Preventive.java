package com.example.hack.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hack.R;
import com.example.hack.ui.home.preventive.Cholera;
import com.example.hack.ui.home.preventive.Dengue;
import com.example.hack.ui.home.preventive.Gastroenteritis;
import com.example.hack.ui.home.preventive.HepatitisA;
import com.example.hack.ui.home.preventive.Influenza;
import com.example.hack.ui.home.preventive.Malaria;
import com.example.hack.ui.home.preventive.Typhoid;
import com.example.hack.ui.home.preventive.VIralFever;

public class Preventive extends AppCompatActivity {

    TextView influenza, chloera, typhoid, hepatiA, dengue, gastro, malaria, viralfever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preventive);

        influenza = (TextView)findViewById(R.id.influenza);
        chloera = (TextView)findViewById(R.id.cholera);
        typhoid = (TextView)findViewById(R.id.typhoid);
        hepatiA = (TextView)findViewById(R.id.hepitA);
        dengue = (TextView)findViewById(R.id.dengue);
        gastro = (TextView)findViewById(R.id.gastro);
        malaria = (TextView)findViewById(R.id.malaria);
        viralfever = (TextView)findViewById(R.id.viralfever);

        influenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, Influenza.class));
            }
        });
        chloera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, Cholera.class));
            }
        });
        typhoid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, Typhoid.class));
            }
        });
        hepatiA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, HepatitisA.class));
            }
        });
        dengue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, Dengue.class));
            }
        });
        gastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Preventive.this, Gastroenteritis.class));
            }
        });
        malaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, Malaria.class));
            }
        });
        viralfever.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Preventive.this, VIralFever.class));
            }
        });

    }
}
