package com.ultrafastappconductor.ultrafastconductor.Receiber;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastappconductor.ultrafastconductor.Activitys.MenuActivity;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesCanceladasmodel;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesModel;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.SolicitudesCanceladasprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.SolicitudesProvider;

public class AceptarNotificacion extends BroadcastReceiver {

    private SolicitudesProvider solicitudesProvider;
    private SolicitudesModel solicitudesModel;
    private Authprovider authprovider;
    private SolicitudesCanceladasprovider solicitudesCanceladasprovider;




    private Authprovider mAuthProvider;

    String id;

    @Override
    public void onReceive(Context context, Intent intent) {

        solicitudesProvider=new SolicitudesProvider();
        mAuthProvider = new Authprovider();



            id = intent.getExtras().getString("idnoty");
       // obtenerDatos(id);



          //  solicitudesProvider.updateStatus(id, "aceptado");

            Intent intent1=new Intent(context, MenuActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
            intent1.setAction(Intent.ACTION_RUN);
          //  intent1.putExtra("idCliente",idClient);
           // intent1.putExtra("precio",precio);
            context.startActivity(intent1);
          // obtenerDatos(id);





        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(2);




    }


    private void obtenerDatos(String idd) {
        solicitudesProvider=new SolicitudesProvider();

        solicitudesProvider.getViajes(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    solicitudesModel = new SolicitudesModel();

                    String idd = solicitudesModel.getIdchofer();
                    String status = solicitudesModel.getStatus();
                    String idcliente = solicitudesModel.getIdcliente();
                    String origen = solicitudesModel.getOrigen();
                    String destino = solicitudesModel.getDestino();
                    String fechasalida = solicitudesModel.getFechadesalida();
                    String imagen = solicitudesModel.getImage();
                    String idnoty = solicitudesModel.getIdnoty();



                    String precio = solicitudesModel.getPrecio();
                    String personaquerecibe = solicitudesModel.getPersonaquerecibe();
                    String contenidopaquete = solicitudesModel.getContenidopaquete();
                    String dimensiones = solicitudesModel.getDimensiones();
                    String fechadepublicacionviaje=solicitudesModel.getFechadepublicacionviaje();


                    String kiloss = solicitudesModel.getKilos();
                    String seo=solicitudesModel.seo;
                    String fechasolicitud=solicitudesModel.getFechadesolicitud();
                    String notapaquete=solicitudesModel.getNotapaquete();
                    String telpersona=solicitudesModel.getTelpersona();
                    String seocliente=solicitudesModel.getSeocliente();


                    authprovider=new Authprovider();
                    solicitudesCanceladasprovider=new SolicitudesCanceladasprovider("SolicitudesAceptadas");
                    SolicitudesCanceladasmodel modelo = new SolicitudesCanceladasmodel();


                    modelo.setContenidopaquete(contenidopaquete);
                    modelo.setDestino(destino);
                    modelo.setDimensiones(dimensiones);
                    modelo.setFechadepublicacionviaje(fechadepublicacionviaje);
                    modelo.setFechadesolicitud(fechasolicitud);
                    modelo.setIdchofer(idd);
                    modelo.setIdcliente(idcliente);
                    modelo.setIdnoty(id);
                    modelo.setImage(imagen);
                    modelo.setKilos(kiloss);
                    modelo.setNotapaquete(notapaquete);
                    modelo.setOrigen(origen);
                    modelo.setPersonaquerecibe(personaquerecibe);
                    modelo.setPrecio(precio);
                    modelo.setSeo(seo);
                    modelo.setSeocliente(idcliente+"_cancelado");
                    modelo.setStatus("cancelado");



                    solicitudesCanceladasprovider.Aceptar(modelo).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            solicitudesCanceladasprovider=new SolicitudesCanceladasprovider("Solicitudes");
                            solicitudesCanceladasprovider.delete(idnoty).addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }
                            });
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });





    }



}
