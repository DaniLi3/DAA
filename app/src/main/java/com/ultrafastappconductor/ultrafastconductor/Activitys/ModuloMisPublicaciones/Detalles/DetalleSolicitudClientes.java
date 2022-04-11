package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.Detalles;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PerfilUsuariodeSolicitud;
import com.ultrafastappconductor.ultrafastconductor.Models.FCMBody;
import com.ultrafastappconductor.ultrafastconductor.Models.IFCMResponse;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesCanceladasmodel;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.NotificationProvider;
import com.ultrafastappconductor.ultrafastconductor.Providers.SolicitudesCanceladasprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.TokenProvider;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class    DetalleSolicitudClientes extends AppCompatActivity {
    CircleImageView circleImageView;
    CircleImageView bak;
    Button aceptar;
    Button cancelar;
    Authprovider authprovider;
    private String mExtraId;
    private ProgressDialog mdialog;
    TextView origen,nombre,comentario,destino,contenidopaquetee;
    TextView detalles;
    TextView fecha;
    TextView dimensiones;
    TextView peso;
    TextView precio;
    TextView personarecibe;
    private NotificationProvider mnotificationProvider;
    private TokenProvider tokenProvider;
    String status;
    CircleImageView clienteimage;
    String idnoty;
    String idcliente;
    String personaquerecibe;
    String telpersonarecibe;
    String notapaquete;
    String contenidopaquete;
    String origenn;
    String destinoo;
    String fechasalida;
    String fechasolicitud;
    String precioo;
    String nombree;
    String modelo;
    String marca;
    String matricula;
    String viajespubli;
    String viajesconclu;

    String fechavali;
    String horasalida;

    String calificacion;
    String fechapublicacionviaje;


    String imagen;

    String dimensioness;
    String pesoo;
    String destinoLat;
    String destinoLog;
    String contenido;
    String costopaquete;
    String idviaje;
    ImageView imagenpaquete;
    View botones;

    String imagencliente,fecharegistrocliente,descripcioncliente;

    View veruser;
    double destinolat;
    double destinolog;
    double origenlat;
    double origenlog;

    SolicitudesCanceladasprovider solicitudesProviderCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_solicitud);

        circleImageView=findViewById(R.id.imageViewPro);
        bak=findViewById(R.id.CirculeImageback);
        aceptar=findViewById(R.id.soliaceptar);
        cancelar=findViewById(R.id.solicancelar);
        veruser=findViewById(R.id.veruser);
        nombre=findViewById(R.id.txtnombre);
        origen=findViewById(R.id.txtdetallesorigen);
        destino=findViewById(R.id.txtdetallesdestino);
        fecha=findViewById(R.id.txtdetallesfecha);
        peso=findViewById(R.id.txtkilos);
        comentario=findViewById(R.id.txtcomenario);
        contenidopaquetee=findViewById(R.id.txtcontenido);
        clienteimage=findViewById(R.id.imageuser);
        personarecibe=findViewById(R.id.txtpersonarecibe);
        botones=findViewById(R.id.botones);
        mnotificationProvider = new NotificationProvider();
        mdialog = new ProgressDialog(this);
        precio=findViewById(R.id.txtprecioo);
        imagenpaquete=findViewById(R.id.imagenpaquete);
        dimensiones=findViewById(R.id.txtdimensaiones);

        tokenProvider=new TokenProvider();


        authprovider=new Authprovider();
        solicitudesProviderCancel=new SolicitudesCanceladasprovider("SolicitudesAceptadas");
        mExtraId = getIntent().getStringExtra("idHistoryBooking");
        idnoty = getIntent().getStringExtra("idnoty");
        idcliente = getIntent().getStringExtra("idcliente");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        notapaquete = getIntent().getStringExtra("notapaquete");
        contenidopaquete = getIntent().getStringExtra("contenidopaquete");
        dimensioness = getIntent().getStringExtra("dimensiones");
        pesoo = getIntent().getStringExtra("kilos");
        origenn = getIntent().getStringExtra("origen");
        status = getIntent().getStringExtra("status");
        destinoo = getIntent().getStringExtra("destino");
        fechasalida = getIntent().getStringExtra("fechasalida");
        fechasolicitud = getIntent().getStringExtra("fechasolicitud");
        imagen = getIntent().getStringExtra("imagen");
        calificacion=getIntent().getStringExtra("calificacion");
        fechapublicacionviaje=getIntent().getStringExtra("fechadepublicacionviaje");
        costopaquete=getIntent().getStringExtra("costopaquete");

        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        telpersonarecibe = getIntent().getStringExtra("telpersonarecibe");
        precioo = getIntent().getStringExtra("precio");
        nombree = getIntent().getStringExtra("nombre");
        imagencliente = getIntent().getStringExtra("imagencliente");
        fecharegistrocliente = getIntent().getStringExtra("fecharegistrocliente");
        descripcioncliente = getIntent().getStringExtra("descripcioncliente");
        fechavali = getIntent().getStringExtra("fechavalidar");
        horasalida = getIntent().getStringExtra("horasalida");
        idviaje = getIntent().getStringExtra("idviaje");
        destinolat = getIntent().getDoubleExtra("destinolat",0);
        destinolog = getIntent().getDoubleExtra("destinolog",0);
        origenlat = getIntent().getDoubleExtra("origenlat",0);
        origenlog = getIntent().getDoubleExtra("origenlog",0);



        if (status.equals("cancelado"))
        {
            botones.setVisibility(View.GONE);
        }




        destino.setText(destinoo);
        fecha.setText(fechasalida);
        origen.setText(origenn);
        precio.setText("MXN $"+precioo);
        peso.setText(pesoo+" kg");
        nombre.setText(nombree);
        comentario.setText(notapaquete);

        dimensiones.setText(dimensioness);
        contenidopaquetee.setText(contenidopaquete);
        personarecibe.setText(personaquerecibe);

        Picasso.with(DetalleSolicitudClientes.this).load(imagen).into(imagenpaquete);
        if (imagencliente!=null)
        {
            Picasso.with(DetalleSolicitudClientes.this).load(imagencliente).into(clienteimage);
        }

        veruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetalleSolicitudClientes.this, PerfilUsuariodeSolicitud.class);
                intent.putExtra("usuario",nombree);
                intent.putExtra("descripcion",descripcioncliente);
                intent.putExtra("fecha",fecharegistrocliente);
                intent.putExtra("imagen",imagencliente);
                intent.putExtra("calificacion",calificacion);
                Log.d("jacob",calificacion);

                startActivity(intent);
            }
        });
        bak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aceptarsoli();

            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarsoli();
            }
        });
        imagenpaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog(imagen);
            }
        });
    }

    private void aceptarsoli() {
        SendNotificationAceptada("","");

    }

    private void cancelarsoli() {
        SendNotification("","");



    }

    private void SendNotification(final String time, final String dista) {
            mdialog.setMessage("Cancelando...");
            mdialog.setCanceledOnTouchOutside(false);
            mdialog.show();
            tokenProvider.getToken(idcliente).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists())
                    {    //handler.removeCallbacks(runnable);
                        //mCounter=20;
                        //handler.postDelayed(runnable,1000);
                        // Log.d("CONDUCTOR","SI ENTRO el metodo: "+mtokenList);

                        String toke=snapshot.child("token").getValue().toString();

                        Map<String,String> map = new HashMap<>();
                        map.put("title","SOLICITUD CANCELADA");
                        map.put("body",
                                "Se rechazo la solicitud");
                        map.put("idCliente",idcliente);
                        map.put("origin","mExtraorigen");
                        map.put("destination","mdestino");
                        map.put("min","time");
                        map.put("distance","dista");
                        map.put("precio","String.valueOf(precio)");
                        map.put("searchById","false"); //ya que no estamos haciendo una busqueda por id
                        FCMBody fcmBody =new FCMBody(toke,"high", map);

                        mnotificationProvider.sendNotification(fcmBody).enqueue(new Callback<IFCMResponse>()
                        {
                            @Override
                            public void onResponse(Call<IFCMResponse> call, Response<IFCMResponse> response) {
                                if (response!=null)
                                {
                                    if (response.body().getSuccess()==1)
                                    {
                                        Log.d("fallos","si se envio correctamente");
                                        //   Toast.makeText(ConfirmarSolicitud.this, "La peticion se envio correctamente", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Log.d("fallos"," no envio correctamente");
                                        Toast.makeText(DetalleSolicitudClientes.this, "La peticion no se pudo enviar", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(DetalleSolicitudClientes.this, "No ubo respuesta del servidor", Toast.LENGTH_SHORT).show();
                                }
                                long timee=0;
                                String datee = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());




                            }
                            @Override
                            public void onFailure(Call<IFCMResponse> call, Throwable t) {
                                Toast.makeText(DetalleSolicitudClientes.this, "Fallo al enviar la notificacion", Toast.LENGTH_SHORT).show();
                                Log.d("fallos","fallo1");

                            }
                        });

                        Cancelar();


                    }
                    else
                    {
                        Toast.makeText(DetalleSolicitudClientes.this, "No existe el token", Toast.LENGTH_SHORT).show();
                        Log.d("fallos","fallo 2");
                    }




                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }
    private void SendNotificationAceptada(final String time, final String dista) {

        mdialog.setMessage("Espere...");
        mdialog.setCanceledOnTouchOutside(false);
        mdialog.show();
        tokenProvider.getToken(idcliente).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {    //handler.removeCallbacks(runnable);
                    //mCounter=20;
                    //handler.postDelayed(runnable,1000);
                    // Log.d("CONDUCTOR","SI ENTRO el metodo: "+mtokenList);

                    String toke=snapshot.child("token").getValue().toString();

                    Map<String,String> map = new HashMap<>();
                    map.put("title","SOLICITUD ACEPTADA");
                    map.put("body",
                            "Se acepto la solicitud");
                    map.put("idCliente",idcliente);
                    map.put("origin","mExtraorigen");
                    map.put("destination","mdestino");
                    map.put("min","time");
                    map.put("distance","dista");
                    map.put("precio","String.valueOf(precio)");
                    map.put("searchById","false"); //ya que no estamos haciendo una busqueda por id
                    FCMBody fcmBody =new FCMBody(toke,"high", map);

                    mnotificationProvider.sendNotification(fcmBody).enqueue(new Callback<IFCMResponse>()
                    {
                        @Override
                        public void onResponse(Call<IFCMResponse> call, Response<IFCMResponse> response) {
                            if (response!=null)
                            {
                                if (response.body().getSuccess()==1)
                                {
                                    Log.d("fallos","si se envio correctamente");
                                    //   Toast.makeText(ConfirmarSolicitud.this, "La peticion se envio correctamente", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Log.d("fallos"," no envio correctamente");
                                    Toast.makeText(DetalleSolicitudClientes.this, "La peticion no se pudo enviar", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(DetalleSolicitudClientes.this, "No ubo respuesta del servidor", Toast.LENGTH_SHORT).show();
                            }
                            long timee=0;
                            String datee = (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString());




                        }
                        @Override
                        public void onFailure(Call<IFCMResponse> call, Throwable t) {
                            Toast.makeText(DetalleSolicitudClientes.this, "Fallo al enviar la notificacion", Toast.LENGTH_SHORT).show();
                            Log.d("fallos","fallo1");

                        }
                    });

                    Aceptado();


                }
                else
                {
                    Toast.makeText(DetalleSolicitudClientes.this, "No existe el token", Toast.LENGTH_SHORT).show();
                    Log.d("fallos","fallo 2");
                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void Cancelar() {
        solicitudesProviderCancel=new SolicitudesCanceladasprovider("SolicitudesCanceladas");
        SolicitudesCanceladasmodel modelo = new SolicitudesCanceladasmodel();

        modelo.setContenidopaquete(contenidopaquete);
        modelo.setDestino(destinoo);
        modelo.setDimensiones(dimensioness);
        modelo.setFechadepublicacionviaje(fechapublicacionviaje);
        modelo.setFechadesolicitud(fechasolicitud);
        modelo.setIdchofer(authprovider.getid());
        modelo.setIdcliente(idcliente);
        modelo.setIdnoty(idnoty);
        modelo.setImage(imagen);
        modelo.setKilos(pesoo);
        modelo.setFechadesalida(fechasalida);
        modelo.setNotapaquete(notapaquete);
        modelo.setOrigen(origenn);
        modelo.setPersonaquerecibe(personaquerecibe);
        modelo.setPrecio(precioo);
        modelo.setSeo(authprovider.getid()+"_cancelado");
        modelo.setSeocliente(idcliente+"_cancelado");
        modelo.setStatus("cancelado");

        solicitudesProviderCancel.Cancelar(modelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                solicitudesProviderCancel=new SolicitudesCanceladasprovider("Solicitudes");
                solicitudesProviderCancel.delete(idnoty).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetalleSolicitudClientes.this, "Se rechazo la solicitud"+idnoty, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
            }
        });
    }
    private void Aceptado() {

        SolicitudesCanceladasmodel modelo = new SolicitudesCanceladasmodel();
        modelo.setContenidopaquete(contenidopaquete);
        modelo.setDestino(destinoo);
        modelo.setDimensiones(dimensioness);
        modelo.setFechadepublicacionviaje(fechapublicacionviaje);
        modelo.setFechadesolicitud(fechasolicitud);
        modelo.setIdchofer(authprovider.getid());
        modelo.setIdcliente(idcliente);
        modelo.setIdnoty(idnoty);
        modelo.setImage(imagen);
        modelo.setFechadesalida(fechasalida);
        modelo.setKilos(pesoo);
        modelo.setNotapaquete(notapaquete);
        modelo.setOrigen(origenn);
        modelo.setPersonaquerecibe(personaquerecibe);
        modelo.setPersonaquerecibe(personaquerecibe);
        modelo.setPrecio(precioo);
        modelo.setSeo(authprovider.getid()+"_aceptado");
        modelo.setSeocliente(idcliente+"_aceptado");
        modelo.setStatus("aceptado");
        modelo.setEstado("SIN PAGAR");
        modelo.setFechavalidar(fechavali);
        modelo.setHorasalida(horasalida);
        modelo.setCostopaquete(costopaquete);
        modelo.setIdviaje(idviaje);
        modelo.setOrigenLat(origenlat);
        modelo.setOrigenLog(origenlog);
        modelo.setDestinoLatt(destinolat);
        modelo.setDestinoLog(destinolog);

        solicitudesProviderCancel.Aceptar(modelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                solicitudesProviderCancel=new SolicitudesCanceladasprovider("Solicitudes");
                solicitudesProviderCancel.delete(idnoty).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetalleSolicitudClientes.this, "Se acepto la solicitud", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                finish();
            }
        });
    }
    public void mostrarDialog(String ima)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(DetalleSolicitudClientes.this);

        LayoutInflater inflater=getLayoutInflater();
        ImageView imageView;

        View view=inflater.inflate(R.layout.dialog_mostrar_foto,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();


         imageView=view.findViewById(R.id.imagenpaquete);
        Picasso.with(DetalleSolicitudClientes.this).load(ima).into(imageView);





    }
}