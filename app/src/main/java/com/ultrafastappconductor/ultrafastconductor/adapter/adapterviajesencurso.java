package com.ultrafastappconductor.ultrafastconductor.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados.VerViajeenCurso;
import com.ultrafastappconductor.ultrafastconductor.Models.ClienteBookin;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.Choferprovider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.channel.SNTPClient;

import java.util.TimeZone;

public class adapterviajesencurso extends FirebaseRecyclerAdapter<ViajesPublicados, adapterviajesencurso.ViewHolder> {

    Choferprovider choferprovider;
    public Authprovider authprovider;
    ClienteBookin clienteBookin;
    String nombre="";
    String apellidos="";
    String imagencliente;
    String fechaderegistrocliente;
    String descripcioncliente;
    String telefono;
    String fechasistema;
    private Context mcontex;
    private ImageView mimageViewBookin;
    public adapterviajesencurso(@NonNull FirebaseRecyclerOptions<ViajesPublicados> options, Context context) {
        super(options);
        choferprovider=new Choferprovider();
        mcontex=context;
        clienteBookin = new ClienteBookin();
        authprovider=new Authprovider();
        sasa();
    }

    @Override
    protected void onBindViewHolder(@NonNull adapterviajesencurso.ViewHolder holder, int position, @NonNull ViajesPublicados model) {
        final String id=getRef(position).getKey();
        String iduser = model.getIdUser();
        String origen = model.getOrigen();
        String origenlat = model.getOrigenLat();
        String origenlog = model.getOrigenLog();
        String destino = model.getDestino();
        String destinolat = model.getDestinoLat();
        String destinolog = model.getDestinoLog();
        String fechasalida = model.getFecha()   ;
        String hora = model.getHora()   ;
        String fechapublicado = model.getFechaactual();
        String peso = model.getPeso();
        String idviaje = model.getIdViajes();
        String comentario = model.getComentario();
        String cantidaddeentregas = model.getCantidadentregas();
        String cityorigen=model.getCityOrigen();
        String citydestino=model.getCityDestino();
        String paquetesasignados=model.getPaquetesasignados();
       // String idnoty=model.geti();
        String status=model.getStatus();


        holder.origenn.setText(origen);
        holder.ciudadorigen.setText(cityorigen);
        holder.destinoo.setText(destino);
        holder.ciudaddestino.setText(citydestino);
        holder.fecha.setText("Fecha de salida: "+fechapublicado);
        holder.kilos.setText("Peso disponible en el auto: "+peso+" kg");
        holder.asignados.setText("Paquetes asignados: "+paquetesasignados);
        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                     Intent intent=new Intent(mcontex, VerViajeenCurso.class);
                     intent.putExtra("iduser", iduser);
                     intent.putExtra("origen", origen);
                     intent.putExtra("origenlat", origenlat);
                     intent.putExtra("origenlog", origenlog);
                     intent.putExtra("destino", destino);
                     intent.putExtra("destinolat", destinolat);
                     intent.putExtra("destinolog", destinolog);
                     intent.putExtra("fechasalida", fechasalida);
                     intent.putExtra("hora", hora);
                     intent.putExtra("fechapublicado", fechapublicado);
                     intent.putExtra("peso", peso);
                     intent.putExtra("idviaje", idviaje);
                     intent.putExtra("comentario", comentario);
                     intent.putExtra("cantidaddeentregas", cantidaddeentregas);
                     intent.putExtra("status", status);
                     mcontex.startActivity(intent);



            }

        });
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_viajespubli,parent,false);
        return new ViewHolder( view);
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView estado,modelo;
        private TextView origenn,fecha,destinoo;
        private TextView nota;
        private TextView ciudadorigen,ciudaddestino ,asignados,kilos;
        private View mview;
        ImageView imageView;

        public ViewHolder(View view){
            super(view);
            origenn=itemView.findViewById(R.id.txtorigen);
            destinoo=itemView.findViewById(R.id.txtdestino);
            fecha=itemView.findViewById(R.id.cardhora);
            ciudadorigen=itemView.findViewById(R.id.txtciudadorigen);
            ciudaddestino=itemView.findViewById(R.id.txtciudaddestino);
            kilos=itemView.findViewById(R.id.txtpesodis);
            asignados=itemView.findViewById(R.id.txtasignados);
            mview=itemView;
        }

    }


    public void sasa() {
        SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
            @Override
            public void onTimeReceived(String rawDate) {
                fechasistema=rawDate;

            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
            }
        });
    }
}
