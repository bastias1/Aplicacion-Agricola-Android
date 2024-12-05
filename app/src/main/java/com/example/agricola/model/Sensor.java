package com.example.agricola.model;

public class Sensor {
    private String nombre;
    private String descripcion;
    private float ideal;
    private Tipo tipo;
    private Ubicacion ubicacion;

    public Sensor(){}

    public Sensor(String nombre, String descripcion, float ideal, Tipo tipo, Ubicacion ubicacion){
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ideal = ideal;
        this.tipo = tipo;
        this.ubicacion = ubicacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getIdeal() {
        return ideal;
    }

    public void setIdeal(float ideal) {
        this.ideal = ideal;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getIdealString(){
        return String.valueOf(ideal);
    }

    public String toString() {
        return this.nombre;
    }
}
