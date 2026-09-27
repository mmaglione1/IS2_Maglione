package org.example.persistencia;

import java.util.ArrayList;
import java.util.List;
import org.example.logica.Alumno;
import org.example.logica.Carrera;
import org.example.logica.Materia;

public class ControladoraPersistencia {
    AlumnoJpaController aluJpa = new AlumnoJpaController();
    CarreraJpaController carJpa = new CarreraJpaController();
    MateriaJpaController matJpa = new MateriaJpaController();

    //ALUMNOS
    public void crearAlumno(Alumno alu) {
        aluJpa.create(alu);
    }

    public void eliminarAlumno(int id) {
        try {
            aluJpa.destroy(id);
        } catch (Exception ex){
            System.out.println("Alumno no encontrado");
        }
    }

    public void editarAlumno(Alumno alu) {
        try {
            aluJpa.edit(alu);
        } catch (Exception ex){
            System.out.println("Error al modificar");
        }
    }

    public Alumno traerAlumno(int id) {
        return aluJpa.findAlumno(id);

    }

    public ArrayList<Alumno> traerListaAlumnos() {
        List<Alumno> listita = aluJpa.findAlumnoEntities();
        ArrayList<Alumno> listaAlumnos = new ArrayList<Alumno> (listita);
        return listaAlumnos;
    }

    // CARRERA
    public void crearCarrera(Carrera car) {
        carJpa.create(car);
    }

    public void eliminarCarrera(int id) {
        try {
            carJpa.destroy(id);
        } catch (Exception ex){
            System.out.println("Carrera no encontrada");
        }
    }

    public void editarCarrera(Carrera car) {
        try {
            carJpa.edit(car);
        } catch (Exception ex){
            System.out.println("Error al modificar");
        }
    }

    public Carrera traerCarrera(int id) {
        return carJpa.findCarrera(id);
    }

    public ArrayList<Carrera> traerListaCarreras() {
        List<Carrera> listita = carJpa.findCarreraEntities();
        ArrayList<Carrera> listaCarreras = new ArrayList<Carrera> (listita);
        return listaCarreras;
    }

    // MATERIA
    public void crearMateria(Materia mat) {
        matJpa.create(mat);
    }

    public void eliminarMateria(int id) {
         try {
            matJpa.destroy(id);
        } catch (Exception ex){
            System.out.println("Carrera no encontrada");
        }
    }

    public void editarMateria(Materia mat) {
        try {
            matJpa.edit(mat);
        } catch (Exception ex){
            System.out.println("Error al modificar");
        }
    }

    public Materia traerMateria(int id) {
        return matJpa.findMateria(id);
    }

    public ArrayList<Materia> traerListaMaterias() {
        List<Materia> listita = matJpa.findMateriaEntities();
        ArrayList<Materia> listaMaterias = new ArrayList<Materia> (listita);
        return listaMaterias;
    }
}
