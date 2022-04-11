package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicarHora extends AppCompatActivity {
    CircleImageView circleImageView;
    ImageView down;
    TextView horas;
    String horaa="18",minn="00";
    String fecha;
    Button btnhora;

    private String cityOrigen;
    private String cityDestino;

    String dia;
    Authprovider authprovider;
    DatePickerDialog datePickerDialog;

    ViajesPublicados viajesPublicados;
    ViajesPubliProvider viajesPubliProvider;
    private LatLng originlatlong;
    private LatLng destinolatlong;

    private double mExtraOrigenlat;
    private double mExtraOrigenlong;
    private String mExtraOrigen;

    private double mExtraDestinolat;
    private double mExtraDestinolong;
    private String mExtraDestino;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_hora);


        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");
        authprovider = new Authprovider();

        mExtraOrigen =getIntent().getStringExtra("Origen");
        mExtraOrigenlat = getIntent().getDoubleExtra("OrigenLat",0);
        mExtraOrigenlong = getIntent().getDoubleExtra("OrigenLog",0);
        mExtraDestino =getIntent().getStringExtra("Destino");
        mExtraDestinolat = getIntent().getDoubleExtra("DestinoLat",0);
        mExtraDestinolong = getIntent().getDoubleExtra("DestinoLog",0);
        fecha =getIntent().getStringExtra("Fecha");
        dia=getIntent().getStringExtra("Dia");

        cityOrigen=getIntent().getStringExtra("cityorigen");
        cityDestino=getIntent().getStringExtra("citydestino");





        circleImageView=findViewById(R.id.Cir);
        down = findViewById(R.id.down);
        btnhora = findViewById(R.id.btnhora);
        horas=findViewById(R.id.horas);
        Bundle bundle = new Bundle();




        String f = dia+" de "+fecha;



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                abrirhora();

            }
        });
        horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirhora();
            }
        })
        ;
        btnhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicarHora.this,PublicarKilosDisponibles.class);
                intent.putExtra("Origen",mExtraOrigen);
                intent.putExtra("OrigenLat",mExtraOrigenlat);
                intent.putExtra("OrigenLog",mExtraOrigenlong);
                intent.putExtra("Destino",mExtraDestino);
                intent.putExtra("DestinoLat",mExtraDestinolat);
                intent.putExtra("DestinoLog",mExtraDestinolong);
                intent.putExtra("Fecha",fecha);
                intent.putExtra("Dia",dia);
                intent.putExtra("hora",horaa+":"+minn);
                intent.putExtra("cityorigen",cityOrigen);
                intent.putExtra("citydestino",cityDestino);

                startActivity(intent);

            }
        });
    }



    public void abrirhora(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(PublicarHora.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                horaa= String.valueOf(hourOfDay);
                minn= String.valueOf(minute);
                horas.setText(hourOfDay + ":"+minute);

            }
        },hora,min,true);


        timePickerDialog.show();


    }


}