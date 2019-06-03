package mycompra.app;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.iterador.IteradorConcreto;

import static org.junit.Assert.assertEquals;


public class AgregadoTest {

    @Before
    public void crearIterador() {
        Agregado<Integer> agregador = new AgregadoConcreto<>();
        Iterador<Integer> iterador = agregador.iterador();
        Iterador<Integer> iteradorCorrecto = new IteradorConcreto<>(new ArrayList<Integer>());

        assertEquals(iteradorCorrecto.size(), iterador.size());
        assertEquals(0, agregador.size());
    }

    @Test
    public void agregarCorrecto() {
        Agregado<Integer> agregador = new AgregadoConcreto<>();

        for (int i = 0; i < 5; i++) {
            agregador.add(i);
        }

        assertEquals(5, agregador.size());
    }

    @After
    public void eliminarCorrecto() {
        Agregado<Integer> agregador = new AgregadoConcreto<>();

        for (int i = 0; i < 5; i++) {
            agregador.add(i);
        }

        agregador.delete(0);

        assertEquals(4, agregador.size());
    }
}
