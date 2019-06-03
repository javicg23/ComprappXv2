package mycompra.app.controlador;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import mycompra.app.R;
import mycompra.app.adaptersRecycler.AdapterListaHabitual;
import mycompra.app.dao.ListaDAO;
import mycompra.app.dao.ProductoListaDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Lista;
import mycompra.app.modelo.Producto;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListaHabitual extends Fragment {

    private ArrayList<String> listCheckBox;
    private String idLista;
    private Lista lista;
    private ListaDAO listaDAO;

    public ListaHabitual() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_habitual, container, false);

        idLista = getArguments().getString("idLista");

        TextView textViewNombreListaHabitual = view.findViewById(R.id.textViewNombreListaHabitual);
        RecyclerView recycler = view.findViewById(R.id.RecyclerListaHabitualId);
        FloatingActionButton eliminarProd = view.findViewById(R.id.btnBorrarListaHabitual);
        FloatingActionButton nuevoProdLista = view.findViewById(R.id.btnAnyadirProductoAListaHabitual);

        listaDAO = new ListaDAO(getActivity().getApplicationContext());
        lista = listaDAO.getListaById(Integer.parseInt(idLista));

        getActivity().setTitle(lista.getNombre());
        textViewNombreListaHabitual.setText(lista.getNombre());

        llenarLista();

        AdapterListaHabitual adapter = new AdapterListaHabitual(listCheckBox);

        recycler.setAdapter(adapter);

        recycler.setItemAnimator(new DefaultItemAnimator());

        recycler.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler.getContext(),
                ((LinearLayoutManager) recycler.getLayoutManager()).getOrientation());
        recycler.addItemDecoration(dividerItemDecoration);

        eliminarProd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Borrar Lista")
                        .setMessage("¿Está seguro de que desea eliminar esta lista?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                listaDAO.delete(lista.getId());
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.frame, new Listas());
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
        });

        nuevoProdLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("idLista", idLista);

                NuevoProductoLista nuevoProductoLista = new NuevoProductoLista();
                nuevoProductoLista.setArguments(bundle);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame, nuevoProductoLista);
                fr.commit();
            }
        });

        return view;
    }

    private void llenarLista() {
        ProductoListaDAO productoListaDAO = new ProductoListaDAO(getActivity().getApplicationContext());
        Iterador<Producto> listaProductos = productoListaDAO.getProductoListFromListaHabitual(lista.getId());

        listCheckBox = new ArrayList<>();

        while (listaProductos.hasNext()) {
            listCheckBox.add(listaProductos.next().getNombre());
        }
    }
}
