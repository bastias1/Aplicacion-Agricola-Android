package com.example.agricola;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agricola.datos.Repositorio;
import com.example.agricola.model.Ubicacion;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class UbicacionesActivity extends AppCompatActivity {
    private EditText nombreUbicacionEditText;
    private EditText descripcionUbicacionEditText;

    private Button ingresarUbicacionButton;
    private Button listadoUbicacionesButton;

    private List<Ubicacion> ubicaciones = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicaciones);
        FirebaseFirestore db = FirebaseFirestore.getInstance();



        nombreUbicacionEditText = findViewById(R.id.nombreUbicacionEditText);
        descripcionUbicacionEditText = findViewById(R.id.descripcionUbicacionEditText);

        ingresarUbicacionButton = findViewById(R.id.ubicacionIngresoButton);
        listadoUbicacionesButton = findViewById(R.id.listaUbicacionesButton);

        ingresarUbicacionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre = nombreUbicacionEditText.getText().toString();
                if (!nombre.isEmpty()){
                    if (nombre.length() > 5 && nombre.length() < 15) {
                        String descripcion = descripcionUbicacionEditText.getText().toString();
                        if (descripcion.length() < 30) {
                            Ubicacion nuevo = new Ubicacion(nombre, descripcion);
                            ubicaciones.add(nuevo);
                            db.collection("ubicaciones").document(nuevo.getNombre()).set(nuevo).addOnSuccessListener(documentReference -> {
                                Toast.makeText(UbicacionesActivity.this, "Ingreso de Ubicacion Exitoso!", Toast.LENGTH_LONG).show();
                                finish();
                            });

                            Toast.makeText(UbicacionesActivity.this, "Ingreso de Ubicacion Exitoso!", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else {
                            Toast.makeText(UbicacionesActivity.this,"Error: La descripcion debe de tener menos de 30 letras.",Toast.LENGTH_LONG).show();

                        }
                    } else {
                        Toast.makeText(UbicacionesActivity.this,"Error: El nombre de la ubicacion debe de tener entre 5 y 15 letras.",Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(UbicacionesActivity.this,"Error: La ubicacion debe de tener un Nombre!",Toast.LENGTH_LONG).show();
                }
            }
        });

        listadoUbicacionesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UbicacionesActivity.this, listaUbicacionesActivity.class);
                startActivity(intent);
            }
        });

    }
}