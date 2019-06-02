package mycompra.app.controlador;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import mycompra.app.R;
import mycompra.app.dao.MesDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Mes;
import mycompra.app.modelo.Ticket;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoMensual extends Fragment {
    private TextView textViewMesActual;
    private TextView textViewMesAnterior;
    private TextView textViewGastosActual;
    private TextView textViewGastosAnterior;
    private EditText editTextPresupuestoActual;
    private TextView textViewPresupuestoAnterior;
    private MesDAO mesDAO;
    private TicketDAO ticketDAO;
    private Iterador<Mes> listaMes;
    private Iterador<Ticket> listaTicketsMes;
    private Iterador<Ticket> listaTicketsMesAnterior;
    private Mes mes;
    private Mes mesAnterior;
    private Button btnConfirmarInfoMensual;

    public InfoMensual() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_info_mensual, container, false);

        textViewMesActual = view.findViewById(R.id.textViewNombreMesInfoMensual);
        textViewMesAnterior = view.findViewById(R.id.textViewMesAnteriorInfoMensual);
        textViewGastosActual = view.findViewById(R.id.textViewGastosAcumuladosInfoMensual);
        textViewGastosAnterior = view.findViewById(R.id.textViewGastosMesAnteriorInfoMensual);
        editTextPresupuestoActual = view.findViewById(R.id.editTextPresupuestoInfoMensual);
        textViewPresupuestoAnterior = view.findViewById(R.id.textViewPresupuestoMesAnteriorInfoMensual);
        btnConfirmarInfoMensual = view.findViewById(R.id.btnConfirmarInfoMensual);

        getActivity().setTitle("Info Mensual");

        mesDAO = new MesDAO(getActivity().getApplicationContext());
        ticketDAO = new TicketDAO(getActivity().getApplicationContext());

        listaMes = mesDAO.getMesList();

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/YYYY");
        String fechaActual = mdformat.format(date);

        String[] partesFecha = fechaActual.split("/");
        String mesString = partesFecha[1];
        String anyo = partesFecha[2];

        String nombreMes = "";

        switch (Integer.parseInt(mesString)) {
            case 1:
                nombreMes = "enero";
                break;
            case 2:
                nombreMes = "febrero";
                break;
            case 3:
                nombreMes = "marzo";
                break;
            case 4:
                nombreMes = "abril";
                break;
            case 5:
                nombreMes = "mayo";
                break;
            case 6:
                nombreMes = "junio";
                break;
            case 7:
                nombreMes = "julio";
                break;
            case 8:
                nombreMes = "agosto";
                break;
            case 9:
                nombreMes = "septiembre";
                break;
            case 10:
                nombreMes = "octubre";
                break;
            case 11:
                nombreMes = "noviembre";
                break;
            case 12:
                nombreMes = "diciembre";
                break;
        }

        while (listaMes.hasNext()) {
            if (Integer.parseInt(anyo) == listaMes.actual().getAnyo() && listaMes.actual().getNombre().equalsIgnoreCase(nombreMes)) {
                mesAnterior = listaMes.getPrevio();
                mes = listaMes.actual();
                break;
            }
            listaMes.avanza();
        }

        listaTicketsMes = ticketDAO.getTicketListByMes(mes.getId());
        listaTicketsMesAnterior = ticketDAO.getTicketListByMes(mesAnterior.getId());

        textViewMesActual.setText(mes.getNombre());
        textViewMesAnterior.setText(mesAnterior.getNombre());

        NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);

        double gastosMesActual = 0.0;
        while (listaTicketsMes.hasNext()) {
            gastosMesActual += listaTicketsMes.next().getPrecio();
        }
        textViewGastosActual.setText(nf.format(gastosMesActual));

        double gastosMesAnterior = 0.0;
        while (listaTicketsMesAnterior.hasNext()) {
            gastosMesAnterior += listaTicketsMesAnterior.next().getPrecio();
        }
        textViewGastosAnterior.setText(nf.format(gastosMesAnterior));


        editTextPresupuestoActual.setText(String.valueOf(mes.getPresupuesto()));
        textViewPresupuestoAnterior.setText(String.valueOf(mesAnterior.getPresupuesto()));

        btnConfirmarInfoMensual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mes.setPresupuesto(Double.parseDouble(editTextPresupuestoActual.getText().toString()));
                mesDAO.update(mes);
                Toast.makeText(getActivity().getApplicationContext(), "Presupuesto guardado", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }


}
