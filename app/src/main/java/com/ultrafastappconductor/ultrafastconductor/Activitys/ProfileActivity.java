package com.ultrafastappconductor.ultrafastconductor.Activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.ultrafastappconductor.ultrafastconductor.Models.Choferes;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.Choferprovider;
import com.ultrafastappconductor.ultrafastconductor.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView mCircleImageBack;
    Choferprovider choferprovider;

    private ImageView imageUser,cambiar;
    Button button;
    Authprovider authprovider;
    TextView marca,matricula,modelo;
    private final int GALLERY_REQUEST = 1;
    private ProgressDialog mdialog;
    View view;
    private File mImageFile;
    private String imageUrl;
    private  final int GalleryRequest=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mCircleImageBack = findViewById(R.id.bac);
        marca = findViewById(R.id.txtmarca);
        modelo = findViewById(R.id.txtmodelo);
        matricula = findViewById(R.id.txtmatricula);
        button = findViewById(R.id.guardarcoche);
        choferprovider = new Choferprovider();
        authprovider = new Authprovider();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar();

            }
        });

        mCircleImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void Guardar()
    {
        String mmarca,mmodelo,mmatri;
        mmarca = marca.getText().toString();
        mmodelo = marca.getText().toString();
        mmatri = marca.getText().toString();
        Choferes choferes=new Choferes();
        choferes.setMarca(mmarca);
        choferes.setModelo(mmodelo);
        choferes.setMatricula(mmatri);
        choferes.setId(authprovider.getid());

        choferprovider.Registrarcoche(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(ProfileActivity.this, "Su información se guardo correctamente", Toast.LENGTH_SHORT).show();

            }
        });




    }





}