package com.club.controllers;

import com.club.services.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controlador de entrega de imágenes binarias (LONGBLOB) hacia las vistas Thymeleaf.
 */
@Controller
@RequiredArgsConstructor
public class ImagenController {

    private final ClubService clubService;

    @GetMapping(value = "/foto/socio/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> obtenerFotoSocio(@PathVariable Long id) {
        byte[] imagen = clubService.obtenerFotoSocio(id);
        if (imagen == null || imagen.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @GetMapping(value = "/foto/acceso/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> obtenerFotoAcceso(@PathVariable Long id) {
        byte[] imagen = clubService.obtenerFotoAcceso(id);
        if (imagen == null || imagen.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/accesos";
    }
}