package com.example.appclima.controller;

import com.example.appclima.service.ClimaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ciudad")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174"})
public class ClimaController {

  private final ClimaService climaService;

  public ClimaController(ClimaService climaService) {
    this.climaService = climaService;
  }

  @GetMapping(value = "/clima/{ciudad}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> obtenerClima(@PathVariable String ciudad) {
    return climaService.obtenerClima(ciudad);
  }
}
