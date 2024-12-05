package com.example.agricola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class AdministrarSensoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_administracion_sensores);

        Button ingresoSensoresButton = findViewById(R.id.menuIngresarSensoresButton);
        Button listaSensoresButton = findViewById(R.id.SensoresListaButton);
        Button buscarSensoresButton = findViewById(R.id.BuscarSensoresButton);

        ingresoSensoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdministrarSensoresActivity.this, ingresoSensoresActivity.class);
                startActivity(intent);
            }
        });

        listaSensoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdministrarSensoresActivity.this, listaSensoresActivity.class);
                startActivity(intent);
            }
        });

        buscarSensoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdministrarSensoresActivity.this, buscarSensoresActivity.class);
                startActivity(intent);
            }
        });
    }
}