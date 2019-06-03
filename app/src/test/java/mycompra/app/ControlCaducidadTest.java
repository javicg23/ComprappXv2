package mycompra.app;

import org.junit.Test;

import static mycompra.app.logica.ControlCaducidad.checkCaducidad;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ControlCaducidadTest {
    @Test
    public void checkCaducadoTest() {
        boolean caducado = checkCaducidad("07/03/1998");
        assertTrue(caducado);
    }

    @Test
    public void checkNoCaducadoTest() {
        boolean caducado = checkCaducidad("07/03/2020");
        assertFalse(caducado);
    }
}
