package mycompra.app.controlador;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mycompra.app.R;
import mycompra.app.adaptersRecycler.AdapterTickets;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.MesDAO;
import mycompra.app.dao.SupermercadoDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Mes;
import mycompra.app.modelo.Supermercado;
import mycompra.app.modelo.Ticket;

/**
 * A simple {@link Fragment} subclass.
 */
public class TicketsDelMes extends Fragment {

    ArrayList<String> listSupermercados;
    ArrayList<String> listFechas;
    ArrayList<String> listPrecios;
    RecyclerView recyclerView;
    private MesDAO mesDAO;
    private TicketDAO ticketDAO;
    private SupermercadoDAO supermercadoDAO;
    private Iterador<Mes> listaMes;
    private Iterador<Ticket> listaTicketsMes;
    private Iterador<Supermercado> listaSupermercados;
    private Mes mes;

    public TicketsDelMes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tickets, container, false);

        getActivity().setTitle("Tickets del mes");
        recyclerView = view.findViewById(R.id.RecyclerTickets);

        llenarListaTickets();

        AdapterTickets adapter = new AdapterTickets(listSupermercados, listFechas, listPrecios);

        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Borrar Ticket")
                        .setMessage("¿Está seguro de que desea eliminar este ticket?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ticketDAO.delete(listaTicketsMes.get(position).getId());
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.frame, new TicketsDelMes()).addToBackStack(null);
                                ft.commit();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setCancelable(false)
                        .show();
            }
        }));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton btnAnyadirTicket = view.findViewById(R.id.btnAnyadirTicket);
        btnAnyadirTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new NuevoTicket()).addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    private void llenarListaTickets() {
        ticketDAO = new TicketDAO(getActivity().getApplicationContext());
        supermercadoDAO = new SupermercadoDAO(getActivity().getApplicationContext());
        mesDAO = new MesDAO(getActivity().getApplicationContext());

        listaMes = mesDAO.getMesList();
        listaSupermercados = supermercadoDAO.getSupermercadoList();

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
                mes = listaMes.actual();
                break;
            }
            listaMes.avanza();
        }

        listaTicketsMes = ticketDAO.getTicketListByMes(mes.getId());

        listSupermercados = new ArrayList<String>();
        listFechas = new ArrayList<String>();
        listPrecios = new ArrayList<String>();

        while (listaTicketsMes.hasNext()) {
            listPrecios.add(String.valueOf(listaTicketsMes.actual().getPrecio()));
            listFechas.add(listaTicketsMes.actual().getFecha());
            listSupermercados.add(listaSupermercados.get(listaTicketsMes.actual().getIdSupermercado() - 1).getNombre());
            listaTicketsMes.avanza();
        }
    }
}
