package com.unitarias.mockito_app.ejemplos.services;

import com.unitarias.mockito_app.ejemplos.models.Examen;

public interface ExamenService {
    
    Examen findExamenPorNombre(String nombre);
    
}
