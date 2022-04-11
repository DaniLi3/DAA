package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ultrafastappconductor.ultrafastconductor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicarCantidadViajes extends AppCompatActivity {
    CircleImageView circlefinis;

    ImageView menos,mas;
    TextView contador;
    public int con=1;
    String kilosdisponibles;


    private String cityOrigen;
    private String cityDestino;
    Button nex;


    private double mExtraOrigenlat;
    private double mExtraOrigenlong;
    private String mExtraOrigen;

    private double mExtraDestinolat;
    private double mExtraDestinolong;
    private String mExtraDestino;
    String fecha;
    String dia;
    String horadepubli;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_cantidad_viajes);
        mas=findViewById(R.id.btnmas);
        menos=findViewById(R.id.btnmenos);
        contador=findViewById(R.id.txtcontador);
        nex=findViewById(R.id.btnnext);

        mExtraOrigen =getIntent().getStringExtra("Origen");
        mExtraOrigenlat = getIntent().getDoubleExtra("OrigenLat",0);
        mExtraOrigenlong = getIntent().getDoubleExtra("OrigenLog",0);

        mExtraDestino =getIntent().getStringExtra("Destino");
        mExtraDestinolat = getIntent().getDoubleExtra("DestinoLat",0);
        mExtraDestinolong = getIntent().getDoubleExtra("DestinoLog",0);
        fecha =getIntent().getStringExtra("Fecha");
        horadepubli =getIntent().getStringExtra("hora");
        dia=getIntent().getStringExtra("Dia");
        kilosdisponibles =getIntent().getStringExtra("kilos");

        cityOrigen=getIntent().getStringExtra("cityorigen");
        cityDestino=getIntent().getStringExtra("citydestino");
        contador.setText(String.valueOf(con));



        circlefinis=findViewById(R.id.CirculeImageback);

        String kilos="";

        nex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              next("");
            }
        });


        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (con<=9)
                {
                    con+=1;
                    contador.setText(String.valueOf(con));
                }

            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (con>1)
                {
                    con-=1;
                    contador.setText(String.valueOf(con));
                }
            }
        });

        circlefinis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }
    private void next(String precio) {
        Intent intent = new Intent(PublicarCantidadViajes.this, ConfirmarPublicacion.class);
        intent.putExtra("kilos",kilosdisponibles);
        intent.putExtra("precio",precio);
        intent.putExtra("Origen",mExtraOrigen);
        intent.putExtra("OrigenLat",mExtraOrigenlat);
        intent.putExtra("OrigenLog",mExtraOrigenlong);
        intent.putExtra("Destino",mExtraDestino);
        intent.putExtra("DestinoLat",mExtraDestinolat);
        intent.putExtra("DestinoLog",mExtraDestinolong);
        intent.putExtra("Fecha",fecha);
        intent.putExtra("Dia",dia);
        intent.putExtra("hora",horadepubli);
        intent.putExtra("cityorigen",cityOrigen);
        intent.putExtra("citydestino",cityDestino);
        intent.putExtra("cantidad",String.valueOf(con));
        Log.d("loco 1",dia);
        Log.d("loco 2", String.valueOf(con));

        startActivity(intent);
    }
}