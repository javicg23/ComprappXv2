package mycompra.app.logica.parserEstrategia;

import java.util.ArrayList;

import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.logica.ClasificadorCategoria;
import mycompra.app.logica.GestorInventario;
import mycompra.app.modelo.Categoria;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoTicket;
import mycompra.app.modelo.Ticket;

public class ParserContexto {
    public static ArrayList<Producto> productos;
    public static ArrayList<Integer> cantidades;
    public static ArrayList<String> nombres;
    public static ArrayList<Double> precios;
    public static int contadorPrecioKilo;
    public static int numProductosNuevos;
    public static double precioTotalTicket;
    public static ParserEstrategia parser;
    static ProductoDAO prodDao;
    static TicketDAO ticketDAO;

    public ParserContexto(int supermercado) {
        if (supermercado == 1) {
            parser = new ParserMercadonaEstrategia();
        } else if (supermercado == 2) {
            parser = new ParserConsumEstrategia();
        } else if (supermercado == 3) {
        }

    }

    public static void createProductos() {
        int cantidad;
        String nombre;
        double precio;

        Ticket nuevoTicket = new Ticket();
        ticketDAO.insert(nuevoTicket);

        for (int i = 1; i <= numProductosNuevos; i++) {
            Producto nuevoProducto = new Producto();
            nuevoProducto.setId(prodDao.getLastProducto().getId() + i);

            cantidad = cantidades.get(i);
            nombre = nombres.get(i);
            precio = precios.get(contadorPrecioKilo + i);
            precioTotalTicket += precio;

            nuevoProducto.setCantidad(cantidad);
            nuevoProducto.setNombre(nombre);
            nuevoProducto.setPrecio(precio);


            Categoria categoriaProd = ClasificadorCategoria.findCategoria(nombre);
            nuevoProducto.setIdCategoria(categoriaProd.getId());


            productos.add(nuevoProducto);

            ProductoTicket nuevaRelacion = new ProductoTicket();
            nuevaRelacion.setIdTicket(nuevoTicket.getId());
            nuevaRelacion.setIdProducto(nuevoProducto.getId());
            nuevaRelacion.setCantidad(cantidad);

            GestorInventario.guardarProducto(nuevoProducto, nuevaRelacion);

        }

        nuevoTicket.setPrecio(precioTotalTicket);
        ticketDAO.update(nuevoTicket);
    }
}
