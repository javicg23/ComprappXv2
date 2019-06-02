package mycompra.app.adaptersRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mycompra.app.R;

public class AdapterTickets extends RecyclerView.Adapter<AdapterTickets.ViewHolderTickets> {

    ArrayList<String> listSupermercadoTickets;
    ArrayList<String> listFechaTickets;
    ArrayList<String> listPrecioTickets;

    public AdapterTickets(ArrayList<String> listSupermercadoTickets, ArrayList<String> listFechaTickets, ArrayList<String> listPrecioTickets) {
        this.listSupermercadoTickets = listSupermercadoTickets;
        this.listFechaTickets = listFechaTickets;
        this.listPrecioTickets = listPrecioTickets;
    }

    @Override
    public ViewHolderTickets onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.tickets_list, viewGroup, false);

        return new ViewHolderTickets(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderTickets viewHolderProductos, int i) {
        viewHolderProductos.asignarDatos(listSupermercadoTickets.get(i), listFechaTickets.get(i), listPrecioTickets.get(i));
    }

    @Override
    public int getItemCount() {
        return listSupermercadoTickets.size();
    }

    public class ViewHolderTickets extends RecyclerView.ViewHolder {

        TextView supermercadoTickets;
        TextView fechaTickets;
        TextView precioTickets;

        public ViewHolderTickets(@NonNull View itemView) {
            super(itemView);
            supermercadoTickets = itemView.findViewById(R.id.idSupermercadoTickets);
            fechaTickets = itemView.findViewById(R.id.idFechaTickets);
            precioTickets = itemView.findViewById(R.id.idPrecioTickets);
        }

        public void asignarDatos(String s, String s1, String s2) {
            supermercadoTickets.setText(s);
            fechaTickets.setText(s1);
            precioTickets.setText(s2);
        }
    }
}