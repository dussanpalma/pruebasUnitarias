package com.unitarias.mockito_app.ejemplos.services;

import com.unitarias.mockito_app.ejemplos.models.Examen;
import com.unitarias.mockito_app.ejemplos.repositories.ExamenRepository;
import java.util.Optional;

public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {

        Optional<Examen> examenOptional = examenRepository.findAll()
                .stream()
                .filter(e -> e.getNombre().contains(nombre))
                .findFirst();

        Examen examen = null;
        if (examenOptional.isPresent()) {

            examen = examenOptional.orElseThrow();

        }

        return examen;
    }

}
