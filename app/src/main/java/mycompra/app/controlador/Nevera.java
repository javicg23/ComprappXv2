package mycompra.app.controlador;


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

import java.util.ArrayList;

import mycompra.app.R;
import mycompra.app.adaptersRecycler.AdapterNevera;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class Nevera extends Fragment {

    ArrayList<String> listCantidad;
    ArrayList<String> listProd;
    ArrayList<String> listCaducidad;
    RecyclerView recycler;
    Iterador<Producto> listaProductos;

    public Nevera() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_nevera, container, false);

        recycler = view.findViewById(R.id.RecyclerId);
        getActivity().setTitle("Nevera");

        llenarLista();

        AdapterNevera adapter = new AdapterNevera(listCantidad, listProd, listCaducidad);

        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Nevera");
                bundle.putString("idProducto", String.valueOf(listaProductos.get(position).getId()));

                DetalleProdInventario detalleProdInventario = new DetalleProdInventario();
                detalleProdInventario.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, detalleProdInventario).addToBackStack(null);
                ft.commit();
            }
        }));

        recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                ((LinearLayoutManager) recycler.getLayoutManager()).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        FloatingActionButton buttonNuevoProdNevera = view.findViewById(R.id.buttonNuevoProdNevera);
        buttonNuevoProdNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Nevera");

                NuevoProducto nuevoProducto = new NuevoProducto();
                nuevoProducto.setArguments(bundle);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame, nuevoProducto).addToBackStack(null);
                fr.commit();
            }
        });
        return view;
    }

    private void llenarLista() {
        ProductoDAO productoDAO = new ProductoDAO(getActivity().getApplicationContext());

        listaProductos = productoDAO.getProductoListNevera();

        listCantidad = new ArrayList<String>();
        listProd = new ArrayList<String>();
        listCaducidad = new ArrayList<String>();

        while (listaProductos.hasNext()) {
            listCantidad.add(String.valueOf(listaProductos.actual().getCantidad()));
            listProd.add(listaProductos.actual().getNombre());
            listCaducidad.add(listaProductos.actual().getCaducidad());
            listaProductos.avanza();
        }
    }
}