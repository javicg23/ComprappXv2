package mycompra.app.adaptersRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mycompra.app.R;
import mycompra.app.logica.ControlCaducidad;

public class AdapterCongelador extends RecyclerView.Adapter<AdapterCongelador.ViewHolderCongelador> {

    private ArrayList<String> listCantidadC, listProdC, listCaducidadC;

    public AdapterCongelador(ArrayList<String> listCantidadC, ArrayList<String> listProdC, ArrayList<String> listCaducidadC) {
        this.listCantidadC = listCantidadC;
        this.listProdC = listProdC;
        this.listCaducidadC = listCaducidadC;
    }

    @Override
    public ViewHolderCongelador onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productos_congelador_list, viewGroup, false);
        return new ViewHolderCongelador(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCongelador viewHolderCongelador, int i) {
        viewHolderCongelador.asignarDatos(listCantidadC.get(i), listProdC.get(i), listCaducidadC.get(i));
    }

    @Override
    public int getItemCount() {
        return listCaducidadC.size();
    }

    public class ViewHolderCongelador extends RecyclerView.ViewHolder {

        TextView cantidadC;
        TextView productC;
        TextView cadC;

        public ViewHolderCongelador(@NonNull View itemView) {
            super(itemView);
            cantidadC = itemView.findViewById(R.id.idCantidadCongelador);
            productC = itemView.findViewById(R.id.idDatoProductoCongelador);
            cadC = itemView.findViewById(R.id.idCaducidadCongelador);
        }

        public void asignarDatos(String s, String s1, String s2) {
            cantidadC.setText(s);
            productC.setText(s1);
            cadC.setText(s2);

            ControlCaducidad.setColorCaducidad(s2, cantidadC, productC, cadC);
        }
    }
}