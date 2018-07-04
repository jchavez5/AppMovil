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
 * Created by Josechavez on 29/06/2018.
 */

public class ClientesAdaptador extends RecyclerView.Adapter<ClientesAdaptador.clienteViewHolder>  {

    private ArrayList<Clientes> clientes;
    private ClientesAdaptador.OnclienteClickListener clickListener;
    public ClientesAdaptador(ArrayList<Clientes> clientes, ClientesAdaptador.OnclienteClickListener clickListener){
        this.clientes = clientes;
        this.clickListener = clickListener;
    }

    public ClientesAdaptador(ArrayList<Clientes> clientes) {
        this.clientes=clientes;
    }


    @Override
    public ClientesAdaptador.clienteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_consulta_usuarios,parent,false);
        return new ClientesAdaptador.clienteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ClientesAdaptador.clienteViewHolder holder, int position) {
        final Clientes p = clientes.get(position);
        holder.cedula.setText(p.getCedula());
        holder.nombre.setText(p.getNombre());
        holder.telefono.setText(p.getTelefono());
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
        private TextView cedula;
        private TextView nombre;
        private TextView telefono;
        private View v;

        public clienteViewHolder(View itemView){
            super(itemView);
            v = itemView;
            cedula = v.findViewById(R.id.lblCedula);
            nombre = v.findViewById(R.id.lblNombre);
            telefono=v.findViewById(R.id.lblTelefono);

        }

    }

    public interface OnclienteClickListener{
        void onclienteClick(Clientes p);
    }
}
