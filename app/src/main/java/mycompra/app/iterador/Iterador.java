package mycompra.app.iterador;

public interface Iterador<T> {
    boolean hasNext();

    T next();

    T getPrevio();

    T previo();

    T actual();

    T get(int i);

    void inicio();

    void avanza();


}
