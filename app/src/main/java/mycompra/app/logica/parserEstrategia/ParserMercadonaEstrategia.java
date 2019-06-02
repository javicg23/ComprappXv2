package mycompra.app.logica.parserEstrategia;

import android.content.Context;

import java.util.ArrayList;

import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.logica.ClasificadorCategoria;
import mycompra.app.logica.GestorInventario;
import mycompra.app.modelo.Categoria;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoTicket;
import mycompra.app.modelo.Ticket;

public class ParserMercadonaEstrategia implements ParserEstrategia
{
    private static ArrayList<Integer> cantidades;
    private static ArrayList<String> nombres;
    private static ArrayList<Double> precios;

    private static int contadorPrecioKilo;

    public ParserMercadonaEstrategia(){
        cantidades = ParserContexto.cantidades;
        nombres = ParserContexto.nombres;
        precios = ParserContexto.precios;

        contadorPrecioKilo = ParserContexto.contadorPrecioKilo;
    }

    public  void parseProducto(String prod)
    {
        if(!Character.isDigit(prod.charAt(2)))
        {
            cantidades.add(Integer.parseInt(String.valueOf(prod.charAt(0))));
            nombres.add(prod.substring(2));
        }
        else if (Character.isDigit(prod.charAt(2)))
        {
            if (prod.charAt(6) == 'k')
            {
                contadorPrecioKilo++;
                return;
            }

            precios.add(Double.parseDouble(prod));
        }
    }
}
