package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.Calendar;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditarViajePublicado extends AppCompatActivity {

    private Button Modificar;
    private TextView Fechasalida;
    private TextInputLayout Horasalida;
    private TextInputLayout Comentario;
    private ProgressDialog mdialog;

    private TextView Btnfecha;
    private TextView Origen;
    private TextView Destino;
    private TextView Kilos;
    private TextView Cantidad;
    public int kilos=1;
    public int con=1;
    ImageView menos,mas;
    ImageView menoss,mass;

    private TextView Horaa;
    private TextView Btnhora;
    String horaa;
    String minn;
    String fechaactualizada;
    String horaactualizada;
    String comentarioo;
    String pesoo;
    String cantidadentregass;

    private int dia,mes,anio;
    public String iduser;
    public String origen;
    public String origenlat;
    public String origenlog;
    public String destino;
    public String destinolat;
    public String destinolog;
    public String fechasalida;
    public String hora;
    public String fechapublicado;
    public String peso;
    public String idviaje;
    public String comentario;
    public String cantidaddeentregas;
    CircleImageView circleImageView;

    ViajesPubliProvider viajesPubliProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_viaje_publicado);
        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");
        mas=findViewById(R.id.btnmass);
        menos=findViewById(R.id.btnmenosc);
        mass=findViewById(R.id.btnmas);
        menoss=findViewById(R.id.btnmenos);
        mdialog = new ProgressDialog(this);

        Modificar=findViewById(R.id.btnmodificar);
        Fechasalida=findViewById(R.id.txtfecha);
        circleImageView=findViewById(R.id.CirculeImagebackk);


        Comentario=findViewById(R.id.txtcomentario);

        Origen=findViewById(R.id.txtdetallesorigen);
        Destino=findViewById(R.id.txtdetallesdestino);
        Btnhora=findViewById(R.id.txtmodihora);
        Btnfecha=findViewById(R.id.txtmodifecha);
        Horaa=findViewById(R.id.txthora);
        Kilos=findViewById(R.id.txtcontadorc);
        Cantidad=findViewById(R.id.txtcontador);


        iduser=getIntent().getStringExtra("iduser");
        origen=getIntent().getStringExtra("origen");
        origenlat=getIntent().getStringExtra("origenlat");
        origenlog=getIntent().getStringExtra("origenlog");
        destino=getIntent().getStringExtra("destino");
        destinolat=getIntent().getStringExtra("destinolat");
        destinolog=getIntent().getStringExtra("destinolog");
        fechasalida=getIntent().getStringExtra("fechasalida");
        hora=getIntent().getStringExtra("hora");
        fechapublicado=getIntent().getStringExtra("fechapublicado");
        peso=getIntent().getStringExtra("peso");
        idviaje=getIntent().getStringExtra("idviaje");
        comentario=getIntent().getStringExtra("comentario");
        cantidaddeentregas=getIntent().getStringExtra("cantidaddeentregas");
        Fechasalida.setText(fechasalida);

        Comentario.getEditText().setText(comentario);
        Kilos.setText(peso);
        Cantidad.setText(cantidaddeentregas);

        kilos= Integer.parseInt(peso);
        con= Integer.parseInt(cantidaddeentregas);

        Origen.setText(origen);
        Destino.setText(destino);
        Horaa.setText(hora);
        fechaactualizada=fechasalida;
        horaactualizada=hora;

        comentarioo= Comentario.getEditText().getText().toString();

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Btnfecha.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                abrirfecha();
            }
        });
        Btnhora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirhora();
            }
        });
        Modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdialog.setMessage("Actualizando...");
                mdialog.setCanceledOnTouchOutside(false);
                mdialog.show();

                        actualizarpubli();


                        mdialog.dismiss();
                        finish();



            }
        });
        mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (kilos<=49)
                {
                    kilos+=1;
                    Kilos.setText(String.valueOf(kilos));
                }

            }
        });
        menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kilos>1)
                {
                    kilos-=1;
                    Kilos.setText(String.valueOf(kilos));
                }

            }
        });
        mass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (con<=9)
                {
                    con+=1;
                    Cantidad.setText(String.valueOf(con));
                }

            }
        });
        menoss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (con>1)
                {
                    con-=1;
                    Cantidad.setText(String.valueOf(con));
                }

            }
        });
    }
    public void actualizarpubli()
    {
        comentarioo= Comentario.getEditText().getText().toString();

        ViajesPublicados viajesPublicados=new ViajesPublicados();
        viajesPublicados.setFecha(fechaactualizada);
        Log.d("david", String.valueOf(horaactualizada.length()));


        viajesPublicados.setHora(horaactualizada);
        viajesPublicados.setIdViajes(idviaje);
        viajesPublicados.setCantidadentregas(String.valueOf(con));
        viajesPublicados.setPeso(String.valueOf(kilos));
        viajesPublicados.setComentario(comentarioo);

        viajesPubliProvider.actualizarviaje(viajesPublicados).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(EditarViajePublicado.this, "Se actualizo correctamente", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(EditarViajePublicado.this, "Ocurrio un error en el sistema", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void abrirfecha() {
        final Calendar calendar= Calendar.getInstance();
        dia=calendar.get(Calendar.DAY_OF_MONTH);
        mes=calendar.get(Calendar.MONTH);
        anio=calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                fechaactualizada=dayOfMonth+" de "+meses(month);

                Fechasalida.setText(dayOfMonth+" de "+meses(month));


            }
        },dia,mes,anio);

         datePickerDialog.show();
    }

    public void abrirhora(){
        Calendar c = Calendar.getInstance();
        int hora = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(EditarViajePublicado.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                horaa= String.valueOf(hourOfDay);
                minn= String.valueOf(minute);
                horaactualizada=horaa+":"+minn;
                Horaa.setText(horaa+":"+minn);


            }
        },hora,min,true);


        timePickerDialog.show();


    }

    public String meses(int valor)

    {
        String mes="";
        switch (valor){
            case 0:
                mes="Enero";break;

            case 1:
                mes="Febrero"; break;
            case 2:
                mes="Marzo"; break;
            case 3:
                mes="Abril"; break;
            case 4:
                mes="Mayo"; break;
            case 5:
                mes="Junio"; break;
            case 6:
                mes="Julio"; break;
            case 7:
                mes="Agosto"; break;
            case 8:
                mes="Septiembre"; break;
            case 9:
                mes="Octubre"; break;
            case 10:
                mes="Noviembre"; break;
            case 11:
                mes="Diciembre"; break;
            case 13:
                Toast.makeText(this, "Seleccione una fecha", Toast.LENGTH_SHORT).show();; break;




        }
        return mes;

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}