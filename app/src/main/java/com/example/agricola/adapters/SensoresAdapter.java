package com.example.agricola.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.agricola.R;
import com.example.agricola.model.Sensor;

import java.util.List;

public class SensoresAdapter extends RecyclerView.Adapter<SensoresAdapter.ViewHolder> {

    private List<Sensor> sensores;

    public SensoresAdapter(List<Sensor> sensores){
        this.sensores = sensores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item_sensor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SensoresAdapter.ViewHolder holder, int position) {
        holder.getTextViewNombre().setText(sensores.get(position).getNombre());
        holder.getTextViewDescripcion().setText(sensores.get(position).getDescripcion());
        holder.getTextViewIdeal().setText(sensores.get(position).getIdealString());
        holder.getTextViewTipo().setText(sensores.get(position).getTipo().toString());
        holder.getTextViewUbicacion().setText(sensores.get(position).getUbicacion().toString());
    }

    @Override
    public int getItemCount() {
        return sensores.size();
    }

    public void setSensores(List<Sensor> sensores) {
        this.sensores = sensores;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewNombre;
        private TextView textViewDescripcion;
        private TextView textViewIdeal;
        private TextView textViewTipo;
        private TextView textViewUbicacion;

        public ViewHolder(View view){
            super(view);
            textViewNombre = view.findViewById(R.id.textViewNombre);
            textViewDescripcion = view.findViewById(R.id.textViewDescripcion);
            textViewIdeal = view.findViewById(R.id.textViewIdeal);
            textViewTipo = view.findViewById(R.id.textViewTipo);
            textViewUbicacion = view.findViewById(R.id.textViewUbicacion);
        }

        public TextView getTextViewNombre() {
            return textViewNombre;
        }

        public TextView getTextViewDescripcion() {
            return textViewDescripcion;
        }

        public TextView getTextViewIdeal() {
            return textViewIdeal;
        }

        public TextView getTextViewTipo() {
            return textViewTipo;
        }

        public TextView getTextViewUbicacion() {
            return textViewUbicacion;
        }
    }
}
