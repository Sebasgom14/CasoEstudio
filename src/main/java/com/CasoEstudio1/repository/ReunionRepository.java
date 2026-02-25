package com.CasoEstudio1.repository;

import com.CasoEstudio1.domain.Reunion;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReunionRepository extends JpaRepository<Reunion, Long> {

    List<Reunion> findBySala_IdAndFecha(Long idSala, LocalDate fecha);

}