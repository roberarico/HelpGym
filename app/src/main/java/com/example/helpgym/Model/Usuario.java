package com.example.helpgym.Model;

import java.io.Serializable;


public class Usuario implements Serializable {
    private int Id;
    private String nombre;
    private String usuario;
    private String contraseña;

    public Usuario() {
    }
    public Usuario(String nombre, String usuario){
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public Usuario(String nombre, String usuario, String contraseña) {
        this.nombre = nombre;
        this.usuario = usuario;
        this.contraseña = contraseña;
    }

    public boolean isNull() {
        return nombre.isEmpty() && usuario.isEmpty() && contraseña.isEmpty();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "Id=" + Id +
                ", nombre='" + nombre + '\'' +
                ", usuario='" + usuario + '\'' +
                ", contraseña='" + contraseña + '\'' +
                '}';
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}

