package mycompra.app.modelo;

public class NullMes extends AbstractMes {

    @Override
    public boolean isNil() {
        return true;
    }

    @Override
    public String getNombre() {
        return "nulo";
    }
}
