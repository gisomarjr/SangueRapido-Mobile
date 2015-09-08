package br.com.laboratorio.hemope.Diagnostico;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.R;

public class DetalheDiagnosticoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_diagnostico);
        if (savedInstanceState == null) {
            Diagnostico diagnostico = (Diagnostico) getIntent().getSerializableExtra("diagnostico");

            DetalheDiagnosticoFragment detalheDiagnosticoFragmentFragment =
                    DetalheDiagnosticoFragment.novaInstancia(diagnostico);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheDiagnosticoFragmentFragment, "detalhe")
                    .commit();
        }
    }

}
