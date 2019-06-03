package mycompra.app;
import android.widget.TextView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.iterador.IteradorConcreto;
import static mycompra.app.logica.ControlCaducidad.checkCaducidad;

import static org.junit.Assert.*;
public class ControlCaducidadTest {
    @Test
    public void checkCaducadoTest(){
        boolean caducado = checkCaducidad("07/03/1998");
        assertTrue(caducado);
    }

    @Test
    public void checkNoCaducadoTest(){
        boolean caducado = checkCaducidad("07/03/2020");
        assertEquals(false, caducado);
    }
}
