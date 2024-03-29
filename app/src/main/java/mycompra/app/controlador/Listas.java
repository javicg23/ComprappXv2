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
import mycompra.app.adaptersRecycler.AdapterListas;
import mycompra.app.adaptersRecycler.RecyclerItemClickListener;
import mycompra.app.dao.ListaDAO;
import mycompra.app.iterador.Iterador;
import mycompra.app.modelo.Lista;


/**
 * A simple {@link Fragment} subclass.
 */
public class Listas extends Fragment {

    private ArrayList<String> nombreListas;
    private Iterador<Lista> listaListas;

    public Listas() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_listas, container, false);

        getActivity().setTitle("Listas");

        RecyclerView recyclerListas = view.findViewById(R.id.RecyclerIdListas);
        FloatingActionButton btnNuevaLista = view.findViewById(R.id.btnNuevaLista);

        llenarListas();

        AdapterListas adapter = new AdapterListas(nombreListas);

        recyclerListas.setAdapter(adapter);

        recyclerListas.setItemAnimator(new DefaultItemAnimator());

        recyclerListas.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();

                bundle.putString("idLista", String.valueOf(listaListas.get(position).getId()));

                ListaHabitual listaHabitual = new ListaHabitual();
                listaHabitual.setArguments(bundle);

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, listaHabitual);
                ft.commit();
            }
        }));

        recyclerListas.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerListas.getContext(),
                ((LinearLayoutManager) recyclerListas.getLayoutManager()).getOrientation());
        recyclerListas.addItemDecoration(dividerItemDecoration);

        btnNuevaLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();

                bundle.putString("fragmentAnterior", "Listas");

                NuevaLista nuevaLista = new NuevaLista();
                nuevaLista.setArguments(bundle);

                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.frame, nuevaLista);
                fr.commit();
            }
        });
        return view;
    }

    private void llenarListas() {
        nombreListas = new ArrayList<>();

        ListaDAO listaDAO = new ListaDAO(getActivity().getApplicationContext());

        listaListas = listaDAO.getListaList();

        while (listaListas.hasNext()) {
            nombreListas.add(listaListas.next().getNombre());
        }
    }
}
