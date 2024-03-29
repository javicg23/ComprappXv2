package mycompra.app.controlador;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mycompra.app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Inventarios extends Fragment {


    public Inventarios() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_inventario, container, false);

        getActivity().setTitle("Inventarios");

        Button btnNevera = vista.findViewById(R.id.buttonNevera);
        Button btnCongelador = vista.findViewById(R.id.buttonCongelador);
        Button btnDespensa = vista.findViewById(R.id.buttonDespensa);

        btnNevera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Nevera());
                ft.commit();
            }
        });

        btnCongelador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Congelador());
                ft.commit();
            }
        });

        btnDespensa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Despensa());
                ft.commit();
            }
        });


        return vista;
    }

}
