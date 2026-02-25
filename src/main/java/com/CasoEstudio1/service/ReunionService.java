package com.CasoEstudio1.service;

import com.CasoEstudio1.domain.Reunion;
import com.CasoEstudio1.repository.ReunionRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReunionService {

    @Autowired
    private ReunionRepository reunionRepository;

    @Transactional(readOnly = true)
    public List<Reunion> getReuniones() {
        return reunionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Reunion> getReunion(Long idReunion) {
        return reunionRepository.findById(idReunion);
    }

    @Transactional(readOnly = true)
    public List<Reunion> getReunionesPorSalayFecha(Long idSala,LocalDate fecha) {
        return reunionRepository.findBySala_IdAndFecha(idSala,fecha);
    }

    @Transactional
    public void save(Reunion reunion) {
        reunionRepository.save(reunion);
    }

    @Transactional
    public void delete(Long idReunion) {
        if (!reunionRepository.existsById(idReunion)) {
            throw new IllegalArgumentException("La reunión con ID " + idReunion + " no existe.");
        }
        reunionRepository.deleteById(idReunion);
    }
}