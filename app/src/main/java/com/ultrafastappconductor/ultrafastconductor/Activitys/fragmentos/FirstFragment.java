  package com.ultrafastappconductor.ultrafastconductor.Activitys.fragmentos;

  import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesAceptadasFrakment;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesCanceladasFrakment;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.SolicitudesDeUsuariosFrakmen;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.ValidarFecha;
import com.ultrafastappconductor.ultrafastconductor.adapter.AdapterRecientes;

import java.util.ArrayList;
import java.util.List;


public class FirstFragment extends Fragment {


    ViajesPubliProvider viajesPubliProvider;
    public ValidarFecha validarFecha;

    private FragmentActivity myContext;
    Button bntpublicar;
    private View view;
    private View vieww;

    private Context context;
    RecyclerView rv;
    List<ViajesPublicados> coches;
    AdapterRecientes adapterr;
    TabLayout tabLayout;
    ViewPager viewPager;

    Authprovider authprovider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_first,container, false);
        FirstFragment f = new FirstFragment();
        rv=view.findViewById(R.id.reci);
        tabLayout=view.findViewById(R.id.table);
        viewPager=view.findViewById(R.id.view_pague);
        ArrayList<String> arrayList=new ArrayList<>();

        tabLayout.setupWithViewPager(viewPager);
        prepareViewPague(viewPager,arrayList);

        return view;
    }


    private void prepareViewPague(ViewPager viewPager,ArrayList<String> arrayList) {
        //iniciar el mainfragment
        MainAdapter adapter=new MainAdapter(getActivity().getSupportFragmentManager()  );
        adapter.addFragment(new SolicitudesDeUsuariosFrakmen(),"Pendientes");
        adapter.addFragment(new SolicitudesAceptadasFrakment(),"Aceptadas");
        adapter.addFragment(new SolicitudesCanceladasFrakment(),"Canceladas");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        validarFecha = new ValidarFecha();
        authprovider=new Authprovider();
        viajesPubliProvider = new ViajesPubliProvider("ViajesPublicados");

      /*  viajesPubliProvider.getViajestodos().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    String Dato = dataSnapshot.child("fechavali").getValue().toString();
                    String id = dataSnapshot.child("idUser").getValue().toString();
                    Log.d("nuevo",id);
                    Log.d("nuevo",authprovider.getid());

                    if (!validarFecha.validarfecha(Dato)&&!id.equals(authprovider.getid()))
                    {

                        viajesPubliProvider = new ViajesPubliProvider("ViajesCaducados");
                        String origen = dataSnapshot.child("origen").getValue().toString();
                        String destino = dataSnapshot.child("destino").getValue().toString();
                        String fecha = dataSnapshot.child("fecha").getValue().toString();
                        String hora = dataSnapshot.child("hora").getValue().toString();
                        String idUser = dataSnapshot.child("idUser").getValue().toString();
                        String idViajes = dataSnapshot.child("idViajes").getValue().toString();
                        String seo = dataSnapshot.child("seo").getValue().toString();
                        String fechaactual = dataSnapshot.child("fechaactual").getValue().toString();
                        String comentario = dataSnapshot.child("comentario").getValue().toString();
                        String peso = dataSnapshot.child("peso").getValue().toString();
                        String cantidadentregas = dataSnapshot.child("cantidadentregas").getValue().toString();

                        viajesPubliProvider.createcaducados(validarFecha.viajesCaducadoss(origen,destino,fecha,hora,idUser,idViajes,seo,fechaactual,comentario,peso,cantidadentregas)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                viajesPubliProvider = new ViajesPubliProvider("ViajesPublicados");
                               viajesPubliProvider.delete(idViajes);

                            }
                        });
                    }else
                    {
                      //  vieww.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

        authprovider = new Authprovider();
        Query query = FirebaseDatabase.getInstance().getReference().child("ViajesPublicados").orderByChild("peso").limitToLast(5);
        FirebaseRecyclerOptions<ViajesPublicados> options=new FirebaseRecyclerOptions.Builder<ViajesPublicados>()
                .setQuery(query,ViajesPublicados.class).build();
        adapterr=new AdapterRecientes(options, getContext());
//        rv.setAdapter(adapterr);
       // adapterr.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapterr.stopListening();
    }
    private class MainAdapter extends FragmentPagerAdapter {
//iniciamos el arrarlist

        ArrayList<String> arrayList=new ArrayList<>();
        List<Fragment> fragmentslist=new ArrayList<>();

        //cramos el constructor
        public void addFragment(Fragment fragment,String title)
        {
            arrayList.add(title);
            fragmentslist.add(fragment);


        }
        public MainAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            //retornamos la posocion del fragmetn

            return fragmentslist.get(position);
        }

        @Override
        public int getCount() {
            // retornamos el tamaño de la lista

            return fragmentslist.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            //retornamos el array list de posocion,,jjj
            return arrayList.get(position);
        }
    }
}