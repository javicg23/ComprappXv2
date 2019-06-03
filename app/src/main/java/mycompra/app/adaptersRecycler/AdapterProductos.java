package mycompra.app.adaptersRecycler;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mycompra.app.R;
import mycompra.app.logica.ControlCaducidad;

public class AdapterProductos extends RecyclerView.Adapter<AdapterProductos.ViewHolderProductos> {

    private ArrayList<String> listDatosProd, listProduct, listCatProd, listCaducidades;

    public AdapterProductos(ArrayList<String> listDatosProd, ArrayList<String> listProduct, ArrayList<String> listCatProd, ArrayList<String> listCaducidades) {
        this.listDatosProd = listDatosProd;
        this.listProduct = listProduct;
        this.listCatProd = listCatProd;
        this.listCaducidades = listCaducidades;
    }

    @Override
    public ViewHolderProductos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productos_list, viewGroup, false);

        return new ViewHolderProductos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProductos viewHolderProductos, int i) {
        viewHolderProductos.asignarDatos(listDatosProd.get(i), listProduct.get(i), listCatProd.get(i), listCaducidades.get(i));
    }

    @Override
    public int getItemCount() {
        return listDatosProd.size();
    }

    public class ViewHolderProductos extends RecyclerView.ViewHolder {

        TextView datosProd;
        TextView productos;
        TextView categoria;


        public ViewHolderProductos(@NonNull View itemView) {
            super(itemView);
            datosProd = itemView.findViewById(R.id.idDatos);
            productos = itemView.findViewById(R.id.idDatoProducto);
            categoria = itemView.findViewById(R.id.idDatoCategoriaProducto);
        }

        public void asignarDatos(String s, String s1, String s2, String caducidades) {

            datosProd.setText(s);
            productos.setText(s1);
            categoria.setText(s2);

            ControlCaducidad.checkCaducidad(caducidades, datosProd, productos, categoria);
        }
    }
}