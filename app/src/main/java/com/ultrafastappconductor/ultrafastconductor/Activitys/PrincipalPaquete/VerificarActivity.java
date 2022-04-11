package com.ultrafastappconductor.ultrafastconductor.Activitys.PrincipalPaquete;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity.PublicarViajeOrigen;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.Choferprovider;
import com.ultrafastappconductor.ultrafastconductor.R;

public class VerificarActivity extends AppCompatActivity {
    SharedPreferences.Editor meditor;
    SharedPreferences mpref;
    Authprovider authprovider;
    Choferprovider choferprovider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificar);
        choferprovider = new Choferprovider();
        authprovider = new Authprovider();
        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    String status = snapshot.child("status").getValue().toString();
                    if (status.equals("Sin validar"))
                    {
                        Intent intent=new Intent(VerificarActivity.this, PublicarViajeOrigen.class);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






    }
}