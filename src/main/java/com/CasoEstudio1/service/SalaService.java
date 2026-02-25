package com.CasoEstudio1.service;

import com.CasoEstudio1.domain.Sala;
import com.CasoEstudio1.repository.SalaRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaService {

    @Autowired
    private SalaRepository salaRepository;

    @Transactional(readOnly = true)
    public List<Sala> getSalas() {
        return salaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Sala> getSala(Long idSala) {
        return salaRepository.findById(idSala);
    }

    @Transactional
    public void save(Sala sala) {
        salaRepository.save(sala);
    }

    @Transactional
    public void delete(Long idSala) {
        if (!salaRepository.existsById(idSala)) {
            throw new IllegalArgumentException("La sala con ID " + idSala + " no existe.");
        }
        try {
            salaRepository.deleteById(idSala);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException(
                    "No se puede eliminar la sala. Tiene reuniones asociadas.", e);
        }
    }
}