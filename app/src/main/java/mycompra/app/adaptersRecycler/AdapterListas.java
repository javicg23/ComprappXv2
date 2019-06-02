package mycompra.app.adaptersRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mycompra.app.R;

public class AdapterListas extends RecyclerView.Adapter<AdapterListas.ViewHolderListas> {

    ArrayList<String> nombreListas;

    public AdapterListas(ArrayList<String> nombreListas) {
        this.nombreListas = nombreListas;
    }

    @NonNull
    @Override
    public ViewHolderListas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listas_item, null, false);
        return new ViewHolderListas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListas viewHolderListas, int i) {
        viewHolderListas.asignarDatos(nombreListas.get(i));
    }

    @Override
    public int getItemCount() {
        return nombreListas.size();
    }

    public class ViewHolderListas extends RecyclerView.ViewHolder {

        TextView nombre;

        public ViewHolderListas(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.idNombreListas);
        }

        public void asignarDatos(String s) {
            nombre.setText(s);
        }
    }
}
