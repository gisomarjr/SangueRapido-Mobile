package br.com.laboratorio.hemope.Caixa;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;

public class CaixaItemActivity extends AppCompatActivity {

    TableLayout table_layout;
    ArrayList<Alocacao> alocacoes;

    private static final String ARG_SECTION_NUMBER = "section_number";
    static Itens _itens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa_item);
        if (savedInstanceState == null) {
            Caixa caixa = new Caixa();


            caixa.qtdX = (int) getIntent().getSerializableExtra("x");
            caixa.qtdY = (int) getIntent().getSerializableExtra("y");
            caixa.alocacoes = new ArrayList<Alocacao>();
            caixa.alocacoes = (ArrayList<Alocacao>) getIntent().getSerializableExtra("alocacao");
            //Toast.makeText(this, "" + caixa.qtdX, Toast.LENGTH_LONG).show();
            //Toast.makeText(this, "" + caixa.qtdY, Toast.LENGTH_LONG).show();
            if (caixa.alocacoes.size()>0) {
                //     Toast.makeText(this, "" + caixa.alocacoes.get(0).aliquota.codigo, Toast.LENGTH_LONG).show();
            }
            CaixaItemFragment caixaItemFragment = CaixaItemFragment.novaInstancia(caixa);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, caixaItemFragment, "detalhe")
                    .commit();

        }
    }
}