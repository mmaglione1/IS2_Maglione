package org.example.logica;

import java.util.Date;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
public class Alumno implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    private String nombre;
    private String apellido;

    @Temporal(TemporalType.DATE)
    private Date fechaNac;

    @OneToOne
    @JoinColumn(name = "carrera_id")
    private Carrera car;
    
    public Alumno(){
    }

    public Alumno(String nombre, String apellido, Date fechaNac, Carrera car) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.car = car;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Carrera getCarre() {
        return car;
    }

    public void setCarre(Carrera carre) {
        this.car = carre;
    }
    

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }
    
    @Override
    public String toString() {
    return "Nombre: "  + this.nombre + 
        " ||Apellido: " + this.apellido;
        
}
}
