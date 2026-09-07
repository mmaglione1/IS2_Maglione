package com.club.repositories;

import com.club.entities.PagoCuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PagoCuotaRepository extends JpaRepository<PagoCuota, Long> {
    List<PagoCuota> findAllByOrderByFechaPagoDesc();
}