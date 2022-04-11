package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.Detalles;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PerfilUsuariodeSolicitud;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesCanceladasmodel;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.SolicitudesCanceladasprovider;
import com.ultrafastappconductor.ultrafastconductor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetallesViajeAceptado extends AppCompatActivity {
    CircleImageView circleImageView;
    CircleImageView bak;
    Button aceptar;
    Button cancelar;
    Authprovider authprovider;
    private String mExtraId;

    TextView origen,nombre,comentario,destino,contenidopaquetee;
    TextView califi;
    TextView fecha;
    TextView Estado;
    TextView dimensiones;
    TextView peso;
    TextView precio;
    TextView personarecibe;


    String telefono;
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
    String idchofer;

    String imagen;

    String dimensioness;
    String pesoo;
    String calificacion;
    String estado;
    ImageView imagenpaquete;

    String imagencliente,fecharegistrocliente,descripcioncliente,fechapublicacionviaje;

    View veruser;
    SolicitudesCanceladasprovider solicitudesProvider;
    View AlertaFaltadePago;
    View Mostrarcontactar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        View llamar;
        super.onCreate(savedInstanceState);
        Context context = DetallesViajeAceptado.this;
        setContentView(R.layout.activity_detalles_viaje_aceptado);
        llamar=findViewById(R.id.llamar);
        circleImageView=findViewById(R.id.imageViewPro);
        bak=findViewById(R.id.CirculeImageback);
        Estado=findViewById(R.id.txtestado);

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
        califi=findViewById(R.id.cali);
        AlertaFaltadePago=findViewById(R.id.solicitarpagolayout);
        Mostrarcontactar=findViewById(R.id.vercontactar);

        precio=findViewById(R.id.txtprecioo);
        imagenpaquete=findViewById(R.id.imagenpaquete);
        dimensiones=findViewById(R.id.txtdimensaiones);
        authprovider=new Authprovider();
        solicitudesProvider=new SolicitudesCanceladasprovider("SolicitudesCanceladas");
        mExtraId = getIntent().getStringExtra("idHistoryBooking");
        idnoty = getIntent().getStringExtra("idnoty");
        idcliente = getIntent().getStringExtra("idcliente");
        idchofer = getIntent().getStringExtra("id");
        personaquerecibe = getIntent().getStringExtra("personaquerecibe");
        telpersonarecibe = getIntent().getStringExtra("telpersonarecibe");
        notapaquete = getIntent().getStringExtra("notapaquete");
        contenidopaquete = getIntent().getStringExtra("contenidopaquete");
        dimensioness = getIntent().getStringExtra("dimensiones");
        pesoo = getIntent().getStringExtra("kilos");
        origenn = getIntent().getStringExtra("origen");
        destinoo = getIntent().getStringExtra("destino");
        fechasalida = getIntent().getStringExtra("fechasalida");
        fechasolicitud = getIntent().getStringExtra("fechasolicitud");
        imagen = getIntent().getStringExtra("imagen");
        calificacion=getIntent().getStringExtra("calificacion");
        estado=getIntent().getStringExtra("estado");
        precioo = getIntent().getStringExtra("precio");
        nombree = getIntent().getStringExtra("nombre");
        imagencliente = getIntent().getStringExtra("imagencliente");
        fecharegistrocliente = getIntent().getStringExtra("fecharegistrocliente");
        descripcioncliente = getIntent().getStringExtra("descripcioncliente");
        telefono = getIntent().getStringExtra("telefono");
        fechapublicacionviaje = getIntent().getStringExtra("fechapublicacionviaje");
        Estado.setTextColor(Color.RED);
        if (estado.equals("PAGADO")||estado.equals("PAGADO_CURSANDO"))
        {
            AlertaFaltadePago.setVisibility(View.INVISIBLE);
            Mostrarcontactar.setVisibility(View.VISIBLE);
            Estado.setTextColor(Color.GREEN);
        }
        destino.setText(destinoo);
        califi.setText(calificacion);
        fecha.setText(fechasalida);
        origen.setText(origenn);
        precio.setText("MXN $"+precioo);
        peso.setText(pesoo+"kg");
        nombre.setText(nombree);
        comentario.setText(notapaquete);
        dimensiones.setText(dimensioness);
        contenidopaquetee.setText(contenidopaquete);
        personarecibe.setText(personaquerecibe);
        Estado.setText(estado);


        Picasso.with(DetallesViajeAceptado.this).load(imagen).into(imagenpaquete);
        if (imagencliente!=null)
        {
            Picasso.with(DetallesViajeAceptado.this).load(imagencliente).into(clienteimage);
        }

        veruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DetallesViajeAceptado.this, PerfilUsuariodeSolicitud.class);
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

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelarsoli();
            }
        });

        llamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String dial = "tel:" + telefono;
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));

            }
        });
        imagenpaquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog(imagen);
            }
        });
    }

    private void cancelarsoli() {

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
            modelo.setNotapaquete(notapaquete);
            modelo.setOrigen(origenn);
            modelo.setPersonaquerecibe(personaquerecibe);
            modelo.setPersonaquerecibe(telpersonarecibe);
            modelo.setPrecio(precioo);
            modelo.setSeo(authprovider.getid()+"_cancelado");
            modelo.setSeocliente(idcliente+"_cancelado");
            modelo.setStatus("cancelado");
        solicitudesProvider.Cancelar(modelo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                SolicitudesCanceladasprovider solicitudesCanceladasprovider=new SolicitudesCanceladasprovider("SolicitudesAceptadas");
                solicitudesCanceladasprovider.delete(idnoty);
                Toast.makeText(DetallesViajeAceptado.this, "Se rechazo la solicitud", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }
    public void mostrarDialog(String ima)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(DetallesViajeAceptado.this);

        LayoutInflater inflater=getLayoutInflater();
        ImageView imageView;

        View view=inflater.inflate(R.layout.dialog_mostrar_foto,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        imageView=view.findViewById(R.id.imagenpaquete);
        Picasso.with(DetallesViajeAceptado.this).load(ima).into(imageView);



    }
}