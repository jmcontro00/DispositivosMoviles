package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Actualizar extends AppCompatActivity {

    Spinner spinnerID, spinnerStatus;
    List<String> idsList;
    String[] idsArray;
    String status_str, id_str;
    FloatingActionButton incidencias;
    private static final String[] statusArray = {"Ocupado", "Activo"};

    public static final String DATABASE_NAME = "androidapp";
    public static final String url = "jdbc:mysql://instanciabase.czusf9ginxup.us-east-1.rds.amazonaws.com:3306/" +
            DATABASE_NAME;
    public static final String username = "admin", password = "admin123";

    public static final String TABLE_NAME = "accesos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        incidencias=(FloatingActionButton) findViewById(R.id.incidencia);

        incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Actualizar.this, Incidentes.class);
                startActivity(intent);
            }
        });

        spinnerID = findViewById(R.id.idSpinner);
        spinnerStatus = findViewById(R.id.statusSpinner);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            utilFun();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow2, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.perfil) {
            Intent intent = new Intent(Actualizar.this, Perfil.class);
            startActivity(intent);
        } else if (id == R.id.normas) {
            Intent intent = new Intent(Actualizar.this, Reglamento.class);
            startActivity(intent);
        } else if (id == R.id.actlz) {
            Toast.makeText(this, "Ya te encuentras en la pantalla...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.salir) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(Actualizar.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(Actualizar.this,"Se cerró la sesión.",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void utilFun() throws SQLException {
        new Thread(() -> {
            //do your work

            StringBuilder records = new StringBuilder();
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();

                ResultSet rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                idsList = new ArrayList<>();

                while (rs.next()) {
                    idsList.add(rs.getString(1));
                }

                idsArray = idsList.toArray(new String[0]);


                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // after the job is finished:

                    ArrayAdapter<String> adapterID = new ArrayAdapter<String>(Actualizar.this,
                            android.R.layout.simple_spinner_item, idsArray);
                    ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(Actualizar.this,
                            android.R.layout.simple_spinner_item, statusArray);
                    adapterID.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerID.setAdapter(adapterID);
                    adapterStatus.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerStatus.setAdapter(adapterStatus);
                }
            });
        }).start();

    }

    public void update(View view) throws SQLException {
        new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                Statement statement2= connection.createStatement();
                Statement statement3= connection.createStatement();

                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
                String email= user.getEmail();
                String date = String.valueOf(Calendar.getInstance().getTime());

                id_str = spinnerID.getSelectedItem().toString();
                status_str = spinnerStatus.getSelectedItem().toString();

                statement.execute("UPDATE " + TABLE_NAME + " SET stsaccesos = '" + status_str + "' where idaccesos = " + id_str);
                statement2.execute("UPDATE " + TABLE_NAME + " SET mail = '" + email + "' where idaccesos = " + id_str);
                statement3.execute("UPDATE " + TABLE_NAME + " SET fechaCambio = '" + date + "' where idaccesos = " + id_str);
                connection.close();
                finish();
                startActivity(new Intent(Actualizar.this, PantallaPrincipal.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();


    }

}