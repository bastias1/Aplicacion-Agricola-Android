package com.example.agricola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button sensoresButton = findViewById(R.id.sensoresButton);
        Button ubicacionesButton = findViewById(R.id.ubicacionesButton);
        Button lecturasButton = findViewById(R.id.lecturasButton);

        sensoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdministrarSensoresActivity.class);
                startActivity(intent);
            }
        });

        ubicacionesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UbicacionesActivity.class);
                startActivity(intent);
            }
        });
        lecturasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, lecturaSensoresActivity.class);
                startActivity(intent);
            }
        });
    }
}