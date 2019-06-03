package mycompra.app.modelo;

import android.content.Context;

import mycompra.app.dao.MesDAO;
import mycompra.app.iterador.Iterador;

public class MesFactory {

    private static MesDAO mesDAO = null;

    public static AbstractMes getMes(Context context, String nombre, int anyo) {

        mesDAO = new MesDAO(context);

        Iterador<Mes> listaMes = mesDAO.getMesList();

        while (listaMes.hasNext()) {
            if (anyo == listaMes.actual().getAnyo() && listaMes.actual().getNombre().equalsIgnoreCase(nombre)) {
                return listaMes.actual();
            }
            listaMes.avanza();
        }
        return new NullMes();
    }

    public static AbstractMes checkExistsInMesList(Iterador<Mes> listaMeses, String nombre, int anyo) {

        while (listaMeses.hasNext()) {
            if (anyo == listaMeses.actual().getAnyo() && listaMeses.actual().getNombre().equalsIgnoreCase(nombre)) {
                return listaMeses.actual();
            }
            listaMeses.avanza();
        }
        return new NullMes();
    }
}
