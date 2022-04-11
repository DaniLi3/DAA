package com.ultrafastappconductor.ultrafastconductor.Activitys.fragmentos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapSolicitudes;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados.TapViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesModel;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.DialogClass;
import com.ultrafastappconductor.ultrafastconductor.adapter.AdapterNotificaciones;
import com.ultrafastappconductor.ultrafastconductor.adapter.adapterviajespublicados;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FrakmenPrincipal#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrakmenPrincipal extends Fragment {
    RecyclerView recyclerView;
    RecyclerView recyclerView2;
    List<SolicitudesModel> coches;
    ProgressDialog progressDialog;
    Authprovider authprovider;
    TextView gonee;
    Button publicar;
    View vertodospublicados,vertodossolicitudes;
    View vista;
    View vista2;
    private adapterviajespublicados madapter;
    private AdapterNotificaciones madapter2;
    DialogClass dialogClass;

    public FrakmenPrincipal() {
        // Required empty public constructor
    }


    public static FrakmenPrincipal newInstance(String param1, String param2) {
        FrakmenPrincipal fragment = new FrakmenPrincipal();


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
        View view=  inflater.inflate(R.layout.fragment_frakmen_principal, container, false);
        coches=new ArrayList<>();
        recyclerView=view.findViewById(R.id.reci);
        recyclerView2=view.findViewById(R.id.reci2);
        gonee=view.findViewById(R.id.nofun);
        vista=view.findViewById(R.id.verr);
        vista2=view.findViewById(R.id.verr2);
        publicar=view.findViewById(R.id.btnpublicar);
        vertodospublicados=view.findViewById(R.id.vertodospublicados);
        vertodossolicitudes=view.findViewById(R.id.vertodossolicitudes);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        authprovider=new Authprovider();

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getContext(), PublicarViajeOrigen.class);
               // startActivity(intent);
                double km=100;
                double pesoenkg=100;
                double anchopaquete=25;
                double longitudpaquete=25;
                double alturapaquete=25;
                double pesovolumetro=0;
                double pkdre=0;

                pesovolumetro=((anchopaquete*longitudpaquete*alturapaquete)/5000);
                pkdre=(2.15*(km))+(2.45*(pesovolumetro)+6.5);

                Log.d("peso", String.valueOf(pesovolumetro));
                Log.d("peso", String.valueOf(pkdre));
            }
        });
        vertodospublicados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TapViajesPublicados.class);
                startActivity(intent);
            }
        });
        vertodossolicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TapSolicitudes.class);
                startActivity(intent);
            }
        });

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        mostrarpublicados();
        mostrarsolicitudes();


    }
    public void mostrarpublicados()
    {
        dialogClass=new DialogClass(getActivity());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Procesando");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        authprovider = new Authprovider();
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("ViajesPublicados").orderByChild("estado").limitToLast(2)
                .equalTo(authprovider.getid()+"_PUBLICADO");
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
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        madapter=new adapterviajespublicados(options,getContext());
        recyclerView.setAdapter(madapter);
        madapter.startListening();

    }
    public void mostrarsolicitudes()
    {
        Query query= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Solicitudes").orderByChild("seo").limitToLast(2)
                .equalTo(authprovider.getid()+"_creado");
        FirebaseRecyclerOptions<SolicitudesModel> options=new FirebaseRecyclerOptions.Builder<SolicitudesModel>()
                .setQuery(query,SolicitudesModel.class).build();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                   // progressDialog.dismiss();
                    vista2.setVisibility(View.GONE);
                }
                else
                {

                    vista2.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        madapter2=new AdapterNotificaciones(options,getContext(),"no");
        recyclerView2.setAdapter(madapter2);
        madapter2.startListening();

    }
}