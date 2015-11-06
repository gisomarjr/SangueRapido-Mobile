package br.com.laboratorio.hemope.Caixa;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.R;

public class CaixaItemActivity extends AppCompatActivity {

    TableLayout table_layout;
    ArrayList<Alocacao> alocacoes;

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
                                Toast.makeText(getApplicationContext(),"oi",Toast.LENGTH_SHORT).show();
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



}