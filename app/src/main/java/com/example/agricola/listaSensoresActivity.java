package com.example.agricola;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricola.adapters.SensoresAdapter;
import com.example.agricola.datos.Repositorio;
import com.example.agricola.model.Sensor;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class listaSensoresActivity extends AppCompatActivity {

    private RecyclerView sensoresRecyclerView;
    private SensoresAdapter sensoresAdapter;
    private List<Sensor> sensores;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_sensores);

        db = FirebaseFirestore.getInstance();

        sensoresRecyclerView = findViewById(R.id.listaSensoresRecyclerView);
        sensoresRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        sensores = new ArrayList<>();
        sensoresAdapter = new SensoresAdapter(sensores);
        sensoresRecyclerView.setAdapter(sensoresAdapter);

        obtenerSensores();
    }

    private void obtenerSensores(){
        db.collection("sensores")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        sensores.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Sensor sensor = document.toObject(Sensor.class);
                            sensores.add(sensor);
                        }
                        sensoresAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error al cargar sensores", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}