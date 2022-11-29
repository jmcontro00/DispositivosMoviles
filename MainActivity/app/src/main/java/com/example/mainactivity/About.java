package com.example.mainactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.mainactivity.FragmentsAbout.BlankFragment;
import com.example.mainactivity.FragmentsAbout.InfoApp;
import com.example.mainactivity.FragmentsAbout.InfoDev;
import com.example.mainactivity.FragmentsAbout.InfoEmpresa;

public class About extends AppCompatActivity {

    FragmentTransaction transaction;
    Fragment fragmentEmpresa, fragmentDesarollador, fragmentApp, fragmentBlanco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        fragmentEmpresa= new InfoEmpresa();
        fragmentDesarollador= new InfoDev();
        fragmentApp= new InfoApp();
        fragmentBlanco= new BlankFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments,fragmentBlanco).commit();
    }


    public void onClick (View view){
        transaction=getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.btnEmpresa:
                transaction.replace(R.id.contenedorFragments,fragmentEmpresa).commit();
                break;
            case R.id.btnDesarollador:
                transaction.replace(R.id.contenedorFragments,fragmentDesarollador).commit();
                break;
            case R.id.btnApp:
                transaction.replace(R.id.contenedorFragments,fragmentApp).commit();
                break;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.overflow, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id= item.getItemId();

        if(id == R.id.inicio){
            Intent intent=new Intent(About.this,MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.acercade){
            Toast.makeText(this,"Ya te encuentras en esta pantalla...",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}