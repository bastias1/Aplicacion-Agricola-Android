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

import com.example.agricola.adapters.UbicacionesAdapter;
import com.example.agricola.datos.Repositorio;
import com.example.agricola.model.Ubicacion;
import com.google.apphosting.datastore.testing.DatastoreTestTrace;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class listaUbicacionesActivity extends AppCompatActivity {

    private RecyclerView ubicacionesRecyclerView;
    private UbicacionesAdapter ubicacionesAdapter;
    private List<Ubicacion> ubicaciones;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ubicaciones);

        db = FirebaseFirestore.getInstance();

        ubicacionesRecyclerView = findViewById(R.id.listaUbicacionesRecyclerView);
        ubicacionesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ubicaciones = new ArrayList<>();
        ubicacionesAdapter = new UbicacionesAdapter(ubicaciones);
        ubicacionesRecyclerView.setAdapter(ubicacionesAdapter);

        cargarUbicaciones();
    }

    private void cargarUbicaciones() {
        db.collection("ubicaciones")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ubicaciones.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Ubicacion ubicacion = document.toObject(Ubicacion.class);
                            ubicaciones.add(ubicacion);
                        }
                        ubicacionesAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Error al cargar ubicaciones", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
