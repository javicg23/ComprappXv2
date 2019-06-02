package mycompra.app.controlador;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import mycompra.app.R;
import mycompra.app.dao.MesDAO;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.ProductoTicketDAO;
import mycompra.app.dao.TicketDAO;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Mes;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoTicket;
import mycompra.app.modelo.Ticket;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoTicket extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private EditText editTextNombre;
    private EditText editTextPrecio;
    private EditText editTextCantidad;
    private EditText editTextCaducidad;
    private EditText editTextFecha;
    private Spinner spinnerInventario;
    private Spinner spinnerCategoria;
    private Spinner spinnerSupermercado;
    private ArrayAdapter<CharSequence> adapterInventario;
    private ArrayAdapter<CharSequence> adapterCategoria;
    private ArrayAdapter<CharSequence> adapterSupermercado;
    private Button btnCancelar;
    private Button btnAnyadir;
    private Button btnAnyadirProducto;
    private String fecha;
    private String fechaTicket;
    private Mes mes;
    private int idMes = 0;
    private int anyoTicket;
    private int mesTicket;
    private Agregado<Producto> agregaProd = new AgregadoConcreto<>();
    private NumberFormat nf;
    private Iterador<Mes> listaMes;
    private MesDAO mesDAO;
    private ProductoDAO productoDAO;
    private boolean pulsadoCaducidad = false;
    private boolean pulsadoFecha = false;

    public NuevoTicket() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo_ticket, container, false);

        spinnerInventario = view.findViewById(R.id.spinnerInventarioNuevoTicket);
        spinnerSupermercado = view.findViewById(R.id.spinnerSupermercadoNuevoTicket);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoriaNuevoTicket);
        editTextPrecio = view.findViewById(R.id.editTextPrecioNuevoTicket);
        editTextCantidad = view.findViewById(R.id.editTextCantidadNuevoTicket);
        editTextCaducidad = view.findViewById(R.id.editTextCaducidadNuevoTicket);
        editTextFecha = view.findViewById(R.id.editTextFechaNuevoTicket);
        editTextNombre = view.findViewById(R.id.editTextNombreNuevoTicket);
        btnAnyadirProducto = view.findViewById(R.id.btnAnyadirProductoATicket);
        btnAnyadir = view.findViewById(R.id.btnConfirmarNuevoTicket);
        btnCancelar = view.findViewById(R.id.btnAtrasNuevoTicket);

        adapterCategoria = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategoria.setAdapter(adapterCategoria);
        spinnerCategoria.setOnItemSelectedListener(this);

        adapterInventario = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.inventarios, android.R.layout.simple_spinner_item);
        adapterInventario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerInventario.setAdapter(adapterInventario);
        spinnerInventario.setOnItemSelectedListener(this);

        adapterSupermercado = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.supermercados, android.R.layout.simple_spinner_item);
        adapterSupermercado.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSupermercado.setAdapter(adapterSupermercado);
        spinnerSupermercado.setOnItemSelectedListener(this);

        nf = NumberFormat.getNumberInstance(Locale.US);
        nf.setMaximumFractionDigits(2);

        editTextCaducidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulsadoCaducidad = true;
                pulsadoFecha = false;
                showDatePickerDialog();
            }
        });

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pulsadoCaducidad = false;
                pulsadoFecha = true;
                showDatePickerDialog();
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new TicketsDelMes()).addToBackStack(null);
                ft.commit();
            }
        });

        btnAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterador<Producto> iteraProd = agregaProd.iterador();

                if (!editTextFecha.getText().toString().equalsIgnoreCase("") && iteraProd.hasNext()) {

                    TicketDAO ticketDAO = new TicketDAO(getActivity().getApplicationContext());
                    Ticket ticket = new Ticket();
                    ticket.setFecha(fechaTicket);
                    double precioTotal = 0;
                    while (iteraProd.hasNext()) {
                        precioTotal += iteraProd.actual().getPrecio() * iteraProd.actual().getCantidad();
                        iteraProd.avanza();
                    }
                    iteraProd.inicio();

                    ticket.setPrecio(Double.parseDouble(nf.format(precioTotal)));
                    ticket.setIdSupermercado(spinnerSupermercado.getSelectedItemPosition() + 1);
                    mesDAO = new MesDAO(getContext().getApplicationContext());
                    listaMes = mesDAO.getMesList();
                    String nombreMes = "";
                    switch (mesTicket) {
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
                        if (anyoTicket == listaMes.actual().getAnyo() && listaMes.actual().getNombre().equalsIgnoreCase(nombreMes)) {
                            mes = listaMes.actual();
                            idMes = mes.getId();
                            break;
                        }
                        listaMes.avanza();
                    }
                    if (mes == null) {
                        Mes mes = new Mes();
                        mes.setPresupuesto(0);
                        mes.setNombre(nombreMes);
                        mes.setAnyo(anyoTicket);
                        idMes = mesDAO.insert(mes);
                    }
                    ticket.setIdMes(idMes);
                    int idTicket = ticketDAO.insert(ticket);

                    ProductoDAO productoDAO = new ProductoDAO(getActivity().getApplicationContext());
                    ProductoTicketDAO productoTicketDAO = new ProductoTicketDAO(getActivity().getApplicationContext());

                    while (iteraProd.hasNext()) {
                        ProductoTicket productoTicket = new ProductoTicket();
                        int idProducto = productoDAO.insert(iteraProd.actual());
                        productoTicket.setIdTicket(idTicket);
                        productoTicket.setIdProducto(idProducto);
                        productoTicket.setCantidad(iteraProd.actual().getCantidad());
                        productoTicketDAO.insert(productoTicket);
                        iteraProd.avanza();
                    }
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, new TicketsDelMes()).addToBackStack(null);
                    ft.commit();
                } else {
                    if (!iteraProd.hasNext()) {
                        Toast.makeText(getActivity().getApplicationContext(), "No hay productos en el ticket", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar la fecha del ticket", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        productoDAO = new ProductoDAO(getActivity().getApplicationContext());


        btnAnyadirProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNombre.getText().toString().equalsIgnoreCase("") && !editTextPrecio.getText().toString().equalsIgnoreCase("")
                        && !editTextCantidad.getText().toString().equalsIgnoreCase("") && !editTextCantidad.getText().toString().equalsIgnoreCase("0")
                        && !editTextCaducidad.getText().toString().equalsIgnoreCase("")) {

                    Producto producto = new Producto();
                    producto.setNombre(String.valueOf(editTextNombre.getText()));


                    producto.setPrecio(Double.parseDouble(nf.format(Double.parseDouble(editTextPrecio.getText().toString()))));
                    producto.setCantidad(Integer.parseInt(editTextCantidad.getText().toString()));
                    producto.setCaducidad(fecha);
                    producto.setIdCategoria(spinnerCategoria.getSelectedItemPosition() + 1);
                    producto.setIdInventario(spinnerInventario.getSelectedItemPosition() + 1);
                    agregaProd.add(producto);
                    editTextNombre.setText("");
                    editTextPrecio.setText("");
                    editTextCantidad.setText("");
                    editTextCaducidad.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), "Producto añadido correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
        if (pulsadoCaducidad) {
            editTextCaducidad.setText(fecha);
            pulsadoCaducidad = false;
        }
        if (pulsadoFecha) {
            fechaTicket = fecha;
            mesTicket = month + 1;
            anyoTicket = year;
            editTextFecha.setText(fecha);
            pulsadoFecha = false;
        }
    }
}
