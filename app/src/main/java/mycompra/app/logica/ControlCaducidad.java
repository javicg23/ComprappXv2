package mycompra.app.logica;

import android.graphics.Color;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ControlCaducidad
{

    public ControlCaducidad() { }

    public static void setColorCaducidad(String caducidad, TextView datosProd, TextView productos, TextView categoria) {

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/YYYY");
        String fechaActual = mdformat.format(date);

        String[] partesFecha = fechaActual.split("/");
        int diaActual = Integer.parseInt(partesFecha[0]);
        int mesActual = Integer.parseInt(partesFecha[1]);
        int anyoActual = Integer.parseInt(partesFecha[2]);

        String[] partesCaducidad = caducidad.split("/");
        int diaCaducidadProducto = Integer.parseInt(partesCaducidad[0]);
        int mesCaducidadProducto = Integer.parseInt(partesCaducidad[1]);
        int anyoCaducidadProducto = Integer.parseInt(partesCaducidad[2]);

        boolean caducado =  checkCaducidad(caducidad);

        if (!caducado && (diaActual + 3 >= diaCaducidadProducto)) {
            datosProd.setTextColor(Color.rgb( 255, 166, 20 ));
            productos.setTextColor(Color.rgb( 255, 166, 20 ));
            categoria.setTextColor(Color.rgb( 255, 166, 20 ));
        }
        if (caducado) {
            datosProd.setTextColor(Color.RED);
            productos.setTextColor(Color.RED);
            categoria.setTextColor(Color.RED);
        }

    }

    public static boolean checkCaducidad(String caducidad) {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/YYYY");
        String fechaActual = mdformat.format(date);

        String[] partesFecha = fechaActual.split("/");
        int diaActual = Integer.parseInt(partesFecha[0]);
        int mesActual = Integer.parseInt(partesFecha[1]);
        int anyoActual = Integer.parseInt(partesFecha[2]);

        String[] partesCaducidad = caducidad.split("/");
        int diaCaducidadProducto = Integer.parseInt(partesCaducidad[0]);
        int mesCaducidadProducto = Integer.parseInt(partesCaducidad[1]);
        int anyoCaducidadProducto = Integer.parseInt(partesCaducidad[2]);

        boolean caducado = false;

        if (anyoCaducidadProducto < anyoActual) {
            caducado = true;

        }
        else if (!caducado && (mesCaducidadProducto < mesActual) && (anyoCaducidadProducto < anyoActual)) {
            caducado = true;
        }
        else
        if (!caducado && (diaCaducidadProducto < diaActual) && (mesCaducidadProducto < mesActual) && (anyoCaducidadProducto < anyoActual) ) {
            caducado = true;
        }
        return caducado;
    }
}
