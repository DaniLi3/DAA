package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.channel.SNTPClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetallesViajePublicado extends AppCompatActivity {
    public TextView Fechasalida;
    public Button cancelar;
    public TextView Origen;
    public TextView Destino;
    public TextView FechaCreado;
    public TextView Pesodisponible;
    public TextView EntregasDispo;
    public TextView Comentario;
    public TextView Cityorigen;
    public TextView CityDestino;
    public String iduser;
    public String origen;
    public String origenlat;
    public String origenlog;
    String fechasistema;
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
    public String txtcityorigen;
    public String txtcitydestino;

    ViajesPubliProvider viajesPubliProvider;
    CircleImageView circleImageView;

    private Button modificar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_viaje_publicado);
        cancelar=findViewById(R.id.cancelarviaje);
        Fechasalida=findViewById(R.id.txtdetallesfecha);
        Origen=findViewById(R.id.txtdetallesorigen);
        Destino=findViewById(R.id.txtdetallesdestino);
        FechaCreado=findViewById(R.id.txtdetallefechacre);
        Pesodisponible=findViewById(R.id.txtdetalleskilos);
        EntregasDispo=findViewById(R.id.txtdetaprecio);
        Comentario=findViewById(R.id.txtdetallecomenta);
        modificar=findViewById(R.id.btnmodificarviaje);
        Cityorigen=findViewById(R.id.txtcityorigen);
        CityDestino=findViewById(R.id.txtcitydestino);
        circleImageView=findViewById(R.id.CirculeImageback);
        viajesPubliProvider=new ViajesPubliProvider("ViajesPublicados");
        idviaje=getIntent().getStringExtra("idviaje");

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetallesViajePublicado.this,EditarViajePublicado.class);
                intent.putExtra("iduser", iduser);
                intent.putExtra("origen", origen);
                intent.putExtra("origenlat", origenlat);
                intent.putExtra("origenlog", origenlog);
                intent.putExtra("destino", destino);
                intent.putExtra("destinolat", destinolat);
                intent.putExtra("destinolog", destinolog);
                intent.putExtra("fechasalida", fechasalida);
                intent.putExtra("hora", hora);
                intent.putExtra("fechapublicado", fechapublicado);
                intent.putExtra("peso", peso);
                intent.putExtra("idviaje", idviaje);
                intent.putExtra("comentario", comentario);
                intent.putExtra("cantidaddeentregas", cantidaddeentregas);
                startActivity(intent);

            }
        });



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialog();
            }
        });
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void Consultar(String idViaje){
        viajesPubliProvider.getViajes(idViaje).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    peso=snapshot.child("peso").getValue().toString();
                    iduser=snapshot.child("idUser").getValue().toString();
                    origen=snapshot.child("origen").getValue().toString();
                    origenlat=snapshot.child("destino").getValue().toString();
                    origenlog=snapshot.child("peso").getValue().toString();
                    destino=snapshot.child("destino").getValue().toString();
                    destinolat=snapshot.child("peso").getValue().toString();
                    destinolog=snapshot.child("peso").getValue().toString();
                    fechasalida=snapshot.child("fecha").getValue().toString();
                    hora=snapshot.child("hora").getValue().toString();
                    fechapublicado=snapshot.child("fechaactual").getValue().toString();
                    txtcityorigen=snapshot.child("cityOrigen").getValue().toString();
                    txtcitydestino=snapshot.child("cityDestino").getValue().toString();


                    comentario=snapshot.child("comentario").getValue().toString();
                    cantidaddeentregas=snapshot.child("cantidadentregas").getValue().toString();
                    Fechasalida.setText(fechasalida);
                  //  Cityorigen.setText(txtcityorigen);
                    //  CityDestino.setText(txtcitydestino);
                    Origen.setText(origen);
                    Destino.setText(destino);
                    String []f = fechapublicado.split(" ");
                    FechaCreado.setText(f[0]);
                    cancelar.setPaintFlags(cancelar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                    Pesodisponible.setText(peso+" kg");
                    EntregasDispo.setText(cantidaddeentregas+" entregas");
                    Comentario.setText(comentario);
                    SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
                        @Override
                        public void onTimeReceived(String rawDate) {
                            fechasistema=rawDate;
                            long localJsonDate = GetTimeStamp(fechasistema);
                            long remoteJsondate = GetTimeStamp(fechapublicado);
                            Log.d("llave","fecha sistema "+rawDate);
                            Log.d("llave","fecha publi "+fechapublicado);
                            if (remoteJsondate > localJsonDate)  {
                                //Se debe actualizar datos
                                Log.d("llave","se necestita actualizar");
                            } else {
                                Log.d("llave","datos actualizados");
                            }

                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.d("llave","error al obtener"+ex);


                        }
                    });

                }
                else
                {
                    Toast.makeText(DetallesViajePublicado.this, "No existe este campo", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void mostrarDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(DetallesViajePublicado.this);

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.dialog_confirmar_cancelar,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        TextView sicancelar=view.findViewById(R.id.sicancelar);
        TextView noancelar=view.findViewById(R.id.nocancelar);


        sicancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viajesPubliProvider.delete(idviaje).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(DetallesViajePublicado.this, "El viaje se elimino", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        finish();
                    }
                });


            }
        });
        noancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
@Override
protected void onStart() {
    super.onStart();
    Consultar(idviaje);
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
    public void sasa() {
        SNTPClient.getDate(TimeZone.getTimeZone("America/Mexico_City"), new SNTPClient.Listener() {
            @Override
            public void onTimeReceived(String rawDate) {
                fechasistema=rawDate;
                Log.d("llave","fecha fe "+rawDate);
                Log.d("llave","fecha fe "+fechasistema);

            }

            @Override
            public void onError(Exception ex) {
                Log.e(SNTPClient.TAG, ex.getMessage());
                Log.d("llave","error"+fechasistema);
            }
        });
    }




}