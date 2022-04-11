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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.Detalles.DetalleSolicitudClientes;
import com.ultrafastappconductor.ultrafastconductor.Models.ClienteBookin;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesModel;
import com.ultrafastappconductor.ultrafastconductor.Providers.Choferprovider;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.List;

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.CocheviewHolder>{

    public List<SolicitudesModel> cochess;
    Choferprovider choferprovider;
    ImageView mimageViewBookin;
    ClienteBookin clienteBookin;
    public int posicionmarca=0;
    private Context mcontex;

    public SolicitudesAdapter(List<SolicitudesModel> cochess, Context context) {
        this.cochess = cochess;
        choferprovider=new Choferprovider();
        clienteBookin = new ClienteBookin();
        mcontex=context;
    }

    @NonNull
    @Override
    public CocheviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_solicitudes,parent,false);
       CocheviewHolder cocheviewHolder=new CocheviewHolder(view);
       return cocheviewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CocheviewHolder holder, int position) {
        final String id=cochess.get(position).getIdnoty();

        SolicitudesModel solicitudesModel=cochess.get(position);
       // holder.origenn.setText(solicitudesModel.getOrigen());
       // holder.destinoo.setText(solicitudesModel.getDestino());

        choferprovider.getusuario(solicitudesModel.getIdcliente()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {

                    String apellidos =snapshot.child("apellidos").getValue().toString();
                    String nombre =snapshot.child("nombre").getValue().toString();

                    String id =snapshot.child("id").getValue().toString();


                    String image="";
                   if(snapshot.hasChild("image"))
                    {      image=snapshot.child("image").getValue().toString();
                        Log.d("loro id",id +nombre);
                           Picasso.with(mcontex).load(image).into(holder.imageView);
                    }

                    holder.nombre.setText(nombre+" "+apellidos);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.mview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontex, DetalleSolicitudClientes.class);
                intent.putExtra("idHistoryBooking", id);
                mcontex.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cochess.size() ;
    }

    public static class CocheviewHolder extends RecyclerView.ViewHolder{

        TextView marca,modelo;
        private TextView origenn;
        private TextView destinoo;
        private TextView nombre,hora,precio,kilos;
        private View mview;
         ImageView imageView;

        public CocheviewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtnombre);
           // destinoo=itemView.findViewById(R.id.txtdestino);
            origenn=itemView.findViewById(R.id.txtorigen);
            imageView=itemView.findViewById(R.id.imageViewPro);
            mview=itemView;


        }
    }
}
