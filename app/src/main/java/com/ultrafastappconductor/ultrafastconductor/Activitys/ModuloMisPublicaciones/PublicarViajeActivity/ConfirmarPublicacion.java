package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados.TapViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.GeofireProvider;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.FormatoHora;
import com.ultrafastappconductor.ultrafastconductor.channel.SNTPClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfirmarPublicacion extends AppCompatActivity {

    private double mExtraOrigenlat;
    private double mExtraOrigenlong;
    private String mExtraOrigen;
    CircleImageView CirculeImageback;

    private String cityOrigen;
    private String cityDestino;
    private ProgressDialog mdialog;

    GeofireProvider geofireProvider;

    private double mExtraDestinolat;
    private double mExtraDestinolong;
    private String mExtraDestino;
    Authprovider authprovider;
    ViajesPubliProvider viajesPubliProvider;
    private LatLng originlatlong;
    private LatLng destinolatlong;

    private EditText comentario;
    private TextView kiloss;
    private TextView precios;
    private TextView origen;
    private TextView destino;
    private TextView fechaa;
    private TextView fechaactual;
    private TextView horaactu;
    private TextView horasalida;
    private TextView cantidadd;

    private Button confirmar;
    String [] horaactual=new String[2];

    String fecha;
    String kilos,precio;
    String horadesalida;
    String cantidad;
    String fechanormalsalida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_tipo_envio);

        geofireProvider = new GeofireProvider("active_driver");

        kiloss = findViewById(R.id.txtpeso);
        confirmar = findViewById(R.id.botonconfirmar);
        fechaa = findViewById(R.id.txtfecha);
        origen = findViewById(R.id.txtsalida);
        destino = findViewById(R.id.txtdestino);

        fechaactual = findViewById(R.id.txtfechaactual);
        comentario=findViewById(R.id.editTextTextMultiLine);
        horaactu=findViewById(R.id.txthoraactual);
        horasalida=findViewById(R.id.txthora);
        cantidadd=findViewById(R.id.txtcantidad);
        CirculeImageback=findViewById(R.id.CirculeImageback);
        mdialog = new ProgressDialog(this);


        cityOrigen=getIntent().getStringExtra("cityorigen");
        cityDestino=getIntent().getStringExtra("citydestino");
        cantidad=getIntent().getStringExtra("cantidad");

        authprovider = new Authprovider();
        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");

        kilos =getIntent().getStringExtra("kilos");

        horadesalida =getIntent().getStringExtra("hora");

        mExtraOrigen =getIntent().getStringExtra("Origen");
        mExtraOrigenlat = getIntent().getDoubleExtra("OrigenLat",0);
        mExtraOrigenlong = getIntent().getDoubleExtra("OrigenLog",0);

        mExtraDestino =getIntent().getStringExtra("Destino");
        mExtraDestinolat = getIntent().getDoubleExtra("DestinoLat",0);
        mExtraDestinolong = getIntent().getDoubleExtra("DestinoLog",0);

        originlatlong=new LatLng(mExtraOrigenlat,mExtraOrigenlong);
        destinolatlong=new LatLng(mExtraDestinolat,mExtraDestinolong);

        fecha =getIntent().getStringExtra("Fecha");
        fechanormalsalida=getIntent().getStringExtra("Dia");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = simpleDateFormat.format(new Date());
        horaactual =currentDateandTime.split(" ");
        Log.d("loco",kilos);
        Log.d("loco",cityDestino+" es "+cityOrigen);




        cantidadd.setText(cantidad);
        kiloss.setText(kilos+" kg");
        fechaa.setText(fecha);
        origen.setText(mExtraOrigen);
        destino.setText(mExtraDestino);
        fechaactual.setText(horaactual[0]);
        horaactu.setText(horaactual[1]);
        horasalida.setText(horadesalida);


        CirculeImageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String c = comentario.getText().toString();
                if (!c.isEmpty())
                {
                    mdialog.setMessage("Enviando solicitud...");
                    mdialog.setCanceledOnTouchOutside(false);
                    mdialog.show();
                    registrar(c);

                }
                else
                {
                    Toast.makeText(ConfirmarPublicacion.this, "Agrega un comentario al viaje", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void registrar(String comen)
    {
        final String[] datee = new String[2];
        FormatoHora formathora= new FormatoHora();
        SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
            @Override
            public void onTimeReceived(String rawDate) {
                // rawDate -> 2019-11-05T17:51:01+0530
                String dat = rawDate;
               // String cor = UUID.randomUUID().toString();
                String cor = viajesPubliProvider.mDatabase.push().getKey();
                ViajesPublicados viajesPublicados = new ViajesPublicados();
                viajesPublicados.setIdViajes(cor);

                registarCoordenadas(cor);
                viajesPublicados.setIdUser(authprovider.getid());
                viajesPublicados.setOrigen(mExtraOrigen);
                viajesPublicados.setOrigenLat(String.valueOf(mExtraOrigenlat));
                viajesPublicados.setOrigenLog(String.valueOf(mExtraOrigenlong));
                viajesPublicados.setDestinoLat(String.valueOf(mExtraDestinolat));
                viajesPublicados.setDestinoLog(String.valueOf(mExtraDestinolong));
                viajesPublicados.setFecha(fecha+" a las "+horadesalida);
                viajesPublicados.setHoraactual(horaactual[1]);
                viajesPublicados.setHora(horadesalida);
                viajesPublicados.setFechavali(fechanormalsalida);
                viajesPublicados.setFechaactual(dat);
                viajesPublicados.setPeso(kilos);
                viajesPublicados.setPrecio(precio);
                viajesPublicados.setComentario(comen);
                viajesPublicados.setCityOrigen(cityOrigen);
                viajesPublicados.setCityDestino(cityDestino);
                viajesPublicados.setSeo(cityOrigen+"_"+cityDestino);
                viajesPublicados.setDestino(mExtraDestino);
                viajesPublicados.setCantidadentregas(cantidad);
                viajesPublicados.setPaquetesasignados("0");
                viajesPublicados.setEstado(authprovider.getid()+"_PUBLICADO");
                viajesPublicados.setTimestamp(GetTimeStamp(dat));

                viajesPubliProvider.create(viajesPublicados).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(ConfirmarPublicacion.this, "Se publico correctamente", Toast.LENGTH_SHORT).show();
                            mdialog.dismiss();
                            Intent intent = new Intent(ConfirmarPublicacion.this, TapViajesPublicados.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(ConfirmarPublicacion.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                //Toast.makeText(getContext(), "es "+rawDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
            }
        });

    }
    public static long GetTimeStamp(String TimeStampDB) {
        Date fechaConvertida = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            fechaConvertida = dateFormat.parse(TimeStampDB);
        } catch(Exception e) {
            System.out.println("Error occurred"+ e.getMessage());
        }
        return fechaConvertida.getTime();
    }
    public void registarCoordenadas(String idViajes)
    {

        geofireProvider.savelocation(idViajes,originlatlong);

    }
}