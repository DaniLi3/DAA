package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.DialogClass;
import com.ultrafastappconductor.ultrafastconductor.adapter.AdapterNotificaciones;
import com.ultrafastappconductor.ultrafastconductor.adapter.adapterviajesencurso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrakmenEncurso#newInstance} factory method to
 * create an instance of this fragment.
 */
public class    FrakmenEncurso extends Fragment {
    RecyclerView recyclerView;
    DialogClass dialogClass;
    List<SolicitudesModel> coches;
    String seo;
    AdapterNotificaciones adapterr;
    ProgressDialog mdialog;
    Authprovider authprovider;
    Context context;
    TextView gonee;
    View vista;
    private adapterviajesencurso madapter;

    public FrakmenEncurso() {
        context=getContext();
        // Required empty public constructor
    }


    public static FrakmenEncurso newInstance(String param1, String param2) {
        FrakmenEncurso fragment = new FrakmenEncurso();

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

        View view= inflater.inflate(R.layout.fragment_frakmen_finalizados, container, false);
        coches=new ArrayList<>();
        recyclerView=view.findViewById(R.id.reci);
        gonee=view.findViewById(R.id.nofun);
        vista=view.findViewById(R.id.verr);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);


        authprovider=new Authprovider();
        // progressDialog.show();
        // seo=getIntent().getStringExtra("seo");


        return view;    } @Override
    public void onStart() {
        super.onStart();
        dialogClass=new DialogClass(getActivity());
        mdialog = new ProgressDialog(getContext());
        mdialog.setTitle("Procesando");
        mdialog.setMessage("Cargando...");
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.show();
        authprovider = new Authprovider();
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesPublicados").orderByChild("estado")
                .equalTo(authprovider.getid()+"_EN CURSO");
        FirebaseRecyclerOptions<ViajesPublicados> options=new FirebaseRecyclerOptions.Builder<ViajesPublicados>()
                .setQuery(query,ViajesPublicados.class).build();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    vista.setVisibility(View.GONE);

                }
                else
                {
                    vista.setVisibility(View.VISIBLE);
                }
                mdialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        madapter=new adapterviajesencurso(options,getContext());
        recyclerView.setAdapter(madapter);
        madapter.startListening();


    }

    @Override
    public void onStop() {
        super.onStop();
        madapter.stopListening();
    }
}