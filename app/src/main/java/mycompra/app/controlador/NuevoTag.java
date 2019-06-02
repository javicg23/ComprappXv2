package mycompra.app.controlador;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import mycompra.app.R;
import mycompra.app.dao.TagDAO;
import mycompra.app.modelo.Tag;

/**
 * A simple {@link Fragment} subclass.
 */
public class NuevoTag extends Fragment implements AdapterView.OnItemSelectedListener {

    private EditText editTextTag;
    private Spinner spinner;
    private Button btnAtras;
    private Button btnAnyadir;
    private TagDAO tagDAO;
    private ArrayAdapter<CharSequence> adapter;

    public NuevoTag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nuevo_tag, container, false);

        editTextTag = view.findViewById(R.id.editTextNuevoTag);

        spinner = view.findViewById(R.id.spinnerNuevoTag);

        adapter = ArrayAdapter.createFromResource(getActivity().getApplicationContext(),
                R.array.categorias, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        btnAtras = view.findViewById(R.id.btnCancelarNuevoTag);
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame, new Configuracion());
                ft.commit();
            }
        });

        btnAnyadir = view.findViewById(R.id.btnAnyadirNuevoTag);
        btnAnyadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editTextTag.getText().toString().equalsIgnoreCase("")) {
                    tagDAO = new TagDAO(getActivity().getApplicationContext());
                    Tag tag = new Tag();
                    tag.setNombre(String.valueOf(editTextTag.getText()));
                    tag.setIdCategoria(spinner.getSelectedItemPosition() + 1);
                    tagDAO.insert(tag);
                    editTextTag.setText("");
                    Toast.makeText(getActivity().getApplicationContext(), "Tag añadida correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), "Debes rellenar todos los campos correctamente", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
    }

    public void onNothingSelected(AdapterView<?> parent) {
    }
}
