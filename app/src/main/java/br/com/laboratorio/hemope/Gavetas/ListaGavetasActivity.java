package br.com.laboratorio.hemope.Gavetas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.R;

public class ListaGavetasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_gavetas);

        if (savedInstanceState == null) {
            ArrayList<Gaveta> listGavetas = (ArrayList<Gaveta>) getIntent().getSerializableExtra("gavetas");
          //  Toast.makeText(this, "" + listGavetas.get(0).idGaveta, Toast.LENGTH_LONG).show();
            ListaGavetasFragment listaGavetasFragment = ListaGavetasFragment.novaInstancia(listGavetas);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listaGavetasFragment, "detalhe")
                    .commit();
        }
    }
}

