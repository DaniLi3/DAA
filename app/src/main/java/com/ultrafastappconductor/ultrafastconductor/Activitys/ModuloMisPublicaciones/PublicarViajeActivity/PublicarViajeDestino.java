package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.maps.android.SphericalUtil;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PublicarViajeDestino extends AppCompatActivity {
    CircleImageView salir;
    CircleImageView btnpublicardestino;
    GoogleMap mMapM;
    String cityOrigen=" ";
    String cityDestino=" ";
    LatLng mDestinolnLo;
    private double mExtraOrigenlat;
    private boolean marcador = false;
    private double mExtraOrigenlong;
    private String mExtraOrigen;
    private AutocompleteSupportFragment mAutocomplete;
    private GoogleApiClient mGoogleApiClient;
    private LatLng mlatLng;
    private String mOrigin;
    private String mDestino;
    private PlacesClient mplaces;
    private FusedLocationProviderClient mFusedLocation;
    private LocationRequest mLocationrequest;
    private boolean mOriginSelect = true;

    private GoogleMap.OnCameraIdleListener mcameraListener;
    private final static int LOCATION_REQUEST_CODE = 1;
    private final int REQUEST_CHECHK_SETIING = 0x1;
    private SupportMapFragment mapFragment;

    LocationCallback mLocationCallbal = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location : locationResult.getLocations()) {
                if (marcador==false)
                {

                    if (getApplicationContext() != null) {
                    /*marker = mMapM.addMarker(new MarkerOptions().position(
                            new LatLng(location.getLatitude(), location.getLongitude())).title("Tu posicion").icon(BitmapDescriptorFactory.fromResource(R.drawable.point)));*/

                        mlatLng = new LatLng(location.getLatitude(), location.getLongitude());
                        mMapM.moveCamera(CameraUpdateFactory.newCameraPosition(
                                new CameraPosition.Builder()
                                        .target(new LatLng(location.getLatitude(), location.getLongitude()))
                                        .zoom(15f)
                                        .build()
                        ));
                        LimitadSearch();
                        marcador=true;
                    }
                    else{

                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar_viaje_destino);
        salir=findViewById(R.id.Cir);
        btnpublicardestino = findViewById(R.id.btnpublicardestino);
        mGoogleApiClient = getApiCientInstance();

        mExtraOrigen =getIntent().getStringExtra("origin");
        mExtraOrigenlat = getIntent().getDoubleExtra("origin_lat",0);
        mExtraOrigenlong = getIntent().getDoubleExtra("origin_log",0);
        cityOrigen = getIntent().getStringExtra("cityorigen");
        mFusedLocation = LocationServices.getFusedLocationProviderClient(this);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this::onMapReady);
        mGoogleApiClient = getApiCientInstance();
        if (mGoogleApiClient!=null)
        {
            mGoogleApiClient.connect();
        }
        checklocationpermiss();
        startLocation();
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), getResources().getString(R.string.google_maps_key));
        }

        mplaces = Places.createClient(this);
        instanceAutocompleteOrigin();
        onCameramove();



        salir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnpublicardestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("fallaa","es "+cityDestino);
                if (cityDestino==null)
                {
                    Log.d("fallaa" +
                            "a","es "+cityDestino);
                    Toast.makeText(PublicarViajeDestino.this, "Por favor arrastra el icono a otro punto", Toast.LENGTH_SHORT).show();
return;

                }
                if (cityDestino.equals("Centro")||cityDestino=="Centro"||cityDestino==null||cityDestino==" ")
                {
                    Toast.makeText(PublicarViajeDestino.this, "Por favor arrastra el icono a otro punto", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(PublicarViajeDestino.this, PublicarFecha.class);
                    intent.putExtra("Origen",mExtraOrigen);
                    intent.putExtra("OrigenLat",mExtraOrigenlat);
                    intent.putExtra("OrigenLog",mExtraOrigenlong);
                    intent.putExtra("Destino",mDestino);
                    intent.putExtra("DestinoLat",mDestinolnLo.latitude);
                    intent.putExtra("DestinoLog",mDestinolnLo.longitude);
                    intent.putExtra("cityorigen",cityOrigen);
                    intent.putExtra("citydestino",cityDestino);
                     Log.d("marcus",cityOrigen);
                     Log.d("marcus",cityDestino);
                     startActivity(intent);
                }
            }
        });
    }
    private void LimitadSearch() {
        LatLng northSide = SphericalUtil.computeOffset(mlatLng, 5000, 0);
        LatLng southSide = SphericalUtil.computeOffset(mlatLng, 5000, 180);
        // mAutocomplete.setCountry("MX");
        mAutocomplete.setLocationBias(RectangularBounds.newInstance(southSide, northSide));
        mAutocomplete.setCountry("MX");

        mAutocomplete.setLocationBias(RectangularBounds.newInstance(southSide, northSide));
    }
    private void startLocation()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
            {
                if (gpsActive())
                {

                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
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
            if (gpsActive())
            {
                mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
            }
            else
            {
                // alertDialog();
                RequestGPSSetting();

            }
        }

    }
    public void onCameramove()
    {
        mcameraListener = new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                try {
                    Geocoder geocoder = new Geocoder(PublicarViajeDestino.this);

                    mDestinolnLo = mMapM.getCameraPosition().target;
                    List<Address> addressList = geocoder.getFromLocation(mDestinolnLo.latitude, mDestinolnLo.longitude, 1);
                    cityDestino = addressList.get(0).getLocality();
                    String pais = addressList.get(0).getCountryName();
                    String addres = addressList.get(0).getAddressLine(0);

                    String state = addressList.get(0).getAdminArea();
                    String postalCode = addressList.get(0).getPostalCode();

                    mDestino = cityDestino + " " + addres+" "+pais;
                   mAutocomplete.setText(cityDestino + " " + addres+" "+pais);
                    mOriginSelect = false;


                } catch (Exception e) {
                    Log.d("Error" + " Error", e.getMessage());
                }

            }
        };


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
    private void RequestGPSSetting() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationrequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
                Status status = locationSettingsResult.getStatus();
                if (status.getStatusCode() == LocationSettingsStatusCodes.SUCCESS) {
                    Toast.makeText(PublicarViajeDestino.this, "El gps ya está activado", Toast.LENGTH_SHORT).show();
                } else if (status.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                    try {
                        status.startResolutionForResult(PublicarViajeDestino.this, REQUEST_CHECHK_SETIING);

                    } catch (IntentSender.SendIntentException e) {
                        Toast.makeText(PublicarViajeDestino.this, "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    if (ActivityCompat.checkSelfPermission(PublicarViajeDestino.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PublicarViajeDestino.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    mFusedLocation.requestLocationUpdates(mLocationrequest, mLocationCallbal, Looper.myLooper());
                    mMapM.setMyLocationEnabled(false);

                } else if (status.getStatusCode() == LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE) {
                    Toast.makeText(PublicarViajeDestino.this, "La configuracion del gps tiene un error", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private GoogleApiClient getApiCientInstance() {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).build();
        return googleApiClient;

    }
    private void instanceAutocompleteOrigin() {

        mAutocomplete = (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.placeAutoCompleteDes);
        mAutocomplete.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.LAT_LNG, Place.Field.NAME,Place.Field.ADDRESS));
        mAutocomplete.setHint("¿Cúal es tu destino?");
        mAutocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                mDestino = place.getAddress();
                mDestinolnLo = place.getLatLng();

                mMapM.moveCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(new LatLng(mDestinolnLo.latitude, mDestinolnLo.longitude))
                                .zoom(15f)
                                .build()
                ));

            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });
    }
    private void checklocationpermiss()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los permisos para continuar")
                        .setMessage("Esta aplicacion necesita permisos para usarse")
                        .setPositiveButton("Otorgar permisos", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PublicarViajeDestino.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);


                            }
                        })
                        .create()
                        .show();

            }
            else
            {
                ActivityCompat.requestPermissions(PublicarViajeDestino.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }


        }

    }
    private void onMapReady(GoogleMap googleMap) {
        mMapM = googleMap;
        mMapM.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMapM.getUiSettings().setZoomControlsEnabled(true);

        mMapM.setOnCameraIdleListener(mcameraListener);
        mLocationrequest = new LocationRequest();
        mLocationrequest.setInterval(1000);
        mLocationrequest.setFastestInterval(1000);
        mLocationrequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationrequest.setSmallestDisplacement(5);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }



        startLocation();
        //mMapM.setMyLocationEnabled(true);
    }
}