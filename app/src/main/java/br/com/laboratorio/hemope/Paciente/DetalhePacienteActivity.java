package br.com.laboratorio.hemope.Paciente;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;

public class DetalhePacienteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_paciente);
        if (savedInstanceState == null) {
            Paciente paciente = (Paciente) getIntent().getSerializableExtra("paciente");

            DetalhePacienteFragment detalhePacienteFragmentFragment =
                    DetalhePacienteFragment.novaInstancia(paciente);

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalhePacienteFragmentFragment, "detalhe")
                    .commit();
        }
    }

}
