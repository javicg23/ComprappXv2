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
public class Configuracion extends Fragment {

    public Configuracion() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_configuracion, container, false);

        getActivity().setTitle("Configuracion");

        Button btnNuevaTag = vista.findViewById(R.id.buttonNuevaTag);
        btnNuevaTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new NuevoTag()).addToBackStack(null);
                ft.commit();
            }
        });

        return vista;
    }

}
