package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;


public class Incidentes extends AppCompatActivity {

    EditText correoE,fecha,reportes;
    Button enviar;
    public static final String DATABASE_NAME = "androidapp";
    public static final String url = "jdbc:mysql://instanciabase.czusf9ginxup.us-east-1.rds.amazonaws.com:3306/" +
            DATABASE_NAME;
    public static final String username = "admin", password = "admin123";

    public static final String TABLE_NAME = "reportes";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incidentes);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String email= user.getEmail();
        correoE= findViewById(R.id.txtCorreo2);
        correoE.setText(email);
        reportes=findViewById(R.id.txtIncidencia2);
        String date = String.valueOf(Calendar.getInstance().getTime());
        fecha= findViewById(R.id.txtFecha2);
        fecha.setText(date);
        enviar= findViewById(R.id.btnEnviar);
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rep= reportes.getText().toString();
                String corr= correoE.getText().toString();
                String fech= fecha.getText().toString();
                insertData(corr,fech,rep);
            }
        });
    }

    public void insertData(String email, String date, String incidencia) {
        new Thread(() -> {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection connection = DriverManager.getConnection(url, username, password);
                Statement statement = connection.createStatement();
                statement.execute("INSERT INTO " + TABLE_NAME + "(mailReporte, fechaReporte,explReporte) VALUES('" + email + "', '" + date + "', '" + incidencia + "')");
                connection.close();
                finish();
                startActivity(new Intent(Incidentes.this, PantallaPrincipal.class));

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

}