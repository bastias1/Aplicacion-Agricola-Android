package com.example.agricola;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agricola.datos.Repositorio;
import com.example.agricola.model.Sensor;
import com.example.agricola.model.Tipo;
import com.example.agricola.model.Ubicacion;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ingresoSensoresActivity extends AppCompatActivity {
    private EditText nombreSensorEditText;
    private EditText descripcionSensorEditText;
    private EditText idealSensorEditText;
    private Spinner tipoSensorSpinner;
    private Spinner ubicacionSensorSpinner;
    private Button ingresarSensorButton, eliminarSensorButton,modificarSensorButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    private List<Tipo> tipos;
    private List<Ubicacion> ubicaciones;
    private List<Sensor> sensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores_ingreso);

        tipos = Repositorio.getInstance().tipos;
        sensores = new ArrayList<>();

        nombreSensorEditText = findViewById(R.id.nombreSensorEditText);
        descripcionSensorEditText = findViewById(R.id.descripcionSensorEditText);
        idealSensorEditText = findViewById(R.id.idealSensorEditText);
        tipoSensorSpinner = findViewById(R.id.tipoSensorSpinner);
        ubicacionSensorSpinner = findViewById(R.id.ubicacionSensorSpinner);
        ingresarSensorButton = findViewById(R.id.sensoresIngresoButton);
        eliminarSensorButton = findViewById(R.id.sensoresEliminarButton);
        modificarSensorButton = findViewById(R.id.sensoresModificarButton);

        ArrayAdapter<Tipo> tipoAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, tipos);
        tipoSensorSpinner.setAdapter(tipoAdapter);

        spinners();

        ingresarSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingresarSensor();
                finish();
            }
        });

        eliminarSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EliminarSensor();
                finish();
                limpiarCampos();
            }
        });

        modificarSensorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modificarSensor();
                finish();
                limpiarCampos();
            }
        });
    }

    private void spinners(){
        fetchUbicaciones();
    }

    private void ingresarSensor() {
        String nombre = nombreSensorEditText.getText().toString();
        String descripcion = descripcionSensorEditText.getText().toString();
        String idealInput = idealSensorEditText.getText().toString();
        final float ideal; // Declare ideal as final
        String ubicacionNombre = ubicacionSensorSpinner.getSelectedItem().toString();
        Tipo tipo = (Tipo) tipoSensorSpinner.getSelectedItem();

        if (nombre.isEmpty()) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: Nombre del Sensor es Obligatorio", Toast.LENGTH_LONG).show();
            return;
        } else if (nombre.length() < 5 || nombre.length() > 15) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: El nombre del Sensor debe tener entre 5 y 15 letras.", Toast.LENGTH_LONG).show();
            return;
        }
        if (descripcion.length() > 30) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: La descripción debe tener menos de 30 letras.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!idealInput.isEmpty()) {
            try {
                float parsedIdeal = Float.parseFloat(idealInput);
                if (parsedIdeal <= 0) {
                    Toast.makeText(ingresoSensoresActivity.this, "Error: El ideal debe ser un número positivo", Toast.LENGTH_LONG).show();
                    return;
                }
                ideal = parsedIdeal; // Assign parsed value to final variable
            } catch (NumberFormatException e) {
                Toast.makeText(ingresoSensoresActivity.this, "Error: El ideal debe ser un número", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            ideal = 0; // Assign default value to final variable
        }

        db.collection("ubicaciones")
                .whereEqualTo("nombre", ubicacionNombre)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        Ubicacion ubicacion = queryDocumentSnapshots.getDocuments().get(0).toObject(Ubicacion.class);
                        Sensor nuevo = new Sensor(nombre, descripcion, ideal, tipo, ubicacion);
                        sensores.add(nuevo);
                        db.collection("sensores").document().set(nuevo).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ingresoSensoresActivity.this, "Ingreso Exitoso en BD", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ingresoSensoresActivity.this, "Error de Ingreso en BD", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, "Ubicación no encontrada", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al buscar la ubicación", Toast.LENGTH_SHORT).show();
                    e.printStackTrace(); // Depuración del error
                });
    }

    private void modificarSensor(){
        String nombre = nombreSensorEditText.getText().toString();
        String descripcion = descripcionSensorEditText.getText().toString();
        String idealInput = idealSensorEditText.getText().toString();
        final float ideal; // Declare ideal as final

        if (nombre.isEmpty()) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: Nombre del Sensor es Obligatorio", Toast.LENGTH_LONG).show();
            return;
        } else if (nombre.length() < 5 || nombre.length() > 15) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: El nombre del Sensor debe tener entre 5 y 15 letras.", Toast.LENGTH_LONG).show();
            return;
        }
        if (descripcion.length() > 30) {
            Toast.makeText(ingresoSensoresActivity.this, "Error: La descripción debe tener menos de 30 letras.", Toast.LENGTH_LONG).show();
            return;
        }
        if (!idealInput.isEmpty()) {
            try {
                float parsedIdeal = Float.parseFloat(idealInput);
                if (parsedIdeal <= 0) {
                    Toast.makeText(ingresoSensoresActivity.this, "Error: El ideal debe ser un número positivo", Toast.LENGTH_LONG).show();
                    return;
                }
                ideal = parsedIdeal; // Assign parsed value to final variable
            } catch (NumberFormatException e) {
                Toast.makeText(ingresoSensoresActivity.this, "Error: El ideal debe ser un número", Toast.LENGTH_LONG).show();
                return;
            }
        } else {
            ideal = 0; // Assign default value to final variable
        }

        db.collection("sensores")
                .whereEqualTo("nombre", nombre)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        String sensorId = task.getResult().getDocuments().get(0).getId();
                        db.collection("sensores").document(sensorId)
                                .update("descripcion", descripcion, "ideal", ideal)
                                .addOnSuccessListener(aVoid -> Toast.makeText(this, "Sensor modificado correctamente", Toast.LENGTH_SHORT).show())
                                .addOnFailureListener(e -> Toast.makeText(this, "Error al modificar el sensor", Toast.LENGTH_SHORT).show());
                    } else {
                        Toast.makeText(this, "Sensor no encontrado", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void EliminarSensor(){
        String nombre = nombreSensorEditText.getText().toString();

        db.collection("sensores").whereEqualTo("nombre", nombre).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                    document.getReference().delete();
                    Toast.makeText(ingresoSensoresActivity.this, "Sensor Eliminado", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void fetchUbicaciones() {
        db.collection("ubicaciones").get().addOnSuccessListener(queryDocumentSnapshots -> {
            List<String> ubicaciones = new ArrayList<>();
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                ubicaciones.add(document.getString("nombre"));
                ArrayAdapter<String> ubicacionAdapter = new ArrayAdapter<>(this,R.layout.spinner_item, ubicaciones);
                ubicacionSensorSpinner.setAdapter(ubicacionAdapter);
            }
        }).addOnFailureListener(e -> {
            Toast.makeText(ingresoSensoresActivity.this, "Error al cargar ubicaciones", Toast.LENGTH_LONG).show();
        });
    }


    private void limpiarCampos(){
        nombreSensorEditText.setText("");
        descripcionSensorEditText.setText("");
        idealSensorEditText.setText("");
        tipoSensorSpinner.setSelection(0);
        ubicacionSensorSpinner.setSelection(0);
    }

}