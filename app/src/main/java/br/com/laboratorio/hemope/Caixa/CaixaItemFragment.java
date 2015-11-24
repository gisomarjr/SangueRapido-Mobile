package br.com.laboratorio.hemope.Caixa;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;

/**
 * Created by User on 28/10/2015.
 */
public class CaixaItemFragment extends Fragment {


    TableLayout table_layout;
    ArrayList<Alocacao> alocacoes;
    private static final String ARG_SECTION_NUMBER = "section_number";
    static Itens _itens;
    Caixa caixa;
    static Caixa mCaixa;
    public ArrayList<Caixa> listaCaixas = new ArrayList<>();

    static ArrayList<Caixa> mListaCaixas = new ArrayList<>();
    View view;

    public static CaixaItemFragment novaInstancia(Caixa caixas) {

        CaixaItemFragment dpf = new CaixaItemFragment();
        Bundle args = new Bundle();
        args.putSerializable("caixas", caixas);

        mCaixa = caixas;
        dpf.setArguments(args);
        return dpf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setContentView(R.layout.caixa_tabela);
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private  void BuildTable(int rows, int cols) {

        // outer for loop
        for (int i = 1; i <= rows; i++) {
            //j coluna i linha
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));


            // inner for loop
            for (int j = 1; j <= cols; j++) {

                TextView tv = new TextView(getActivity());

                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.alocar);
                tv.setPadding(5, 5, 5, 5);
                tv.setClickable(true);
                tv.setHeight(100);
                tv.setWidth(100);

                tv.setBackground(getResources().getDrawable(R.drawable.naotemaliquota));

                //tv.setText("L " + i + ", C" + j);
                //  tv.setText("nada");

                for (final Alocacao alocacao : alocacoes) {

                    //if((Integer.parseInt(alocacao.posicaoY) == j && Integer.parseInt(alocacao.posicaoX) == i)){
                    //Log.i("debug", Util.decimalParaSimbolos(j, "abcdefghijklmnopqrstuvwxyz"));
                    Log.i("tamanho: " + alocacoes.size() + " alocacao - x ", alocacao.posicaoX + " y: " + alocacao.posicaoY);
                    //tv.setText(Util.decimalParaSimbolos(j, "abcdefghijklmnopqrstuvwxyz"));
                    if (j == Integer.parseInt(alocacao.posicaoX) && i == Integer.parseInt(alocacao.posicaoY) && alocacao.aliquota != null) {
                        // tv.setText("alocado");
                        // tv.setText(alocacao.aliquota.codigo);

                        tv.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(getActivity(), alocacao.aliquota.codigo, Toast.LENGTH_SHORT).show();

                            }
                        });
                        tv.setBackground(getResources().getDrawable(R.drawable.temaliquota));

                    }

                    //}

                }
                row.addView(tv);

            }

            table_layout.addView(row);

        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // listaCaixas = (ArrayList<Caixa>) getArguments().getSerializable("caixas");
        view = inflater.inflate(R.layout.caixa_tabela, container, false);
        Integer x = mCaixa.qtdX;
        Integer y = mCaixa.qtdY;
        alocacoes = mCaixa.alocacoes;

        table_layout = (TableLayout) view.findViewById(R.id.tableLayout1);

        table_layout.removeAllViews();

        BuildTable(x, y);
        Log.i("valores", "linha " + x + " coluna y " + y);
        return view;
    }
}