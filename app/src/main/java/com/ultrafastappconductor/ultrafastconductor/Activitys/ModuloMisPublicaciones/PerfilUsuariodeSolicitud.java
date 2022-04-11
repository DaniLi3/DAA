package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.ultrafastappconductor.ultrafastconductor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilUsuariodeSolicitud extends AppCompatActivity {

    TextView usuario,descripcion,fecha,Cal;
    String usuarioo,descripcionn,fechaa;
    String imagen;
    String Calificacion;
    String valoracion;


    CircleImageView fotoperfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_usuariode_solicitud);

        usuario=findViewById(R.id.username);
        fotoperfil=findViewById(R.id.imageViewPro);
        descripcion=findViewById(R.id.txtdes);
        fecha=findViewById(R.id.txtfe);
        Cal=findViewById(R.id.calificacion);


        usuarioo=getIntent().getStringExtra("usuario");
        descripcionn=getIntent().getStringExtra("descripcion");
        fechaa=getIntent().getStringExtra("fecha");
        valoracion=getIntent().getStringExtra("valoracion");
        imagen=getIntent().getStringExtra("imagen");
        Calificacion=getIntent().getStringExtra("calificacion");

        String f []=fechaa.split(" ");

        usuario.setText(usuarioo);
        descripcion.setText(descripcionn);
        fecha.setText("Usuario desde el "+f[0]);
        Cal.setText(Calificacion);
        if (imagen!=null){

            Picasso.with(PerfilUsuariodeSolicitud.this).load(imagen).into(fotoperfil);
        }




    }
}