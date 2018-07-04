package com.josechavez.aplicacionconsulta.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.josechavez.aplicacionconsulta.R;
import com.josechavez.aplicacionconsulta.adapter.ClientesAdaptador;
import com.josechavez.aplicacionconsulta.clases.Clientes;
import com.josechavez.aplicacionconsulta.clases.DB;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TabConsulClientesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabConsulClientesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabConsulClientesFragment extends Fragment  implements ClientesAdaptador.OnclienteClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private RecyclerView listaClientes;
    private ArrayList<Clientes> clientes;
    private Intent i;
    private FirebaseAuth firebaseAuth ;

    private OnFragmentInteractionListener mListener;

    public TabConsulClientesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabConsulClientesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TabConsulClientesFragment newInstance(String param1, String param2) {
        TabConsulClientesFragment fragment = new TabConsulClientesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista=inflater.inflate(R.layout.fragment_tab_consul_clientes, container, false);
        clientes=new ArrayList<>();
        listaClientes=(RecyclerView)vista.findViewById(R.id.ReclycleUsuarios);
        listaClientes.setLayoutManager(new LinearLayoutManager(getContext()));

        final ClientesAdaptador adaptador= new ClientesAdaptador(clientes);
        listaClientes.setAdapter(adaptador);

        firebaseAuth =FirebaseAuth.getInstance();
        final String id = firebaseAuth.getCurrentUser().getUid();
        DB.findById(id).child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientes.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Clientes c = snapshot.getValue(Clientes.class);
                        clientes.add(c);
                    }
                }
                adaptador.notifyDataSetChanged();
                DB.setClientes(clientes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }        });
        return vista;
    }

    private void consultarLista() {

        firebaseAuth =FirebaseAuth.getInstance();
        final String id = firebaseAuth.getCurrentUser().getUid();
        DB.findById(id).child("clientes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientes.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Clientes c = snapshot.getValue(Clientes.class);
                        clientes.add(c);
                    }
                }

                DB.setClientes(clientes);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onclienteClick(Clientes p) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
