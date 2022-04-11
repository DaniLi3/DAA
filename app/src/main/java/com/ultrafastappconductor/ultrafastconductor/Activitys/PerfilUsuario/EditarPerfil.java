package com.ultrafastappconductor.ultrafastconductor.Activitys.PerfilUsuario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ultrafastappconductor.ultrafastconductor.Activitys.MenuActivity;
import com.ultrafastappconductor.ultrafastconductor.Models.Choferes;
import com.ultrafastappconductor.ultrafastconductor.Providers.Authprovider;
import com.ultrafastappconductor.ultrafastconductor.Providers.Choferprovider;
import com.ultrafastappconductor.ultrafastconductor.R;
import com.ultrafastappconductor.ultrafastconductor.Utils.putPdf;

import java.util.HashMap;
import java.util.Map;

public class EditarPerfil extends AppCompatActivity {

    Choferes choferes;
    Choferprovider choferprovider;
    String nombre;
    String apellidos;
    ProgressDialog progressDialog;
    String status;
    View view;
    Authprovider authprovider;
    Button button;
    Button subirlicencia;
    Button subirine;
    Button mostrarine;
    Button mostrarlicencia;
    CardView cardView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    ImageView closee;
    int bandera=0;


    private TextInputLayout nombree;
    private TextInputLayout apell;
    
    private TextInputLayout domicilio;
    private TextInputLayout email;
    private TextInputLayout telefono;
    private TextInputLayout descripcion;
    private TextInputLayout marca;
    private TextInputLayout modelo;
    private TextInputLayout anio;
    private TextInputLayout matricula;
    TextView LabelLicencia;
    TextView LabelLicencia2;
    TextView statuslicencia;
    TextView statusidentificacion;
    String urlidentificacion;
    String urllicencia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        nombree = findViewById(R.id.perfilnombre);
        apell = findViewById(R.id.perfilapellido);

        domicilio= findViewById(R.id.perfildomicilio);
        email= findViewById(R.id.perfilemail);
        telefono= findViewById(R.id.perfiltelefon);
        descripcion= findViewById(R.id.perfildescrip);
        button=findViewById(R.id.btnguardar);
        subirlicencia=findViewById(R.id.btnsubirlicencia);
        subirine=findViewById(R.id.btnsubir);
        LabelLicencia=findViewById(R.id.labellicencia);
        LabelLicencia2=findViewById(R.id.labellicencia2);
        marca= findViewById(R.id.perfilmarca);
        modelo= findViewById(R.id.perfilmodelo);
        anio= findViewById(R.id.perfilanio);
        matricula= findViewById(R.id.perfilmatricula);
        cardView=findViewById(R.id.documentoscard);
        closee = findViewById(R.id.closs);
        mostrarlicencia = findViewById(R.id.btnmostrarlicencia);
        mostrarine = findViewById(R.id.btnmostrar);
        statuslicencia = findViewById(R.id.statuslicencia);
        statusidentificacion = findViewById(R.id.statusidentificacion);
        choferes = new Choferes();
        choferprovider = new Choferprovider();
        authprovider=new Authprovider();





       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               if (!nombree.getEditText().getText().toString().equals("")&&!apell.getEditText().getText().toString().equals("")
               &&!domicilio.getEditText().getText().toString().equals("")
               &&!email.getEditText().getText().toString().equals("")&&!telefono.getEditText().getText().toString().equals("")
               &&!descripcion.getEditText().getText().toString().equals(""))
               {

                   actualizar();
               }
               else
               {
                   Toast.makeText(EditarPerfil.this, "Todos los datos deben estar completos", Toast.LENGTH_SHORT).show();
               }
           }
       });
       subirlicencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               bandera=1;

               SeleccionarPdfLicencia();
           }
       });
       subirine.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SeleccionarPdf();
           }
       });
       mostrarlicencia.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse(urllicencia));
               startActivity(intentNavegador);
           }
       });  mostrarine.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intentNavegador = new Intent(Intent.ACTION_VIEW, Uri.parse(urlidentificacion));
               startActivity(intentNavegador);
           }
       });

       cardView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mostrarDialog();
           }
       });
       closee.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               finish();
           }
       });
    }
    public void SeleccionarPdf()
    {
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Users");
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"SELECCIONAR PDF"),12);

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==12&&resultCode==RESULT_OK&&data.getData()!=null)
        {
            if (bandera==1)
            {
                Log.d("supera","si");
                LabelLicencia2.setVisibility(View.VISIBLE);
                subirlicencia.setText("Subir ahora");
                LabelLicencia2.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
                subirlicencia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubirPdfLicencia(data.getData());
                    }
                });


            }
            else
            {

                LabelLicencia.setVisibility(View.VISIBLE);
                subirine.setText("Subir ahora");
                LabelLicencia.setText(data.getDataString().substring(data.getDataString().lastIndexOf("/")+1));
                subirine.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SubirPdf(data.getData());
                    }
                });
            }

        }
    }
    public void SeleccionarPdfLicencia()
    {
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance("https://ultrafast-19299-default-rtdb.firebaseio.com").getReference().child("Users");
        Intent intent=new Intent();
        intent.setType("application/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"SELECCIONAR PDF"),12);

    }

    private void SubirPdf(Uri data ) {
        authprovider = new Authprovider();
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.show();
        StorageReference reference=storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri =uriTask.getResult();
                putPdf putPdf=new putPdf(LabelLicencia.getText().toString(),uri.toString());

                Map<String,Object> map=new HashMap<>();
                map.put("identificacion",putPdf);
                databaseReference.child(authprovider.getid()).updateChildren(map);
               // databaseReference.child("bnUFbzJsXIcDHIHoBm4zMMA9lBx1").updateChildren(putPdf);
                Log.d("pipi", String.valueOf(putPdf));


                Choferes choferes=new Choferes();

                Toast.makeText(EditarPerfil.this, "Archivo subido", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
                startActivity(getIntent());


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progess=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Subiendo archivo.. "+(int) progess+ "%");

            }
        });
    }
 private void SubirPdfLicencia(Uri data ) {
        authprovider = new Authprovider();
        final ProgressDialog progressDialog =new ProgressDialog(this);
        progressDialog.setTitle("Subiendo archivo...");
        progressDialog.show();
        StorageReference reference=storageReference.child("upload"+System.currentTimeMillis()+".pdf");
        reference.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri =uriTask.getResult();
                putPdf putPdf=new putPdf(LabelLicencia2.getText().toString(),uri.toString());

                Map<String,Object> map=new HashMap<>();
                map.put("licencia",putPdf);
                databaseReference.child(authprovider.getid()).updateChildren(map);
               // databaseReference.child("bnUFbzJsXIcDHIHoBm4zMMA9lBx1").updateChildren(putPdf);
                Log.d("pipi", String.valueOf(putPdf));


                Choferes choferes=new Choferes();

                Toast.makeText(EditarPerfil.this, "Archivo subido", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
                startActivity(getIntent());


            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progess=(100.0*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                progressDialog.setMessage("Subiendo archivo.. "+(int) progess+ "%");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        authprovider = new Authprovider();
        progressDialog = new ProgressDialog(EditarPerfil.this);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        choferprovider.getidentificacion(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    mostrarine.setVisibility(View.VISIBLE);
                    subirine.setText("Cambiar");
                    urlidentificacion=snapshot.child("url").getValue().toString();
                    statusidentificacion.setVisibility(View.VISIBLE);

                }
                else
                {
                    statusidentificacion.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); choferprovider.getlicencia(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                    mostrarlicencia.setVisibility(View.VISIBLE);
                    subirlicencia.setText("Cambiar");
                    urllicencia=snapshot.child("url").getValue().toString();
                    statuslicencia.setVisibility(View.VISIBLE);

                }
                else
                {
                    statuslicencia.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        getUserInfo();
    }

    private void getUserInfo()
    {

        choferprovider.getusuario(authprovider.getid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {



                    if (snapshot.hasChild("nombre"))
                    {
                        nombree.getEditText().setText(snapshot.child("nombre").getValue().toString());
                    }
                    if (snapshot.hasChild("status"))
                    {
                        Log.d("chay","Aguaa");
                        Log.d("chay",snapshot.child("status").getValue().toString());
                        if (snapshot.child("status").getValue().toString().equals("Validado")){
                            statuslicencia.setVisibility(View.VISIBLE);
                            statusidentificacion.setVisibility(View.VISIBLE);
                            statusidentificacion.setTextColor(getResources().getColor(R.color.purple_200));
                            statuslicencia.setTextColor(getResources().getColor(R.color.purple_200));
                            statusidentificacion.setText("Validado");
                            statuslicencia.setText("Validado");
                            Log.d("chay","Agua");


                        }
                    }
                    nombree.getEditText().setText(snapshot.child("nombre").getValue().toString());
                    apell.getEditText().setText(snapshot.child("apellidos").getValue().toString());

                    if (snapshot.hasChild("domicilio"))
                    {
                        domicilio.getEditText().setText(snapshot.child("domicilio").getValue().toString());
                    }
                    if (snapshot.hasChild("email"))
                    {
                        email.getEditText().setText(snapshot.child("email").getValue().toString());
                    }
                    if (snapshot.hasChild("telefono"))
                    {
                        telefono.getEditText().setText(snapshot.child("telefono").getValue().toString());
                    }
                    if (snapshot.hasChild("bibliografia"))
                    {
                        descripcion.getEditText().setText(snapshot.child("bibliografia").getValue().toString());
                    }
                    if (snapshot.hasChild("marca"))
                    {
                        marca.getEditText().setText(snapshot.child("marca").getValue().toString());
                    }
                    if (snapshot.hasChild("modelo"))
                    {
                        modelo.getEditText().setText(snapshot.child("modelo").getValue().toString());
                    }
                    if (snapshot.hasChild("anio"))
                    {
                        anio.getEditText().setText(snapshot.child("anio").getValue().toString());
                    }
                    if (snapshot.hasChild("matricula"))
                    {
                        matricula.getEditText().setText(snapshot.child("matricula").getValue().toString());
                    }
                    progressDialog.dismiss();



                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
    public void actualizar()
    {
        Choferes choferes=new Choferes();
        choferes.setNombre(nombree.getEditText().getText().toString());
        choferes.setApellidos(apell.getEditText().getText().toString());

        choferes.setTelefono(telefono.getEditText().getText().toString());
        choferes.setDireccion(domicilio.getEditText().getText().toString());
        choferes.setEmail(email.getEditText().getText().toString());
        choferes.setBibliografia(descripcion.getEditText().getText().toString());
        choferes.setAnio(anio.getEditText().getText().toString());
        choferes.setStatus("Sin validar");
        if (!marca.getEditText().getText().toString().equals("")&&!modelo.getEditText().getText().toString().equals("")&&!matricula.getEditText().getText().toString().equals(""))
        {
            choferes.setStatus("Validado");
            choferes.setMarca(marca.getEditText().getText().toString());
            choferes.setModelo(modelo.getEditText().getText().toString());
            choferes.setMatricula(matricula.getEditText().getText().toString());
        }


        choferes.setId(authprovider.getid());

        choferprovider.Actializar(choferes).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(EditarPerfil.this, MenuActivity.class);
                bundle.putString("locoo", "si");
                intent.putExtras(bundle);
                startActivity(intent);
                Toast.makeText(EditarPerfil.this, "Su información se actualizo correctamente", Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void mostrarDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(EditarPerfil.this);

        LayoutInflater inflater=getLayoutInflater();

        View view=inflater.inflate(R.layout.dialog_personalizado,null);
        builder.setView(view);
        AlertDialog dialog=builder.create();
        dialog.show();

        TextView test=view.findViewById(R.id.txtsubirdocu);
        ImageView close=view.findViewById(R.id.close);
        Button continuar=view.findViewById(R.id.btndialog);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dialog.dismiss();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}