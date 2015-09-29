package br.com.laboratorio.hemope.Alocacao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class AlocacaoFragment extends Fragment {

        static View alocacaoView;
        ViewPager viewPager;
        SlidingTabLayout mSlidingTabLayout;

        Itens itens;
        ProgressDialog progressDialog;

         static Spinner spinnerGaveta  = null;
         static Spinner spinnerCaixa  = null;

        public AlocacaoFragment() {


        }

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            //pega os dados da alocação da tela AlocacaoFragment
            itens = (Itens) getArguments().getSerializable("itens");

                    ((AcaoPrincipalActivity) activity).onSectionAttached(
                            getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            alocacaoView = inflater.inflate(R.layout.fragment_alocacao, container, false);
            TextView textFreezer = (TextView) alocacaoView.findViewById(R.id.dadosFreezer);
            TextView textGaveta = (TextView) alocacaoView.findViewById(R.id.dadosGaveta);
            TextView textCaixa = (TextView) alocacaoView.findViewById(R.id.dadosCaixa);
            TextView textPosicao = (TextView) alocacaoView.findViewById(R.id.dadosPosicao);

            textFreezer.setText("Freezer: " +itens.aliquota.alocacao.caixa.gaveta.freezer.codigo);
            textGaveta.setText(String.valueOf("Gaveta: "+itens.aliquota.alocacao.caixa.gaveta.idGaveta));
            textCaixa.setText(String.valueOf("Caixa: "+itens.aliquota.alocacao.caixa.idCaixa));
            textPosicao.setText("Posição: " +itens.aliquota.alocacao.posicaoY + " - " + itens.aliquota.alocacao.posicaoX);

             spinnerGaveta  = (Spinner) alocacaoView.findViewById(R.id.spinnerGaveta);
             spinnerCaixa  = (Spinner) alocacaoView.findViewById(R.id.spinnerCaixa);

            Util.DownloadTask downloadTask = new Util.DownloadTask("Carregando dados da Alocação","Aguarde...","freezer",itens,getActivity());
            downloadTask.execute("https://www.dropbox.com/s/7vc1yyk9kot53z0/aliquotaJson.json?dl=1");


            return alocacaoView;
        }




    public static void preencherSpinnerFreezer(Itens itensCarregados, final FragmentActivity context){

    try {

        final ArrayList<Freezer> freezerArrayList = new ArrayList<Freezer>();
        final ArrayList<Gaveta> gavetaArrayList = new ArrayList<>();
        final ArrayList<Caixa> caixaArrayList = new ArrayList<>();

        for(Freezer f: itensCarregados.freezers){
            freezerArrayList.add(f);
        }

        FreezerArrayAdapter adapter = new FreezerArrayAdapter(context, android.R.layout.simple_spinner_item, freezerArrayList);
        final Spinner spinner = (Spinner) alocacaoView.findViewById(R.id.spinnerFreezer);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //freezer
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Freezer f = (Freezer) parent.getSelectedItem();

                gavetaArrayList.clear();

                for(Gaveta g: f.gavetas) {
                    gavetaArrayList.add(g);

                }

                //Adapter Gaveta
                GavetaArrayAdapter adapterGaveta = new GavetaArrayAdapter(context, android.R.layout.simple_spinner_item, gavetaArrayList);
                spinnerGaveta.setAdapter(adapterGaveta);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Selections cleared.", Toast.LENGTH_SHORT).show();
            }
        });

        //Gaveta
        spinnerGaveta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Gaveta g = (Gaveta) parent.getSelectedItem();

                caixaArrayList.clear();

                for(Caixa c: g.caixas){
                    caixaArrayList.add(c);
                }

                //Adapter Caixa
                CaixaArrayAdapter adapterCaixa = new CaixaArrayAdapter(context, android.R.layout.simple_spinner_item, caixaArrayList);

                spinnerCaixa.setAdapter(adapterCaixa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Selections cleared.", Toast.LENGTH_SHORT).show();
            }
        });


        //Caixa
        spinnerCaixa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Selections cleared.", Toast.LENGTH_SHORT).show();
            }
        });

        }catch (Exception e){
        Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",context);

        }

    }




}