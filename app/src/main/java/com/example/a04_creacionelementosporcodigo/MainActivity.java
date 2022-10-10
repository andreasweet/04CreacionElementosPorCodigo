package com.example.a04_creacionelementosporcodigo;

import android.content.Intent;
import android.os.Bundle;

import com.example.a04_creacionelementosporcodigo.modelos.Alumno;
import com.google.android.material.snackbar.Snackbar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;


import com.example.a04_creacionelementosporcodigo.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    //1. Contenedor donde Mostrar Informacion -> Scroll con un Linear dentro
    //2. Logica para pintar los elementos -> pintarElementos();
    //3. Conjunto de datos
    private ArrayList<Alumno> alumnoList;

    private ActivityResultLauncher<Intent> launcherCrearAlumnos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        alumnoList = new ArrayList<>();

        inicializaLauncher();

        setSupportActionBar(binding.toolbar);


        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            launcherCrearAlumnos.launch(new Intent(MainActivity.this, AddAlumnoActivity.class));
            }
        });
    }

    private void inicializaLauncher() {
        launcherCrearAlumnos = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == RESULT_OK){
                            if(result.getData() != null  && result.getData().getExtras() != null){
                                Alumno alumno =(Alumno) result.getData().getExtras().getSerializable("ALUMNO");
                                alumnoList.add(alumno);
                                pintarElementos();
                            }
                        }
                    }
                }
        );
    }

    private void pintarElementos() {
        binding.content.contenedor.removeAllViews();
        for (Alumno alumno: alumnoList ) {
            TextView txtAlumno = new TextView(MainActivity.this);
            txtAlumno.setText(alumno.toString());
            binding.content.contenedor.addView(txtAlumno);

        }
    }


}