package br.com.laboratorio.hemope.Aliquota;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import br.com.laboratorio.hemope.R;

/**
 *  Atividade responsável por listar a Aliquota
 */
public class AliquotaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aliquota);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AliquotaFragment())
                    .commit();
        }

    }




}
