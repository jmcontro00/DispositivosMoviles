package com.example.mainactivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    EditText mail,contraseña;
    Button ingresar;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mail= findViewById(R.id.txtUser);
        contraseña= findViewById(R.id.txtPwd);
        ingresar= findViewById(R.id.btnIngresar);
        auth= FirebaseAuth.getInstance();

        ingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Email= mail.getText().toString().trim();
                String Password= contraseña.getText().toString().trim();

                if(TextUtils.isEmpty(Email)){
                    mail.setError("Ingrese su usuario.");
                }
                if(TextUtils.isEmpty(Password)){
                    contraseña.setError("Ingrese su contraseña.");
                }

                else
                    auth.signInWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){
                                Intent intent= new Intent(MainActivity.this,PantallaPrincipal.class);
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(MainActivity.this, "Credenciales erróneas.", Toast.LENGTH_SHORT).show();

                        }
                    });
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.inicio) {
            Toast.makeText(this, "Ya te encuentras en la pantalla...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.acercade) {
            Intent intent = new Intent(MainActivity.this, About.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}