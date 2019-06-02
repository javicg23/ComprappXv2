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
import mycompra.app.adaptersRecycler.AdapterProductos;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.CategoriaDAO;
import mycompra.app.dao.ProductoDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Categoria;
import mycompra.app.modelo.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class Productos extends Fragment {

    ArrayList<String> listDatosProd;
    ArrayList<String> listProduct;
    ArrayList<String> listCatProd;
    RecyclerView recyclerView;
    Iterador<Producto> listaProductos;

    public Productos() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_productos, container, false);

        recyclerView = view.findViewById(R.id.RecyclerIdProd);

        llenarListaProd();

        AdapterProductos adapter = new AdapterProductos(listDatosProd, listProduct, listCatProd);

        recyclerView.setAdapter(adapter);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Productos");
                bundle.putString("idProducto", String.valueOf(listaProductos.get(position).getId()));

                DetalleProdInventario detalleProdInventario = new DetalleProdInventario();
                detalleProdInventario.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, detalleProdInventario).addToBackStack(null);
                ft.commit();
            }
        }));

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        FloatingActionButton buttonNuevosPoductos = view.findViewById(R.id.buttonNuevoProducto_prod);
        buttonNuevosPoductos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Productos");

                NuevoProducto nuevoProducto = new NuevoProducto();
                nuevoProducto.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, nuevoProducto).addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    private void llenarListaProd() {
        ProductoDAO productoDAO = new ProductoDAO(getActivity().getApplicationContext());
        CategoriaDAO categoriaDAO = new CategoriaDAO(getActivity().getApplicationContext());

        Iterador<Categoria> listaCategorias = categoriaDAO.getCategoriaList();

        listaProductos = productoDAO.getProductoList();

        listDatosProd = new ArrayList<String>();
        listProduct = new ArrayList<String>();
        listCatProd = new ArrayList<String>();

        while (listaProductos.hasNext()) {
            if (listaProductos.actual().getIdInventario() != 0) {
                listDatosProd.add(String.valueOf(listaProductos.actual().getCantidad()));
                listProduct.add(listaProductos.actual().getNombre());
                if (listaProductos.actual().getIdCategoria() != 0) {
                    listCatProd.add(listaCategorias.get(listaProductos.actual().getIdCategoria() - 1).getNombre());
                } else {
                    listCatProd.add("Sin categoria");
                }
            }
            listaProductos.avanza();
        }
    }
}
