package mycompra.app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import mycompra.app.iterador.Iterador;
import mycompra.app.iterador.IteradorConcreto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;


public class IteradorTest {
    private ArrayList<Integer> lista;
    private Iterador<Integer> iterador;

    @Before
    public void llenarLista() {
        lista = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            lista.add(i);
        }
    }

    @Test
    public void crearIterador() {
        iterador = new IteradorConcreto<>(lista);

        assertEquals((Integer) 0, iterador.get(0));
        assertEquals((Integer) 1, iterador.get(1));
        assertEquals((Integer) 2, iterador.get(2));
        assertEquals((Integer) 3, iterador.get(3));
        assertEquals((Integer) 4, iterador.get(4));
    }

    @Test
    public void iterear() {
        int posicion = 0;
        iterador = new IteradorConcreto<>(lista);

        while (iterador.hasNext()) {
            int actual = iterador.next();
            assertEquals(actual, posicion);
            posicion++;
        }

        assertFalse(iterador.hasNext());
    }

    @Test
    public void getPrevioCorrecto() {
        iterador = new IteradorConcreto<>(lista);
        iterador.inicio();

        assertNull(iterador.getPrevio());
        iterador.avanza();
        assertEquals((Integer) 0, iterador.getPrevio());
    }

    @Test
    public void PrevioCorrecto() {

        iterador = new IteradorConcreto<>(lista);
        iterador.inicio();
        iterador.avanza();
        iterador.avanza();
        assertEquals((Integer) 1, iterador.previo());
    }

    @Test
    public void iterear2() {
        int posicion = 0;
        iterador = new IteradorConcreto<>(lista);

        while (iterador.hasNext()) {
            int actual = iterador.actual();
            assertEquals(actual, posicion);
            posicion++;
            iterador.avanza();
        }

        assertFalse(iterador.hasNext());
    }
}

