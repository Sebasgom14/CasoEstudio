package com.CasoEstudio1.repository;

import com.CasoEstudio1.domain.Sala;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaRepository extends JpaRepository<Sala, Long> {

    
    List<Sala> findByCapacidadGreaterThanEqual(Integer capacidad);

}