package com.unitarias.mockito_app.ejemplos.services;

import com.unitarias.mockito_app.ejemplos.models.Examen;
import com.unitarias.mockito_app.ejemplos.repositories.ExamenRepository;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

public class ExamenServiceImplTest {

    @Test
    public void testSomeMethod() {

//        ExamenRepository repository = new ExamenRepositoryImpl();
        ExamenRepository repository = mock(ExamenRepository.class);
        ExamenService service = new ExamenServiceImpl(repository);

        List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"),
                new Examen(6L, "Lenguaje"),
                new Examen(7L, "Historia"));

        when(repository.findAll()).thenReturn(datos);

        Examen examen = service.findExamenPorNombre("Matematicas");

        assertNotNull(examen);
        assertEquals(5, examen.getId());
        assertEquals("Matematicas", examen.getNombre());

    }

}
