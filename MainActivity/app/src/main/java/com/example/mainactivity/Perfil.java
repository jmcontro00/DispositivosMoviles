package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Perfil extends AppCompatActivity {

    EditText correoE;
    FloatingActionButton incidencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email= user.getEmail();
        incidencias=(FloatingActionButton) findViewById(R.id.incidencia);

        incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Perfil.this, Incidentes.class);
                startActivity(intent);
            }
        });


        correoE= findViewById(R.id.txtMEmail);
        correoE.setText(email);

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.perfil) {
            Toast.makeText(this, "Ya te encuentras en la pantalla...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.normas) {
            Intent intent = new Intent(Perfil.this, Reglamento.class);
            startActivity(intent);
        } else if (id == R.id.actlz) {
            Intent intent = new Intent(Perfil.this, Actualizar.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Perfil.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(Perfil.this,"Se cerró la sesión.",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}