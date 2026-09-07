package com.club.repositories;

import com.club.entities.RegistroAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RegistroAccesoRepository extends JpaRepository<RegistroAcceso, Long> {
    List<RegistroAcceso> findTop30ByOrderByFechaHoraDesc();
}