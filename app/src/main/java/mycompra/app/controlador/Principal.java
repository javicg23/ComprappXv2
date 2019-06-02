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
public class Principal extends Fragment {


    public Principal() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_principal, container, false);

        Button btnListasPrincipal = vista.findViewById(R.id.btnListPrincipalId);
        btnListasPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Listas()).addToBackStack(null);
                ft.commit();
            }
        });

        Button btnEscanerPrincial = vista.findViewById(R.id.btnEscPrincId);
        btnEscanerPrincial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Escanear()).addToBackStack(null);
                ft.commit();
            }
        });

        Button btnInventarioPrincipal = vista.findViewById(R.id.btnInventPrincId);
        btnInventarioPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Inventarios()).addToBackStack(null);
                ft.commit();
            }
        });

        return vista;
    }

}
