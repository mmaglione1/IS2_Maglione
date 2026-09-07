package com.club;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * CLASE PRINCIPAL DE ARRANQUE (Spring Boot Entry Point)
 * ============================================================================
 * SpringBootApplication activa la auto-configuración, el escaneo de componentes
 * (@Controller, @Service, @Repository, @Entity) y la inicialización de Spring Security.
 */
@SpringBootApplication
public class ClubApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClubApplication.class, args);
        System.out.println("\n=======================================================");
        System.out.println(">> SISTEMA DE CLUB DEPORTIVO INICIADO CORRECTAMENTE");
        System.out.println(">> Acceso Web: http://localhost:8080");
        System.out.println(">> Credenciales por defecto: admin / admin123");
        System.out.println("=======================================================\n");
    }
}