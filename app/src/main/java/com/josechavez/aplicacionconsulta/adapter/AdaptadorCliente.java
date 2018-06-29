package com.josechavez.aplicacionconsulta.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.josechavez.aplicacionconsulta.R;
import com.josechavez.aplicacionconsulta.clases.Clientes;

import java.util.ArrayList;

/**
 * Created by Josechavez on 27/06/2018.
 */

public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.clienteViewHolder>  {
    private ArrayList<Clientes> clientes;
    private OnclienteClickListener clickListener;
    public AdaptadorCliente(ArrayList<Clientes> clientes, OnclienteClickListener clickListener){
        this.clientes = clientes;
        this.clickListener = clickListener;
    }

    @Override
    public clienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_tabs_consultar_cliente,parent,false);
        return new clienteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(clienteViewHolder holder, int position) {
        final Clientes p = clientes.get(position);
        holder.foto.setImageResource(R.drawable.profile);
        holder.cedula.setText(p.getCedula());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onclienteClick(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public static class clienteViewHolder extends RecyclerView.ViewHolder{
        private ImageView foto;
        private TextView cedula;
        private TextView nombre;
        private TextView barrio;
        private TextView numero_deuda;
        private View v;

        public clienteViewHolder(View itemView){
            super(itemView);
            v = itemView;
            foto = v.findViewById(R.id.imgFoto);
            cedula = v.findViewById(R.id.lblCedula);
            nombre = v.findViewById(R.id.lblNombre);

        }

    }

    public interface OnclienteClickListener{
        void onclienteClick(Clientes p);
    }
}
