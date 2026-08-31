package com.techstore;

import com.techstore.entities.Producto;
import com.techstore.entities.Proveedor;
import com.techstore.entities.Usuario;
import com.techstore.repositories.ProductoRepository;
import com.techstore.repositories.ProveedorRepository;
import com.techstore.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * ============================================================================
 * CLASE PRINCIPAL DE ARRANQUE (Spring Boot Entry Point)
 * ============================================================================
 * SpringBootApplication: Meta-anotación que activa:
 * 1. @Configuration: Configuración basada en Java.
 * 2. @EnableAutoConfiguration: Configuración automática de DataSource, JPA y Thymeleaf.
 * 3. @ComponentScan: Escaneo de controladores, servicios, repositorios y entidades.
 */
@SpringBootApplication
public class TechStoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechStoreApplication.class, args);
        System.out.println("\n==================================================");
        System.out.println(">> SISTEMA DE STOCK Y COMPRAS TECNOLÓGICAS INICIADO");
        System.out.println(">> Accede en tu navegador: http://localhost:8080");
        System.out.println("==================================================\n");
    }

    /**
     * ========================================================================
     * PRECARGA AUTOMÁTICA DE DATOS INICIALES (CommandLineRunner)
     * ========================================================================
     * Se ejecuta al iniciar la aplicación para insertar datos de prueba en MySQL
     * si las tablas están vacías.
     */
    @Bean
    CommandLineRunner inicializarDatos(UsuarioRepository usuarioRepository,
                                       ProveedorRepository proveedorRepository,
                                       ProductoRepository productoRepository) {
        return args -> {

            // 1. Crear Usuario Administrador inicial para Login
            if (usuarioRepository.count() == 0) {
                Usuario admin = Usuario.builder()
                        .nombreCompleto("Administrador General")
                        .username("admin")
                        .clave("1234") // Credencial inicial: admin / 1234
                        .rol("ADMIN")
                        .activo(true)
                        .build();
                usuarioRepository.save(admin);
                System.out.println(">> [SEEK/INIT] Usuario creado: admin / 1234");
            }

            // 2. Crear Proveedores Mayoristas iniciales
            if (proveedorRepository.count() == 0) {
                Proveedor prov1 = Proveedor.builder()
                        .cuit("30-71234567-9")
                        .razonSocial("Distribuidora Tech Global S.A.")
                        .telefono("+54 11 4321-0000")
                        .email("ventas@techglobal.com")
                        .direccion("Av. Corrientes 1234, CABA")
                        .build();

                Proveedor prov2 = Proveedor.builder()
                        .cuit("30-89654321-2")
                        .razonSocial("Mayorista MayorTech Argentina")
                        .telefono("+54 11 5678-9999")
                        .email("contacto@mayortech.com.ar")
                        .direccion("Parque Industrial Norte, Buenos Aires")
                        .build();

                proveedorRepository.save(prov1);
                proveedorRepository.save(prov2);
                System.out.println(">> [SEEK/INIT] Proveedores mayoristas cargados.");
            }

            // 3. Crear Catálogo Inicial de Productos de Tecnología
            if (productoRepository.count() == 0) {
                Producto prod1 = Producto.builder()
                        .codigoSku("TEC-NOTE-001")
                        .nombre("Notebook Lenovo ThinkPad 16GB RAM 512GB SSD")
                        .categoria("Computadoras")
                        .precioVenta(950000.0)
                        .stock(10) // Stock inicial
                        .activo(true)
                        .build();

                Producto prod2 = Producto.builder()
                        .codigoSku("TEC-MON-002")
                        .nombre("Monitor Gamer ASUS 27'' IPS 165Hz")
                        .categoria("Monitores")
                        .precioVenta(320000.0)
                        .stock(15)
                        .activo(true)
                        .build();

                Producto prod3 = Producto.builder()
                        .codigoSku("TEC-PER-003")
                        .nombre("Teclado Mecánico RGB Switch Red")
                        .categoria("Periféricos")
                        .precioVenta(85000.0)
                        .stock(25)
                        .activo(true)
                        .build();

                productoRepository.save(prod1);
                productoRepository.save(prod2);
                productoRepository.save(prod3);
                System.out.println(">> [SEEK/INIT] Catálogo tecnológico inicial cargado.");
            }
        };
    }
}