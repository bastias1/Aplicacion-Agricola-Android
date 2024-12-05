package com.example.agricola;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricola.adapters.SensoresAdapter;
import com.example.agricola.model.Sensor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class buscarSensoresActivity extends AppCompatActivity {

    private AutoCompleteTextView buscarAutoCompleteTextView;
    private Button buscarButton;
    private RecyclerView resultadosRecyclerView;
    private SensoresAdapter sensoresAdapter;
    private List<Sensor> sensores;
    private FirebaseFirestore db;
    private List<String> nombresSensores; // Lista para los nombres de los sensores

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_sensores);
        db = FirebaseFirestore.getInstance();

        buscarAutoCompleteTextView = findViewById(R.id.buscarNombreSensorAutoCompleteTextView);
        buscarButton = findViewById(R.id.buscarButton);
        resultadosRecyclerView = findViewById(R.id.resultadosRecyclerView);


        resultadosRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensores = new ArrayList<>();
        nombresSensores = new ArrayList<>();
        sensoresAdapter = new SensoresAdapter(sensores);
        resultadosRecyclerView.setAdapter(sensoresAdapter);

        cargarNombresSensores();
        buscarButton.setOnClickListener(v -> buscarSensor());
    }

    private void cargarNombresSensores() {
        db.collection("sensores")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    nombresSensores.clear();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        String nombre = document.getString("nombre");
                        if (nombre != null) {
                            nombresSensores.add(nombre);
                        }
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, nombresSensores);
                    buscarAutoCompleteTextView.setAdapter(adapter);

                    buscarAutoCompleteTextView.setOnClickListener(v -> buscarAutoCompleteTextView.showDropDown());
                    buscarAutoCompleteTextView.setOnFocusChangeListener((v, hasFocus) -> {
                        if (hasFocus) {
                            buscarAutoCompleteTextView.showDropDown();
                        }
                    });
                    buscarAutoCompleteTextView.setThreshold(0); //Configura los caracteres minimos para mostrar la lista en este caso 0
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al cargar nombres de sensores", Toast.LENGTH_SHORT).show());
    }

    private void buscarSensor() {
        String nombre = buscarAutoCompleteTextView.getText().toString().trim();

        sensores.clear();
        if (nombre.isEmpty()) {
            db.collection("sensores")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Sensor sensor = document.toObject(Sensor.class);
                                sensores.add(sensor);
                            }
                            sensoresAdapter.notifyDataSetChanged();
                            if (sensores.isEmpty()) {
                                Toast.makeText(this, "No sensors available.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Error fetching sensors.", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            db.collection("sensores")
                    .whereEqualTo("nombre", nombre)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Sensor sensor = document.toObject(Sensor.class);
                                sensores.add(sensor);
                            }
                            sensoresAdapter.notifyDataSetChanged();
                            if (sensores.isEmpty()) {
                                Toast.makeText(this, "No sensors found with the given name.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(this, "Error searching for sensors.", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

}