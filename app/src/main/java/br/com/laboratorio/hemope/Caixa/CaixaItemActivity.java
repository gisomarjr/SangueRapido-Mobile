package br.com.laboratorio.hemope.Caixa;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Aliquota.AliquotaFragment;
import br.com.laboratorio.hemope.Alocacao.AlocacaoFragment;
import br.com.laboratorio.hemope.Amostra.ListaAmostrasFragment;
import br.com.laboratorio.hemope.Diagnostico.ListaDiagnosticosFragment;
import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Paciente.ListaPacientesFragment;
import br.com.laboratorio.hemope.R;

public class CaixaItemActivity extends AppCompatActivity {

    TableLayout table_layout;
    ArrayList<Alocacao> alocacoes;
    private static final String ARG_SECTION_NUMBER = "section_number";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caixa_item);

        Integer x = getIntent().getIntExtra("x",0);
        Integer y = getIntent().getIntExtra("y",0);
        alocacoes = (ArrayList<Alocacao>) getIntent().getSerializableExtra("alocacao");

        table_layout = (TableLayout) findViewById(R.id.tableLayout1);

        table_layout.removeAllViews();

        BuildTable(x, y);
        Log.i("valores", "linha " + x + " coluna y " + y);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private  void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {
            //j coluna i linha
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));



            // inner for loop
            for (int j = 1; j <= cols; j++) {

                TextView tv = new TextView(this);

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.alocar);
                tv.setPadding(5, 5, 5, 5);
                tv.setClickable(true);
                tv.setBackground(getResources().getDrawable(R.drawable.naoalocado4));

                //tv.setText("L " + i + ", C" + j);
              //  tv.setText("nada");

                for(final Alocacao alocacao : alocacoes){

                    //if((Integer.parseInt(alocacao.posicaoY) == j && Integer.parseInt(alocacao.posicaoX) == i)){
                        //Log.i("debug", Util.decimalParaSimbolos(j, "abcdefghijklmnopqrstuvwxyz"));
                    Log.i("tamanho: " + alocacoes.size() + " alocacao - x ", alocacao.posicaoX + " y: " + alocacao.posicaoY);
                    //tv.setText(Util.decimalParaSimbolos(j, "abcdefghijklmnopqrstuvwxyz"));
                    if(j == Integer.parseInt(alocacao.posicaoX) && i == Integer.parseInt(alocacao.posicaoY))
                    {
                       // tv.setText("alocado");
                       // tv.setText(alocacao.aliquota.codigo);
                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                if(alocacao.aliquota != null){
                                    Toast.makeText(getApplicationContext(),"aliquota: " + alocacao.aliquota.codigo,Toast.LENGTH_SHORT).show();

                                    Itens itensAliquota = new Itens();
                                    itensAliquota.aliquota = alocacao.aliquota;

                                    FragmentTransaction transaction = getSupportFragmentManager()
                                            .beginTransaction();
                                    Bundle argsaLocacaoFragment = new Bundle();
                                    argsaLocacaoFragment.putInt(ARG_SECTION_NUMBER, 5);
                                    argsaLocacaoFragment.putSerializable("itens", itensAliquota);
                                    Fragment novaAlocacaoFragment = new AliquotaFragment(itensAliquota);

                                    novaAlocacaoFragment.setArguments(argsaLocacaoFragment);
                                    transaction.addToBackStack(null);
                                    transaction.replace(R.id.container, novaAlocacaoFragment);
                                    transaction.commit();

                                }else{

                                    Toast.makeText(getApplicationContext(),"Não contém aliquota",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                       tv.setBackground(getResources().getDrawable(R.drawable.alocado4));

                    }

                    //}

                }
                row.addView(tv);

            }

            table_layout.addView(row);

        }
    }

    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static android.support.v4.app.Fragment newInstance(int sectionNumber,String idAliquota) {

            AliquotaFragment aliquotaFragment = new AliquotaFragment();
            Bundle argsaliquotaFragment = new Bundle();
            argsaliquotaFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
            argsaliquotaFragment.putString("idAliquota", idAliquota);
            aliquotaFragment.setArguments(argsaliquotaFragment);
            return aliquotaFragment;

        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_acao_principal, container, false);
            return rootView;
        }


    }

}
