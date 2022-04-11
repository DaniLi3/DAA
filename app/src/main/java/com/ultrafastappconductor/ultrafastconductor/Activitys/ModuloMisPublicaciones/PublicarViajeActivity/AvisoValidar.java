package com.ultrafastappconductor.ultrafastconductor.Activitys.ModuloMisPublicaciones.PublicarViajeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ultrafastappconductor.ultrafastconductor.Activitys.MenuActivity;
import com.ultrafastappconductor.ultrafastconductor.R;

public class AvisoValidar extends AppCompatActivity {
    private Button Iraperfil;
    String fecha="asd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_validar);
        Bundle bundle = new Bundle();
        fecha =getIntent().getStringExtra("Fecha");

        Iraperfil=findViewById(R.id.btniraperfil);

        Iraperfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvisoValidar.this, MenuActivity.class);
                bundle.putString("locoo", "si");
                intent.putExtras(bundle);
                startActivity(intent);
                finish();

            }
        });
    }
}