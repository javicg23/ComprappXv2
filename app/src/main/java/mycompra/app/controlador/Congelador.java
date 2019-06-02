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
import mycompra.app.adaptersRecycler.AdapterCongelador;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class Congelador extends Fragment {

    ArrayList<String> listCantidadC;
    ArrayList<String> listProdC;
    ArrayList<String> listCaducidadC;
    RecyclerView recycler;
    Iterador<Producto> listaProductos;

    public Congelador() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_congelador, container, false);

        recycler = view.findViewById(R.id.RecyclerId);
        getActivity().setTitle("Congelador");

        llenarLista();

        AdapterCongelador adapter = new AdapterCongelador(listCantidadC, listProdC, listCaducidadC);

        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Congelador");
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

        FloatingActionButton buttonNuevoProdNevera = view.findViewById(R.id.buttonNuevoProdCongelador);
        buttonNuevoProdNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Congelador");

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

        listaProductos = productoDAO.getProductoListCongelador();

        listCantidadC = new ArrayList<String>();
        listProdC = new ArrayList<String>();
        listCaducidadC = new ArrayList<String>();

        while (listaProductos.hasNext()) {
            listCantidadC.add(String.valueOf(listaProductos.actual().getCantidad()));
            listProdC.add(listaProductos.actual().getNombre());
            listCaducidadC.add(listaProductos.actual().getCaducidad());
            listaProductos.avanza();
        }
    }

}
