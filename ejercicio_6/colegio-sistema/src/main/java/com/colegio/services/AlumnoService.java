package com.colegio.services;

import com.colegio.dtos.AlumnoDTO;
import com.colegio.dtos.AulaDTO;
import com.colegio.entities.Alumno;
import com.colegio.entities.Aula;
import com.colegio.repositories.AlumnoRepository;
import com.colegio.repositories.AulaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: GESTIÓN DE ALUMNOS, GRADOS Y AULAS
 * ============================================================================
 */
@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Transactional(readOnly = true)
    public List<AlumnoDTO> listarTodos() {
        return alumnoRepository.findAll().stream()
                .map(this::mapearAlumnoADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AulaDTO> listarAulas() {
        return aulaRepository.findAll().stream()
                .map(a -> AulaDTO.builder()
                        .id(a.getId())
                        .nombreGrado(a.getNombreGrado())
                        .numeroAula(a.getNumeroAula())
                        .descripcionCompleta(a.getNombreGrado() + " - " + a.getNumeroAula())
                        .totalAlumnos(a.getAlumnos().size())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public AlumnoDTO registrarAlumno(AlumnoDTO dto) throws Exception {
        if (dto.getId() == null && alumnoRepository.existsByDocumento(dto.getDocumento().trim())) {
            throw new Exception("Ya existe un alumno registrado con el documento: " + dto.getDocumento());
        }

        Aula aula = aulaRepository.findById(dto.getAulaId())
                .orElseThrow(() -> new Exception("El Grado/Aula seleccionado no existe."));

        Alumno alumno = Alumno.builder()
                .id(dto.getId())
                .documento(dto.getDocumento().trim())
                .nombre(dto.getNombre().trim())
                .apellido(dto.getApellido().trim())
                .aula(aula)
                .build();

        return mapearAlumnoADTO(alumnoRepository.save(alumno));
    }

    @Transactional(readOnly = true)
    public AlumnoDTO buscarPorId(Long id) throws Exception {
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new Exception("Alumno no encontrado con ID: " + id));
        return mapearAlumnoADTO(alumno);
    }

    private AlumnoDTO mapearAlumnoADTO(Alumno entity) {
        return AlumnoDTO.builder()
                .id(entity.getId())
                .documento(entity.getDocumento())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .nombreCompleto(entity.getNombre() + " " + entity.getApellido())
                .aulaId(entity.getAula().getId())
                .nombreGrado(entity.getAula().getNombreGrado())
                .numeroAula(entity.getAula().getNumeroAula())
                .ubicacionGradoAula(entity.getAula().getNombreGrado() + " (" + entity.getAula().getNumeroAula() + ")")
                .build();
    }
}