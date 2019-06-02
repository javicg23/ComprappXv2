package mycompra.app.iterador;

import java.util.ArrayList;
import java.util.List;

public class AgregadoConcreto<T> implements Agregado<T> {

    private List<T> lista = new ArrayList<>();


    @Override
    public void add(T object) {
        lista.add(object);
    }

    @Override
    public void delete(T object) {
        lista.remove(object);
    }

    @Override
    public Iterador<T> iterador() {
        return new IteradorConcreto<T>(lista);
    }

}
