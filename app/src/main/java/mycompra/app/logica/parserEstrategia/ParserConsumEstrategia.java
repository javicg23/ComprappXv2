package mycompra.app.logica.parserEstrategia;

import java.util.ArrayList;

public class ParserConsumEstrategia implements ParserEstrategia
{
    private static ArrayList<Integer> cantidades;
    private static ArrayList<String> nombres;
    private static ArrayList<Double> precios;

    private static int contadorPrecioKilo;

    public ParserConsumEstrategia(){
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
