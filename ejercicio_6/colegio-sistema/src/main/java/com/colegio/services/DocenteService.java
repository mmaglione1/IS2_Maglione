package com.colegio.services;

import com.colegio.dtos.CambioPasswordDTO;
import com.colegio.dtos.DocenteDTO;
import com.colegio.dtos.RegistroDocenteDTO;
import com.colegio.entities.Docente;
import com.colegio.repositories.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ============================================================================
 * CAPA DE SERVICIO: DOCENTES Y SEGURIDAD
 * ============================================================================
 * Implementa UserDetailsService para que Spring Security autentique contra MySQL.
 */
@Service
public class DocenteService implements UserDetailsService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String correoPersonal) throws UsernameNotFoundException {
        Docente docente = docenteRepository.findByCorreoPersonal(correoPersonal.trim().toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("No se encontró docente registrado con el correo: " + correoPersonal));

        return new User(
                docente.getCorreoPersonal(),
                docente.getClave(),
                docente.isActivo(),
                true, true, true,
                Collections.singletonList(new SimpleGrantedAuthority(docente.getRol()))
        );
    }

    @Transactional
    public DocenteDTO registrarDocente(RegistroDocenteDTO dto) throws Exception {
        if (!dto.getClave().equals(dto.getConfirmarClave())) {
            throw new Exception("Las contraseñas ingresadas no coinciden.");
        }

        String correo = dto.getCorreoPersonal().trim().toLowerCase();
        if (docenteRepository.existsByCorreoPersonal(correo)) {
            throw new Exception("El correo personal '" + correo + "' ya se encuentra registrado.");
        }

        // Construcción de la entidad auditada
        Docente docente = Docente.builder()
                .nombre(dto.getNombre().trim())
                .apellido(dto.getApellido().trim())
                .sexo(dto.getSexo())
                .fechaNacimiento(dto.getFechaNacimiento())
                .correoPersonal(correo)
                .clave(passwordEncoder.encode(dto.getClave()))
                .rol("ROLE_DOCENTE")
                .activo(true)
                .build();

        Docente guardado = docenteRepository.save(docente);

        // Envío asíncrono de correo de bienvenida por Mailtrap
        emailService.enviarCorreoBienvenida(guardado.getCorreoPersonal(), guardado.getNombre() + " " + guardado.getApellido());

        return mapearADTO(guardado);
    }

    @Transactional
    public void cambiarPassword(String correoDocente, CambioPasswordDTO dto) throws Exception {
        Docente docente = docenteRepository.findByCorreoPersonal(correoDocente)
                .orElseThrow(() -> new Exception("Docente no encontrado."));

        if (!passwordEncoder.matches(dto.getClaveActual(), docente.getClave())) {
            throw new Exception("La contraseña actual es incorrecta.");
        }

        if (!dto.getClaveNueva().equals(dto.getConfirmarClaveNueva())) {
            throw new Exception("La nueva contraseña y su confirmación no coinciden.");
        }

        // Se persiste la nueva clave encriptada (Hibernate Envers registra la modificación en docentes_aud)
        docente.setClave(passwordEncoder.encode(dto.getClaveNueva()));
        docenteRepository.save(docente);
    }

    @Transactional(readOnly = true)
    public DocenteDTO obtenerPorCorreo(String correo) throws Exception {
        Docente docente = docenteRepository.findByCorreoPersonal(correo)
                .orElseThrow(() -> new Exception("Docente no encontrado."));
        return mapearADTO(docente);
    }

    @Transactional(readOnly = true)
    public List<DocenteDTO> listarTodos() {
        return docenteRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    private DocenteDTO mapearADTO(Docente entity) {
        return DocenteDTO.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .apellido(entity.getApellido())
                .nombreCompleto(entity.getNombre() + " " + entity.getApellido())
                .sexo(entity.getSexo())
                .fechaNacimiento(entity.getFechaNacimiento())
                .correoPersonal(entity.getCorreoPersonal())
                .rol(entity.getRol())
                .build();
    }
}