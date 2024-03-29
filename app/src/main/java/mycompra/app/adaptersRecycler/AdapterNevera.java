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

public class AdapterNevera extends RecyclerView.Adapter<AdapterNevera.ViewHolderNevera> {

    private ArrayList<String> listCantidad, listProd, listCaducidad;

    public AdapterNevera(ArrayList<String> listCantidad, ArrayList<String> listProd, ArrayList<String> listCaducidad) {
        this.listCantidad = listCantidad;
        this.listProd = listProd;
        this.listCaducidad = listCaducidad;
    }

    @Override
    public ViewHolderNevera onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productos_nevera_list, viewGroup, false);

        return new ViewHolderNevera(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderNevera viewHolderNevera, int i) {
        viewHolderNevera.asignarDatos(listCantidad.get(i), listProd.get(i), listCaducidad.get(i));
    }

    @Override
    public int getItemCount() {
        return listProd.size();
    }

    public class ViewHolderNevera extends RecyclerView.ViewHolder {

        TextView cantidad;
        TextView product;
        TextView cad;

        public ViewHolderNevera(@NonNull View itemView) {
            super(itemView);
            cantidad = itemView.findViewById(R.id.idCantidadNevera);
            product = itemView.findViewById(R.id.idDatoProductoNevera);
            cad = itemView.findViewById(R.id.idCaducidadNevera);
        }

        public void asignarDatos(String s, String s1, String s2) {
            cantidad.setText(s);
            product.setText(s1);
            cad.setText(s2);

            ControlCaducidad.setColorCaducidad(s2, cantidad, product, cad);
        }
    }
}