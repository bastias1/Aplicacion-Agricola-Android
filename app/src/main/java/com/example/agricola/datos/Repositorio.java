package com.example.agricola.datos;

import com.example.agricola.model.Tipo;
import com.example.agricola.model.Registro;
import com.example.agricola.model.Ubicacion;
import com.example.agricola.model.Sensor;

import java.util.ArrayList;
import java.util.List;

public class Repositorio {

    private static Repositorio instance = null;
    public List<Tipo> tipos;
    protected Repositorio(){
        tipos = new ArrayList<>();

        tipos.add(new Tipo("Humedad"));
        tipos.add(new Tipo("Temperatura"));
    }

    public static synchronized Repositorio getInstance(){
        if (null == instance) {
            instance = new Repositorio();
        }
        return instance;
    }
}
