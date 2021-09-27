package com.unitarias.mockito_app.ejemplos.repositories;

import com.unitarias.mockito_app.ejemplos.models.Examen;
import java.util.List;

public interface ExamenRepository {
    List<Examen> findAll();
}
