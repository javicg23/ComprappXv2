package mycompra.app.iterador;

public interface Agregado<T> {
    void add(T object);

    void delete(T object);

    Iterador<T> iterador();
}
