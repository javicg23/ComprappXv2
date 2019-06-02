package mycompra.app.controlador;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mycompra.app.R;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.ProductoListaDAO;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoLista;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoProductoLista extends Fragment {
    private EditText editTextNombreProducto;
    private ProductoDAO productoDAO;
    private ProductoListaDAO productoListaDAO;
    private String idLista;

    public NuevoProductoLista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo_producto_lista, container, false);

        editTextNombreProducto = view.findViewById(R.id.editTextNombreProductoAnyadirProductoListaHabitual);

        productoDAO = new ProductoDAO(getActivity().getApplicationContext());
        productoListaDAO = new ProductoListaDAO(getActivity().getApplicationContext());
        idLista = getArguments().getString("idLista");

        Button btnAnyadir = view.findViewById(R.id.btnAnyadirNuevoProdLista);
        btnAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNombreProducto.getText().toString().equalsIgnoreCase("")) {
                    Producto producto = new Producto();
                    producto.setNombre(editTextNombreProducto.getText().toString());
                    int idProducto = productoDAO.insert(producto);
                    ProductoLista productoLista = new ProductoLista();
                    productoLista.setIdProducto(idProducto);
                    productoLista.setIdLista(Integer.parseInt(idLista));
                    productoListaDAO.insert(productoLista);
                    editTextNombreProducto.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), "Producto añadido a la lista correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnAtras = view.findViewById(R.id.btnAtrasNuevoProdLista);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                bundle.putString("idLista", idLista);

                ListaHabitual listaHabitual = new ListaHabitual();
                listaHabitual.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, listaHabitual).addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

}
