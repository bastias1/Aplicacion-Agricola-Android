package com.example.agricola.model;

public class Tipo {
    private String nombre;

    public Tipo(){}

    public Tipo(String nombre){
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return this.nombre;
    }
}
