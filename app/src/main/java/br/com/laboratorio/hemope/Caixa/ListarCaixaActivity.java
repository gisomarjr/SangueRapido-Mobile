package br.com.laboratorio.hemope.Caixa;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Gavetas.ListaGavetasFragment;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.R;

public class ListarCaixaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_caixa);


        if (savedInstanceState == null) {
            ArrayList<Caixa> listCaixas = (ArrayList<Caixa>) getIntent().getSerializableExtra("caixas");

            ListarCaixaFragment listarCaixaFragment = ListarCaixaFragment.novaInstancia(listCaixas);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, listarCaixaFragment, "detalhe")
                    .commit();
        }
    }

}