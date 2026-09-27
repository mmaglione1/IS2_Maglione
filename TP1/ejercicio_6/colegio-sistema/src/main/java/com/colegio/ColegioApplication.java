package com.colegio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ============================================================================
 * CLASE PRINCIPAL DE ARRANQUE (Spring Boot Entry Point)
 * ============================================================================
 *
 * Capa: Infraestructura / Inicialización de la Aplicación.
 *
 * Anotaciones:
 * - @SpringBootApplication: Meta-anotación que agrupa:
 *   1. @Configuration: Permite definir beans adicionales en el contexto.
 *   2. @EnableAutoConfiguration: Configura DataSource, JPA, Mail y Thymeleaf.
 *   3. @ComponentScan: Escanea entidades, repositorios, servicios y controladores.
 */
@SpringBootApplication
public class ColegioApplication {

    /**
     * Método principal que inicializa el servidor Tomcat embebido en el puerto 8080.
     *
     * @param args Parámetros pasados por línea de comandos.
     */
    public static void main(String[] args) {
        SpringApplication.run(ColegioApplication.class, args);
        System.out.println("\n==================================================");
        System.out.println(">> SISTEMA ESCOLAR INICIADO CORRECTAMENTE");
        System.out.println(">> URL de acceso: http://localhost:8080/login");
        System.out.println("==================================================\n");
    }
}