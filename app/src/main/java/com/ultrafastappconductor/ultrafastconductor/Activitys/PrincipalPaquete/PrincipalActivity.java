package com.ultrafastappconductor.ultrafastconductor.Activitys.PrincipalPaquete;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.ultrafastappconductor.ultrafastconductor.R;

public class PrincipalActivity extends AppCompatActivity {

    private Button DateSelec;
    private TextView fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        DateSelec = findViewById(R.id.btnfecha);
        fecha=findViewById(R.id.edite);

        //MaterialFecha
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Seleccione la fecha");
        MaterialDatePicker materialDatePicker=builder.build();

        DateSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                fecha.setText(materialDatePicker.getHeaderText());

            }
        });
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                materialDatePicker.show(getSupportFragmentManager(),"DATE_PICKER");

            }
        });


    }

}