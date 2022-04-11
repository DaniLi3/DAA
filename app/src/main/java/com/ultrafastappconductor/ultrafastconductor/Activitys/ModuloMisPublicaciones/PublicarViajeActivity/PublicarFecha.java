package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ultrafastappconductor.ultrafastconductor.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicarFecha extends AppCompatActivity {

    private double mExtraOrigenlat;
    private double mExtraOrigenlong;
    private String mExtraOrigen;

    private String cityOrigen;
    private String cityDestino;

    private double mExtraDestinolat;
    private double mExtraDestinolong;
    private String mExtraDestino;

    CircleImageView circleImageView;

    Button selecfechaa;
    CalendarView calendarView;
    int fecha=13;
    int yearr;
    String fechacomple;
    int dia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_fecha);

        selecfechaa = findViewById(R.id.selecfechaa);
        calendarView = findViewById(R.id.datepic);
        circleImageView=findViewById(R.id.Cir);

        mExtraOrigen =getIntent().getStringExtra("Origen");
        mExtraOrigenlat = getIntent().getDoubleExtra("OrigenLat",0);
        mExtraOrigenlong = getIntent().getDoubleExtra("OrigenLog",0);

        cityOrigen=getIntent().getStringExtra("cityorigen");
        cityDestino=getIntent().getStringExtra("citydestino");

        mExtraDestino =getIntent().getStringExtra("Destino");
        mExtraDestinolat = getIntent().getDoubleExtra("DestinoLat",0);
        mExtraDestinolong = getIntent().getDoubleExtra("DestinoLog",0);

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        selecfechaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 */
                if (fecha!=13)
                {

                    Intent intent = new Intent(PublicarFecha.this,PublicarHora.class);
                    intent.putExtra("Origen",mExtraOrigen);
                    intent.putExtra("OrigenLat",mExtraOrigenlat);
                    intent.putExtra("OrigenLog",mExtraOrigenlong);
                    intent.putExtra("Destino",mExtraDestino);
                    intent.putExtra("DestinoLat",mExtraDestinolat);
                    intent.putExtra("DestinoLog",mExtraDestinolong);
                    intent.putExtra("Fecha",dia+" de "+meses(fecha));
                    intent.putExtra("Dia",fechacomple);
                    intent.putExtra("cityorigen",cityOrigen);
                    intent.putExtra("citydestino",cityDestino);
                   // Log.d("ubi",dia+" de "+meses(fecha));
                    startActivity(intent);

                    //Toast.makeText(SeleccionarFecha.this, meses(fecha), Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(PublicarFecha.this, "Debes de seleccionar una fecha", Toast.LENGTH_SHORT).show();
                }


            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                fecha =month+1;
                dia=dayOfMonth;
                yearr=year;
                fechacomple= dia+"/"+fecha+"/"+yearr;
                Log.d("ubi", String.valueOf(fecha));
                Log.d("ubi", meses(fecha));

                // Toast.makeText(SeleccionarFecha.this, fecha, Toast.LENGTH_LONG).show();
            }
        });






    }
    public String meses(int valor)

    {
        String mes="";
        switch (valor){
            case 1:
                mes="Enero";break;

            case 2:
                mes="Febrero"; break;
            case 3:
                mes="Marzo"; break;
            case 4:
                mes="Abril"; break;
            case 5:
                mes="Mayo"; break;
            case 6:
                mes="Junio"; break;
            case 7:
                mes="Julio"; break;
            case 8:
                mes="Agosto"; break;
            case 9:
                mes="Septiembre"; break;
            case 10:
                mes="Octubre"; break;
            case 11:
                mes="Noviembre"; break;
            case 12:
                mes="Diciembre"; break;
            case 13:
                Toast.makeText(this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();; break;




        }
        return mes;

    }
}