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
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Locale;

import mycompra.app.R;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.modelo.Producto;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetalleProdInventario extends Fragment implements AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {

    private Producto producto;
    private String idProducto;
    private String anteriorFragment;
    private ProductoDAO productoDAO;
    private EditText editTextNombre;
    private EditText editTextPrecio;
    private EditText editTextCantidad;
    private EditText editTextCaducidad;
    private Spinner spinnerInventario;
    private Spinner spinnerCategoria;
    private TextView textViewNombre;
    private Button btnBorrar;
    private Button btnActualizar;
    private ArrayAdapter<CharSequence> adapterInventario;
    private ArrayAdapter<CharSequence> adapterCategoria;
    private View view;
    private String fecha;

    public DetalleProdInventario() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detalle_prod_inventario, container, false);

        editTextNombre = view.findViewById(R.id.editTextNombreDetalle);
        editTextPrecio = view.findViewById(R.id.editTextPrecioDetalle);
        editTextCantidad = view.findViewById(R.id.textViewCantidadDetalle);
        editTextCaducidad = view.findViewById(R.id.editTextCaducidadDetalle);
        textViewNombre = view.findViewById(R.id.textNombreProductoFragmentDetalle);
        spinnerInventario = view.findViewById(R.id.spinnerInventarioDetalle);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoriaDetalle);

        idProducto = getArguments().getString("idProducto");
        anteriorFragment = getArguments().getString("fragmentAnterior");

        adapterInventario = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.inventarios, android.R.layout.simple_spinner_item);
        adapterInventario.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerInventario.setAdapter(adapterInventario);
        spinnerInventario.setOnItemSelectedListener(this);

        adapterCategoria = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCategoria.setAdapter(adapterCategoria);
        spinnerCategoria.setOnItemSelectedListener(this);

        editTextCaducidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        productoDAO = new ProductoDAO(getActivity().getApplicationContext());

        producto = productoDAO.getProductoById(Integer.valueOf(idProducto));

        textViewNombre.setText(producto.getNombre());
        editTextNombre.setText(producto.getNombre());
        editTextPrecio.setText(String.valueOf(producto.getPrecio()));
        editTextCantidad.setText(String.valueOf(producto.getCantidad()));
        editTextCaducidad.setText(producto.getCaducidad());
        spinnerCategoria.setSelection(producto.getIdCategoria() - 1);
        spinnerInventario.setSelection(producto.getIdInventario() - 1);

        btnBorrar = view.findViewById(R.id.btnBorrarDetalle);
        btnBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productoDAO.delete(producto.getId());
                devolverAFragmentAnterior();
            }
        });

        btnActualizar = view.findViewById(R.id.btnActualizarDetalle);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNombre.getText().toString().equalsIgnoreCase("") && !editTextPrecio.getText().toString().equalsIgnoreCase("")
                        && !editTextPrecio.getText().toString().equalsIgnoreCase("0") && !editTextCantidad.getText().toString().equalsIgnoreCase("")
                        && !editTextCantidad.getText().toString().equalsIgnoreCase("0") && !editTextCaducidad.getText().toString().equalsIgnoreCase("")) {

                    producto.setNombre(String.valueOf(editTextNombre.getText()));

                    NumberFormat nf = NumberFormat.getNumberInstance(Locale.US);
                    nf.setMaximumFractionDigits(2);
                    producto.setPrecio(Double.parseDouble(nf.format(Double.parseDouble(editTextPrecio.getText().toString()))));

                    producto.setCantidad(Integer.parseInt(editTextCantidad.getText().toString()));
                    producto.setCaducidad(String.valueOf(editTextCaducidad.getText()));
                    producto.setIdCategoria(spinnerCategoria.getSelectedItemPosition() + 1);
                    producto.setIdInventario(spinnerInventario.getSelectedItemPosition() + 1);

                    productoDAO.update(producto);
                    Toast.makeText(getActivity().getApplicationContext(), "Producto actualizado correctamente", Toast.LENGTH_SHORT).show();
                    devolverAFragmentAnterior();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

    private void devolverAFragmentAnterior() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (anteriorFragment.toLowerCase()) {
            case "nevera":
                ft.replace(R.id.frame, new Nevera()).addToBackStack(null);
                break;
            case "congelador":
                ft.replace(R.id.frame, new Congelador()).addToBackStack(null);
                break;
            case "despensa":
                ft.replace(R.id.frame, new Despensa()).addToBackStack(null);
                break;
            case "productos":
                ft.replace(R.id.frame, new Productos()).addToBackStack(null);
                break;
        }
        ft.commit();
    }

    private void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), this,
                Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
        editTextCaducidad.setText(fecha);
    }
}
