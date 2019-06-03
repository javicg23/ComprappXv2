package mycompra.app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import mycompra.app.iterador.Iterador;
import mycompra.app.iterador.IteradorConcreto;

import static org.junit.Assert.*;


public class IteradorTest
{
    ArrayList<Integer> lista;
    Iterador<Integer> iterador;

    @Before
    public void llenarLista()
    {
        lista = new ArrayList<>();

        for(int i = 0; i < 5; i++)
        {
            lista.add(i);
        }
    }

    @Test
    public void crearIterador()
    {
        iterador = new IteradorConcreto<>(lista);

        assertEquals((Integer) iterador.get(0),(Integer) 0);
        assertEquals((Integer) iterador.get(1),(Integer) 1);
        assertEquals((Integer) iterador.get(2),(Integer) 2);
        assertEquals((Integer) iterador.get(3),(Integer) 3);
        assertEquals((Integer) iterador.get(4),(Integer) 4);
    }

    @Test
    public void iterear()
    {
        int posicion = 0;
        iterador = new IteradorConcreto<>(lista);

        while(iterador.hasNext())
        {
            int actual = iterador.next();
            assertEquals(actual, posicion);
            posicion++;
        }

        assertEquals(iterador.hasNext(), false);
    }

    @Test
    public void getPrevioCorrecto() {
        iterador = new IteradorConcreto<>(lista);
        iterador.inicio();

        assertEquals(iterador.getPrevio(), null);
        iterador.avanza();
        assertEquals((Integer) iterador.getPrevio(), (Integer) 0);
    }

    @Test
    public void PrevioCorrecto(){

        iterador = new IteradorConcreto<>(lista);
        iterador.inicio();
        iterador.avanza();
        iterador.avanza();
        assertEquals((Integer) iterador.previo(), (Integer) 1);
    }

    @Test
    public void iterear2()
    {
        int posicion = 0;
        iterador = new IteradorConcreto<>(lista);

        while(iterador.hasNext())
        {
            int actual = iterador.actual();
            assertEquals(actual, posicion);
            posicion++;
            iterador.avanza();
        }

        assertEquals(iterador.hasNext(), false);
    }
}

