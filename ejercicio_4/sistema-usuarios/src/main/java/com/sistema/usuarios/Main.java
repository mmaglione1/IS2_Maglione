package com.sistema.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * CLASE PRINCIPAL DE ARRANQUE (Spring Boot Entry Point)
 * ============================================================================
 * SpringBootApplication: Meta-anotación que combina:
 * 1. @Configuration: Permite registrar beans adicionales en el contexto.
 * 2. @EnableAutoConfiguration: Configura automáticamente DataSource, JPA, 
 *    Hibernate y Thymeleaf según las dependencias del pom.xml.
 * 3. @ComponentScan: Escanea todos los paquetes hijos (controllers, services, 
 *    repositories, entities) para instanciar e inyectar dependencias automáticamente.
 */
@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
        System.out.println("\n==================================================");
        System.out.println(">> SISTEMA DE GESTIÓN DE USUARIOS INICIADO");
        System.out.println(">> Accede desde tu navegador en: http://localhost:8080");
        System.out.println("==================================================\n");
    }
}