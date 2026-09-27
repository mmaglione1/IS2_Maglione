package com.colegio.services;

import com.colegio.dtos.MateriaDTO;
import com.colegio.dtos.NotaDTO;
import com.colegio.entities.Alumno;
import com.colegio.entities.Materia;
import com.colegio.entities.Nota;
import com.colegio.repositories.AlumnoRepository;
import com.colegio.repositories.MateriaRepository;
import com.colegio.repositories.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: GESTIÓN DE NOTAS Y BOLETINES ESCOLARES
 * ============================================================================
 */
@Service
public class NotaService {

    @Autowired
    private NotaRepository notaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    @Transactional
    public NotaDTO registrarNota(NotaDTO dto) throws Exception {
        Alumno alumno = alumnoRepository.findById(dto.getAlumnoId())
                .orElseThrow(() -> new Exception("El alumno seleccionado no existe."));

        Materia materia = materiaRepository.findById(dto.getMateriaId())
                .orElseThrow(() -> new Exception("La materia seleccionada no existe."));

        // Evitar doble nota para el mismo alumno, materia y período si es nueva carga
        if (dto.getId() == null) {
            boolean yaExiste = notaRepository.findByAlumnoIdAndMateriaIdAndPeriodo(
                    dto.getAlumnoId(), dto.getMateriaId(), dto.getPeriodo()).isPresent();
            if (yaExiste) {
                throw new Exception("El alumno ya posee una calificación asentada para " + materia.getNombre() + " en el " + dto.getPeriodo());
            }
        }

        Nota nota = Nota.builder()
                .id(dto.getId())
                .calificacion(dto.getCalificacion())
                .periodo(dto.getPeriodo())
                .fechaEvaluacion(dto.getFechaEvaluacion())
                .alumno(alumno)
                .materia(materia)
                .build();

        // Al guardar, Hibernate Envers genera automáticamente el registro en notas_aud
        return mapearNotaADTO(notaRepository.save(nota));
    }

    @Transactional(readOnly = true)
    public List<NotaDTO> obtenerBoletinPorAlumno(Long alumnoId) {
        return notaRepository.findByAlumnoIdOrderByFechaEvaluacionDesc(alumnoId).stream()
                .map(this::mapearNotaADTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MateriaDTO> listarMaterias() {
        return materiaRepository.findAll().stream()
                .map(m -> MateriaDTO.builder()
                        .id(m.getId())
                        .nombre(m.getNombre())
                        .docenteId(m.getDocente().getId())
                        .docenteNombreCompleto(m.getDocente().getNombre() + " " + m.getDocente().getApellido())
                        .build())
                .collect(Collectors.toList());
    }

    private NotaDTO mapearNotaADTO(Nota entity) {
        return NotaDTO.builder()
                .id(entity.getId())
                .calificacion(entity.getCalificacion())
                .periodo(entity.getPeriodo())
                .fechaEvaluacion(entity.getFechaEvaluacion())
                .alumnoId(entity.getAlumno().getId())
                .alumnoNombreCompleto(entity.getAlumno().getNombre() + " " + entity.getAlumno().getApellido())
                .alumnoDocumento(entity.getAlumno().getDocumento())
                .materiaId(entity.getMateria().getId())
                .materiaNombre(entity.getMateria().getNombre())
                .gradoAulaInfo(entity.getAlumno().getAula().getNombreGrado() + " - " + entity.getAlumno().getAula().getNumeroAula())
                .build();
    }
}