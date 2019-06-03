package mycompra.app;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.AbstractMes;
import mycompra.app.modelo.Mes;
import mycompra.app.modelo.MesFactory;

import static org.junit.Assert.*;

public class NullObjectTest {

    Agregado<Mes> listaMeses = new AgregadoConcreto<>();
    Iterador iteraMes;


    @Before
    public void rellenarListaMeses() {

        Mes a = new Mes("Mayo", 2019, 100 );
        Mes b = new Mes("Junio", 2019, 100 );
        Mes c = new Mes("Julio", 2019, 100 );

        listaMeses.add(a);
        listaMeses.add(b);
        listaMeses.add(c);
        iteraMes = listaMeses.iterador();
    }

    @Test
    public void seEncuentraMes(){

        AbstractMes test = MesFactory.checkExistsInMesList(iteraMes, "Junio", 2019);

        assertEquals(test.isNil(), false );
    }
    @Test
    public void seFallaMes(){

        AbstractMes test = MesFactory.checkExistsInMesList(iteraMes, "Agosto", 2019);

        assertEquals(test.isNil(), true );
    }
}
