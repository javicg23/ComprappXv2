package mycompra.app.iterador;

import java.util.List;

public class IteradorConcreto<T> implements Iterador {

    private List<T> list;
    private int cursor = 0;

    public IteradorConcreto(List<T> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return cursor != list.size();
    }

    @Override
    public T next() {
        T object = null;
        if (hasNext()) {
            object = list.get(cursor++);
        }
        return object;
    }

    @Override
    public Object getPrevio() {
        T object = null;
        if (cursor > 0) {
            object = list.get(--cursor);
            cursor++;
        }
        return object;
    }

    @Override
    public T previo() {
        T object = null;
        if (cursor > 0) {
            object = list.get(--cursor);
        }
        return object;
    }

    @Override
    public T actual() {
        T object = null;
        if (hasNext()) {
            object = list.get(cursor);
        }
        return object;
    }

    @Override
    public T get(int i) {
        return list.get(i);
    }

    @Override
    public void inicio() {
        cursor = 0;
    }

    @Override
    public void avanza() {
        cursor++;
    }
}
