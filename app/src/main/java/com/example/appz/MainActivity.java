package com.example.appz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText user,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user=findViewById(R.id.et_login);
        pass=findViewById(R.id.et_pass);

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                EditText et = (EditText) view;
                if (et.getText().toString().length() ==0 && b==Boolean.FALSE) {
                    user.setError("No puede estar vacio");
                    Toast.makeText(MainActivity.this,"Ingrese su usuario o su correo",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void irAlRegistro (View v){

        Toast.makeText(MainActivity.this,"Registrar Usuario",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (MainActivity.this, Registro.class);

        intent.putExtra("user",user.getText().toString());
        intent.putExtra("pass",pass.getText().toString());

        startActivity(intent);
    }




}
