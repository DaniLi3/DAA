package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesModel;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.DialogClass;
import com.ultrafastappconductor.ultrafastconductor.adapter.AdapterNotificaciones;

import java.util.ArrayList;
import java.util.List;


public class SolicitudesAceptadasFrakment extends Fragment {
    RecyclerView recyclerView;
    DialogClass dialogClass;
    List<SolicitudesModel> coches;
    String seo;
    AdapterNotificaciones adapterr;
    ProgressDialog progressDialog;
    Authprovider authprovider;
    Context context;
    TextView gonee;
    ProgressDialog mdialog;
    View vista;
    View vertodocontenido;
    private AdapterNotificaciones madapter;



    public SolicitudesAceptadasFrakment() {
        context=getContext();
        // Required empty public constructor
    }


    public static SolicitudesAceptadasFrakment newInstance(String param1, String param2) {
        SolicitudesAceptadasFrakment fragment = new SolicitudesAceptadasFrakment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_frak2, container, false);
        coches=new ArrayList<>();


        recyclerView=view.findViewById(R.id.reci);
       vista=view.findViewById(R.id.verr);
       vertodocontenido=view.findViewById(R.id.vercontenido);
//        rv.setHasFixedSize(true);



        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        // rv.setLayoutManager(new LinearLayoutManager(getContext()));

        authprovider=new Authprovider();

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        // progressDialog.show();
        // seo=getIntent().getStringExtra("seo");


        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        dialogClass=new DialogClass(getActivity());
        mdialog=new ProgressDialog(getContext());
        mdialog.setMessage("cargando..");
        mdialog.setCanceledOnTouchOutside(false);
       // mdialog.show();
        authprovider = new Authprovider();
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("SolicitudesAceptadas").limitToFirst(30).orderByChild("seo")
                .equalTo(authprovider.getid()+"_aceptado");
        FirebaseRecyclerOptions<SolicitudesModel> options=new FirebaseRecyclerOptions.Builder<SolicitudesModel>()
                .setQuery(query,SolicitudesModel.class).build();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {

                    mdialog.dismiss();
                    vista.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "Si existe", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    mdialog.dismiss();
                    vertodocontenido.setBackgroundColor(Color.WHITE);
                   // Toast.makeText(getContext(), "No existe", Toast.LENGTH_SHORT).show();
                    vista.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        madapter=new AdapterNotificaciones(options,getContext(),"no");
        recyclerView.setAdapter(madapter);
        madapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }
}