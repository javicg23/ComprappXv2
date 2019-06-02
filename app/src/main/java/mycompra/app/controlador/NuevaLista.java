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
import mycompra.app.dao.ListaDAO;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.dao.ProductoListaDAO;
import mycompra.app.iterador.Agregado;
import mycompra.app.iterador.AgregadoConcreto;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Lista;
import mycompra.app.modelo.Producto;
import mycompra.app.modelo.ProductoLista;


/**
 * A simple {@link Fragment} subclass.
 */
public class NuevaLista extends Fragment {
    private Button btnAnyadirLista;
    private Button btnAnyadirProductoALista;
    private Button btnAtras;
    private EditText editTextNombreLista;
    private EditText editTextNombreProducto;
    private Agregado<Producto> agregaProd = new AgregadoConcreto<Producto>();

    public NuevaLista() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nueva_lista, container, false);

        editTextNombreLista = view.findViewById(R.id.editTextNombreListaNueva);
        editTextNombreProducto = view.findViewById(R.id.editTextNombreProductoListaNueva);

        btnAnyadirLista = view.findViewById(R.id.btnAnyadirListaNuevaLista);
        btnAnyadirLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Iterador<Producto> iteraProd = agregaProd.iterador();

                if (!editTextNombreLista.getText().toString().equalsIgnoreCase("") && iteraProd.hasNext()) {

                    ListaDAO listaDAO = new ListaDAO(getActivity().getApplicationContext());
                    Lista lista = new Lista();
                    lista.setNombre(editTextNombreLista.getText().toString());
                    int idLista = listaDAO.insert(lista);

                    ProductoDAO productoDAO = new ProductoDAO(getActivity().getApplicationContext());
                    ProductoListaDAO productoListaDAO = new ProductoListaDAO(getActivity().getApplicationContext());

                    while (iteraProd.hasNext()) {
                        ProductoLista productoLista = new ProductoLista();
                        int idProducto = productoDAO.insert(iteraProd.next());
                        productoLista.setIdLista(idLista);
                        productoLista.setIdProducto(idProducto);
                        productoListaDAO.insert(productoLista);
                    }

                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.frame, new Listas()).addToBackStack(null);
                    ft.commit();
                } else {
                    if (!iteraProd.hasNext()) {
                        Toast.makeText(getActivity().getApplicationContext(), "No hay productos en la lista", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar el nombre de la lista", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnAtras = view.findViewById(R.id.btnAtrasNuevaLista);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Listas()).addToBackStack(null);
                ft.commit();
            }
        });

        btnAnyadirProductoALista = view.findViewById(R.id.btnAnyadirProductoALista);


        btnAnyadirProductoALista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextNombreProducto.getText().toString().equalsIgnoreCase("")) {
                    Producto producto = new Producto();
                    producto.setNombre(editTextNombreProducto.getText().toString());
                    agregaProd.add(producto);
                    editTextNombreProducto.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), "Producto añadido a la lista correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar el nombre del producto", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

}
