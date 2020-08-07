package com.example.appz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {

    EditText user, correo, pass1,pass2, nombre, apellido, cedula, nacimiento, fono ;
    ListView listView;
    RadioGroup rg1;
    Switch sw1,sw2,sw3;
    String txtFecha, veri1,veri2,mensaje="";
    Pattern pattern = Pattern
            .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    String CERO = "0";
    String BARRA = "/";
    Calendar c = Calendar.getInstance();
    int mes = c.get(Calendar.MONTH);
    int dia = c.get(Calendar.DAY_OF_MONTH);
    int anio = c.get(Calendar.YEAR);
    int yyyy=0, mm=0, dd=0;
    Set<String> txtlistaFono;
    ArrayList<String> telefonosArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        user = findViewById(R.id.et_nick);
        correo = findViewById(R.id.et_correo);
        pass1 = findViewById(R.id.et_pass1);
        pass2 = findViewById(R.id.et_pass2);
        nombre = findViewById(R.id.et_nombre);
        apellido = findViewById(R.id.et_apellido);
        cedula = findViewById(R.id.et_rut);
        nacimiento = findViewById(R.id.et_naci);
        fono = findViewById(R.id.et_telefono);

        rg1= findViewById(R.id.radioGroup);
        sw1 = findViewById(R.id.switch1);
        sw2 = findViewById(R.id.switch2);
        sw3 = findViewById(R.id.switch3);

        listView = findViewById(R.id.listview);
        txtlistaFono = new HashSet<String>();

        Bundle b = getIntent().getExtras();
        String txt=(b.getString("user"));
        user.setText(txt);

// Inicio Validaciones

        user.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (user.getText().toString().length() ==0 && b==Boolean.FALSE) {
                    user.setError("Debe ingresar un nick o alias");
                   // Toast.makeText(Registro.this,"Ingrese su nick o alias",Toast.LENGTH_SHORT).show();
                }
            }
        });

        correo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                 Matcher mather = pattern.matcher(correo.getText().toString());
                if (correo.getText().toString().length() ==0 && b==Boolean.FALSE) {
                    correo.setError("No puede estar vacio");
                    } else if(mather.find() != true){
                    correo.setError("Debe ingresar un mail válido.");
                }
            }
        });

        pass2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String pas1 = pass1.getText().toString();
                String pas2 = pass2.getText().toString();
                if (!pas2.equals(pas1) && !b) {
                        pass2.setError("Las contraseñas no son iguales");
                }
            }
        });

     nombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean b) {
            if (nombre.getText().toString().length() ==0 && b==Boolean.FALSE) {
                nombre.setError("Debe ingresar un nombre");
            }
        }
    });

        apellido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (apellido.getText().toString().length() ==0 && b==Boolean.FALSE) {
                    apellido.setError("Debe ingresar un apellido");
                }
            }
        });

        cedula.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Boolean rutvalido = validarRut(cedula.getText().toString());
                if (!rutvalido && !b) {
                    cedula.setError("Formato de RUT no valido");
                }
            }
        });

        nacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                txtFecha = nacimiento.getText().toString();
                veri1 = txtFecha.replaceAll("[/]","");
                veri2 = txtFecha.replaceAll("[1234567890]","");
                nacimiento.setError(null); //para evitar que el .setError anterior se mantenga
                try {
                        if (nacimiento.getText().toString().length() == 0 && b == Boolean.FALSE) {
                            Log.i("validacionFecha", "Primer IF valor vacio");
                            nacimiento.setError("Debe ingresar una fecha valida");

                        }
                        else if (txtFecha.length() == 10 && veri1.length() == 8 && veri2.length() == 2) {
                            String[] parts = txtFecha.split("/");
                            yyyy = Integer.valueOf(parts[parts.length - 1]);
                            mm = Integer.valueOf(parts[parts.length - 2]);
                            dd = Integer.valueOf(parts[parts.length - 3]);
                            if (!(yyyy > 1900 && yyyy < anio+1) || !(mm > 0 && mm < 13) || !(dd > 0 && dd < 32)) {
                                Log.i("validacionFecha", "Tercer IF Fecha no valida");
                                nacimiento.setError("Debe ingresar una fecha valida");
                            }
                        }
                        else {
                            Log.i("validacionFecha", "Segundo IF Formato invalido");
                            nacimiento.setError("Debe ingresar una fecha valida");
                        }
                }
                catch (Exception e) {
                    Log.i("validacionFecha", "Excepcion "+e);
                    nacimiento.setError("Debe ingresar una fecha valida");
                }
               Log.i("validacionFecha", "Fin validacionFecha");
            }
        });

    }

    public static boolean validarRut(String rut) {

        boolean validacion = false;
        try {
            rut =  rut.toUpperCase();
            rut = rut.replace(".", "");
            rut = rut.replace("-", "");
            int rutAux = Integer.parseInt(rut.substring(0, rut.length() - 1));
            char dv = rut.charAt(rut.length() - 1);
            int m = 0, s = 1;
            for (; rutAux != 0; rutAux /= 10) {
                s = (s + rutAux % 10 * (9 - m++ % 6)) % 11;
            }
            if (dv == (char) (s != 0 ? s + 47 : 75)) {
                validacion = true;
            }

        } catch (java.lang.NumberFormatException e) {
        } catch (Exception e) {
        }
        return validacion;
    }


    public void obtenerFecha (View fecha){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                final int mesActual = month + 1;
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                nacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);

            }
        },anio, mes, dia);
        recogerFecha.show();
    }

    public void agregarTelefono(View view) {

        Log.i("RegistroActiviy", "agregarTelefono [START]");

        if (fono.getText().toString().length() > 0) {
            // TODO: Ingresar telefono a ListView
            txtlistaFono.add(fono.getText().toString());
            telefonosArray= new ArrayList<String>(txtlistaFono);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, telefonosArray);

            listView.setAdapter(adapter);
        } else {
            fono.setError("Ingrese Teléfono");
        }
        Log.i("RegistroActiviy", "agregarTelefono [END]");
    }



}

// if (rg1.getCheckedRadioButtonId() == R.id.nombre de radiobutton)

// Toast.makeText(Registro.this,mensaje,Toast.LENGTH_SHORT).show();