package mycompra.app.adaptersRecycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import mycompra.app.R;

public class AdapterListaHabitual extends RecyclerView.Adapter<AdapterListaHabitual.ViewHolderListaHabitual> {

    ArrayList<String> listCheckBox;

    public AdapterListaHabitual(ArrayList<String> listCheckBox) {
        this.listCheckBox = listCheckBox;
    }

    @NonNull
    @Override
    public ViewHolderListaHabitual onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.productos_listahabitual, null, false);
        return new ViewHolderListaHabitual(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderListaHabitual viewHolderListaHabitual, int i) {
        viewHolderListaHabitual.asignarDatos(listCheckBox.get(i));
    }

    @Override
    public int getItemCount() {
        return listCheckBox.size();
    }

    public class ViewHolderListaHabitual extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolderListaHabitual(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.idCheckBoxProd);
        }

        public void asignarDatos(String checkBoxx) {
            checkBox.setText(checkBoxx);
        }
    }
}
