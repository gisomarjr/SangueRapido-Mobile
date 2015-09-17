package br.com.laboratorio.hemope.Amostra;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.R;

public class DetalheAmostraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_amostra);
        if (savedInstanceState == null) {
            Amostra amostra = (Amostra) getIntent().getSerializableExtra("amostra");

            DetalheAmostraFragment detalheAmostraFragmentFragment =
                    DetalheAmostraFragment.novaInstancia(amostra);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheAmostraFragmentFragment, "detalhe")
                    .commit();
        }
    }

}
