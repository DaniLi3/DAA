package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.TapViajesPublicados;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastappconductor.ultrafastconductor.Models.SolicitudesCanceladasmodel;
import com.ultrafastappconductor.ultrafastconductor.Models.ViajesPublicados;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.GeofireProvider;
import com.ultrafastappconductor.ultrafastconductor.Providers.TokenProvider;
import com.ultrafastappconductor.ultrafastconductor.Providers.ViajesPubliProvider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Services.ForegrundService2;

public class VerViajeenCurso extends AppCompatActivity {
    ViajesPubliProvider viajesPubliProvider;

   private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private Authprovider authprovider;
    private LatLng mcurrenLanLog;
    String statuss ="";

    private ValueEventListener mListener;
    SharedPreferences mpref;
    private final int REQUEST_CHECHK_SETIING = 0x1;
    private GoogleApiClient mGoogleApiClient;
    private Marker marker;
    private LocationRequest mLocationrequest;
    private FusedLocationProviderClient mFusedLocation;
    private final static int LOCATION_REQUEST = 1;
    private final static int SETTINGS_REQUEAST = 2;
    private TokenProvider mTokenprovider;
    private boolean mextraConnect;

    private Button mbutonConec;
    private boolean isConect = false;

    private GeofireProvider mGeoFireprovider;
    private ValueEventListener mvalueListener;
    private boolean mIsStartLocation = false;
    LatLng mStart;
    LatLng mEnd;
    LocationManager mlocationManager;
    private  boolean validarboton = false;


    LocationListener locationListenerGPS = new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {
            mcurrenLanLog = new LatLng(location.getLatitude(), location.getLongitude());
            if (mStart != null) {
                mEnd = mStart;
            }
            mStart = new LatLng(mcurrenLanLog.latitude, mcurrenLanLog.longitude);
            if (mEnd != null) {
                //CarMoveAnim.carAnim(marker, mEnd, mStart);
            }
          /*  mMap.animateCamera(CameraUpdateFactory.newCameraPosition( // establece la unicacion y mueve la camara a esa posicion
                    new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(17f)
                            .build()
            ));*/
            updateLocation();

        }
    };
    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (getApplicationContext() != null) {


                        //mMap.clear();
                        mcurrenLanLog = new LatLng(location.getLatitude(), location.getLongitude());

                        mIsStartLocation = false;

                     /*   mMap.moveCamera(CameraUpdateFactory.newCameraPosition( // establece la unicacion y mueve la camara a esa posicion
                                new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(17f)
                                        .build()
                        ));
                        marker = mMap.addMarker(new MarkerOptions().position(
                                new LatLng(location.getLatitude(), location.getLongitude())
                        ).title("Tu posicion")
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_bak)));*/

                        updateLocation();
                        if (ActivityCompat.checkSelfPermission(VerViajeenCurso.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VerViajeenCurso.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                            return;
                        }
                        mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, locationListenerGPS);
                     //   stopLocation();


                }

            }
        }
    };





    public TextView Origen;
    public TextView Destino;
    public TextView FechaCreado;
    public TextView Pesodisponible;
    public TextView EntregasDispo;
    public TextView Comentario;
    public TextView Cityorigen;
    public TextView CityDestino;
    public TextView Fechasalida;
    public Button cancelar;
    private Button modificar;


    public String iduser;
    String sstatus=" ";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_viajeen_curso);

        authprovider = new Authprovider();
        mGeoFireprovider= new GeofireProvider("active_driver");
        mTokenprovider = new TokenProvider();
       // mDriverFoundProvider = new DriverFoundProvider();
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mlocationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mGoogleApiClient=getApiCientInstance();
        viajesPubliProvider = new ViajesPubliProvider("ViajesPublicados");
        if (mGoogleApiClient!=null)
        {
            mGoogleApiClient.connect();
        }
        mLocationrequest = new LocationRequest();

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
          sstatus=getIntent().getStringExtra("status");
          if (sstatus!=null)
          {
              if (sstatus.equals("INICIADO"))
              {
                  modificar.setText("DETENER");
                  startService();
                  startLocation();


              }
              else
              {

                  modificar.setText("INICIAR VIAJE");


              }

          }




        Fechasalida.setText(fechasalida);
       // Cityorigen.setText(txtcityorigen);
       // CityDestino.setText(txtcitydestino);
        Origen.setText(origen);
        Destino.setText(destino);
        String []f = fechapublicado.split(" ");
        FechaCreado.setText(f[0]);
      //  cancelar.setPaintFlags(cancelar.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        Pesodisponible.setText(peso+" kg");
        EntregasDispo.setText(cantidaddeentregas+" entregas");
        Comentario.setText(comentario);

        modificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (modificar.getText().equals("INICIAR VIAJE"))
                {
                    startService();
                    startLocation();
                    modificar.setText("DETENER");
                    CambiarestadoViaje("INICIADO");
                    CambiarestadoSoli("INICIADO");

                }
                else if (modificar.getText().equals("DETENER"))
                {
                   stopService();
                    modificar.setText("INICIAR VIAJE");
                    CambiarestadoViaje("DETENIDO");
                    Toast.makeText(VerViajeenCurso.this, "Se detuvo el viaje", Toast.LENGTH_SHORT).show();

                }



            }
        });
        mpref=getApplicationContext().getSharedPreferences("RideStatus",MODE_PRIVATE);
        String statuss=mpref.getString("status","");

    }

    private void CambiarestadoViaje(String status) {
        ViajesPublicados viajesPublicados =new ViajesPublicados();
        viajesPublicados.setStatus(status);
        viajesPublicados.setIdViajes(idviaje);

        viajesPubliProvider.actualizarstatus(viajesPublicados).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }
    private void CambiarestadoSoli(String status) {
        viajesPubliProvider = new ViajesPubliProvider("SolicitudesAceptadas");
        SolicitudesCanceladasmodel viajesPublicados =new SolicitudesCanceladasmodel();
        viajesPublicados.setStatus(status);
        viajesPublicados.setIdnoty(status);

        viajesPubliProvider.actualizarsoli(viajesPublicados).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

            }
        });
    }

    public void startService()
    {
        stopLocation();
        Intent serviceIntent=new Intent(this, ForegrundService2.class);
        ContextCompat.startForegroundService(VerViajeenCurso.this,serviceIntent);
    }

    private void updateLocation()
    {

        if (authprovider.existSesion() && mcurrenLanLog!=null)
        {
            mGeoFireprovider.savelocation(authprovider.getid(), mcurrenLanLog);

            // mGeoFireprovider.savelocation(authprovider.getUI(), mcurrenLanLog);
        }
        else
        {
            Toast.makeText(this, "no se jalo", Toast.LENGTH_SHORT).show();
        }

    }
    public void stopService()
    {
        stopLocation();
        Intent serviceIntent=new Intent(this, ForegrundService2.class);

        stopService(serviceIntent);
    }
    private void startLocation()
    {

        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                if (gpsActive()) {
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallback, Looper.myLooper());
                    Log.d("Llave","es :"+mLocationrequest);
                    Log.d("Llave","es :"+mLocationCallback);

                   // mbutonConec.setText("Desconectarse");
                    isConect = true;
                }
                else
                {
                    //alertDialog();
                    RequestGPSSetting();

                }
            }
            else
            {
                checklocationpermiss();
            }

        }
        else {
            if (gpsActive()) {

                mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallback, Looper.myLooper());
            }
            else
            {
                //  alertDialog();
                RequestGPSSetting();
            }

        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActive()) {
                        mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallback, Looper.myLooper());
                    }
                    else {
                        //alertDialog();
                        RequestGPSSetting();
                    }
                } else {
                    checklocationpermiss();

                }
            } else {
                checklocationpermiss();

            }

        }
    }
    private boolean gpsActive()
    {
        boolean isActv=false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            isActv=true;

        }
        return isActv;
    }
    private void RequestGPSSetting()
    {
        LocationSettingsRequest.Builder builder= new LocationSettingsRequest.Builder().addLocationRequest(mLocationrequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result= LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient,builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status=locationSettingsResult.getStatus();
                if (status.getStatusCode()== LocationSettingsStatusCodes.SUCCESS)
                {
                    Toast.makeText(VerViajeenCurso.this, "El gps ya está activado", Toast.LENGTH_SHORT).show();

                }
                else if(status.getStatusCode()== LocationSettingsStatusCodes.RESOLUTION_REQUIRED)
                {
                    try {
                        status.startResolutionForResult(VerViajeenCurso.this,REQUEST_CHECHK_SETIING);

                    }
                    catch (IntentSender.SendIntentException e)
                    {
                        Toast.makeText(VerViajeenCurso.this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (ActivityCompat.checkSelfPermission(VerViajeenCurso.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(VerViajeenCurso.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallback, Looper.myLooper());
                 //   mMap.setMyLocationEnabled(false);

                }
                else if(status.getStatusCode()== LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE)
                {
                    Toast.makeText(VerViajeenCurso.this, "La configuracion del gps tiene un error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private void stopLocation()
    {
        if (mLocationCallback != null && mFusedLocation != null) {
            mFusedLocation.removeLocationUpdates(mLocationCallback);
            Log.d("nola","sii");
        }
        else{
            Log.d("nola","noo");
            mFusedLocation.removeLocationUpdates(mLocationCallback);

        }
    }
    private void checklocationpermiss()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicacion necesita permisos para usarse")
                        .setPositiveButton("Otorgar permisos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(VerViajeenCurso.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);



                            }
                        })
                        .create()
                        .show();

            }
            else
            {
                ActivityCompat.requestPermissions(VerViajeenCurso.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST);


            }


        }

    }
    private GoogleApiClient getApiCientInstance()
    {
        GoogleApiClient googleApiClient= new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        return googleApiClient;

    }


}