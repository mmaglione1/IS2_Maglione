package org.example;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import org.example.logica.Alumno;
import org.example.logica.Carrera;
import org.example.logica.Controladora;
import org.example.logica.Materia;

public class Main {
    public static void main(String[] args) {
        
        Controladora control = new Controladora();
        
        Materia mat1 = new Materia("Ingenieria de software", "Presencial");
        Materia mat2 = new Materia("Lenguajes formales", "Presencial");
        Materia mat3 = new Materia("PyE", "Virtual");
        
        control.crearMateria(mat1);
        control.crearMateria(mat2);
        control.crearMateria(mat3);
        
        List<Materia> listaMaterias = new LinkedList<>();
        listaMaterias.add(mat1);
        listaMaterias.add(mat2);
        listaMaterias.add(mat3);
        
        Carrera car = new Carrera("LCC",listaMaterias);
        control.crearCarrera(car);

        Alumno alu = new Alumno("Roman","Riquelme",new Date(), car);
        control.crearAlumno(alu);
        
        //control.eliminarAlumno(2);
        
        //alu.setApellido("Perez");
        //control.editarAlumno(alu);
        
        
   
    }
}