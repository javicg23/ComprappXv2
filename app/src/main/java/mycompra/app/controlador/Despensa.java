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
import mycompra.app.adaptersRecycler.AdapterDespensa;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class Despensa extends Fragment {

    private ArrayList<String> listCantidadD, listProdD, listCaducidadD;
    private Iterador<Producto> listaProductos;

    public Despensa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_despensa, container, false);

        getActivity().setTitle("Despensa");

        RecyclerView recycler = view.findViewById(R.id.RecyclerId);
        FloatingActionButton buttonNuevoProdNevera = view.findViewById(R.id.buttonNuevoProdDespensa);

        llenarLista();

        AdapterDespensa adapter = new AdapterDespensa(listCantidadD, listProdD, listCaducidadD);

        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Despensa");
                bundle.putString("idProducto", String.valueOf(listaProductos.get(position).getId()));

                DetalleProdInventario detalleProdInventario = new DetalleProdInventario();
                detalleProdInventario.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, detalleProdInventario);
                ft.commit();
            }
        }));

        recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                ((LinearLayoutManager) recycler.getLayoutManager()).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        buttonNuevoProdNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Despensa");

                NuevoProducto nuevoProducto = new NuevoProducto();
                nuevoProducto.setArguments(bundle);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame, nuevoProducto);
                fr.commit();
            }
        });
        return view;
    }

    private void llenarLista() {
        ProductoDAO productoDAO = new ProductoDAO(getActivity().getApplicationContext());

        listaProductos = productoDAO.getProductoListDespensa();

        listCantidadD = new ArrayList<>();
        listProdD = new ArrayList<>();
        listCaducidadD = new ArrayList<>();

        while (listaProductos.hasNext()) {
            listCantidadD.add(String.valueOf(listaProductos.actual().getCantidad()));
            listProdD.add(listaProductos.actual().getNombre());
            listCaducidadD.add(listaProductos.actual().getCaducidad());
            listaProductos.avanza();
        }
    }
}