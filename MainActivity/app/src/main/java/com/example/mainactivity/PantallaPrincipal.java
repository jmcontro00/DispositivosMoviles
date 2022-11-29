package com.example.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class PantallaPrincipal extends AppCompatActivity {

    List<ListElement> elements;
    ResultSet rs;
    RecyclerView recyclerView;
    ListAdapter listAdapter;
    Context c;
    FloatingActionButton incidencias;

    public static final String DATABASE_NAME = "androidapp";
    public static final String url = "jdbc:mysql://instanciabase.czusf9ginxup.us-east-1.rds.amazonaws.com:3306/" +
            DATABASE_NAME;
    public static final String username = "admin", password = "admin123";

    public static final String TABLE_NAME = "accesos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        incidencias=(FloatingActionButton) findViewById(R.id.incidencia);

        incidencias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PantallaPrincipal.this, Incidentes.class);
                startActivity(intent);
            }
        });

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
            Intent intent = new Intent(PantallaPrincipal.this, Perfil.class);
            startActivity(intent);
        } else if (id == R.id.normas) {
            Intent intent = new Intent(PantallaPrincipal.this, Reglamento.class);
            startActivity(intent);
        } else if (id == R.id.actlz) {
            Intent intent = new Intent(PantallaPrincipal.this, Actualizar.class);
            startActivity(intent);
        } else if (id == R.id.salir) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(PantallaPrincipal.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(PantallaPrincipal.this,"Se cerró la sesión.",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void utilFun() throws SQLException {

        new Thread(() -> {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();

                rs = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
                elements = new ArrayList<>();

                while (rs.next()) {
                    elements.add(new ListElement(rs.getString(1),rs.getString(2)));
                }

                c = this;
                listAdapter = new ListAdapter(elements, this);
                recyclerView = findViewById(R.id.listRecyclerView);

                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(c));
                    recyclerView.setAdapter(listAdapter);
                }
            });
        }).start();
    }

    public void updateFunc(View view) {
        startActivity(new Intent(PantallaPrincipal.this, Actualizar.class));
    }
}