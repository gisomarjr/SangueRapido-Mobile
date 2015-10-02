package br.com.laboratorio.hemope.Alocacao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Aliquota.AliquotaFragment;
import br.com.laboratorio.hemope.Model.Alocacao;
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

        static Itens itens;
        ProgressDialog progressDialog;

         static Spinner spinnerGaveta  = null;
         static Spinner spinnerCaixa  = null;
         static Spinner spinnerAlocacao = null;

        static String idAlocacaoAtualizar;

        public AlocacaoFragment() {


        }

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            //pega os dados da alocação da tela AlocacaoFragment
            itens = (Itens) getArguments().getSerializable("itens");

                    ((AcaoPrincipalActivity) activity).onSectionAttached(33);
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
             spinnerAlocacao = (Spinner) alocacaoView.findViewById(R.id.spinnerAlocacao);

            String urlGeral = alocacaoView.getResources().getString(R.string.urlGeralWebService);
            String urlSecundaria = alocacaoView.getResources().getString(R.string.urlGeralWebServiceConsultarFreezer);

            Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde...","Carregando dados da Alocação","freezer",itens,getActivity());
            downloadTask.execute(urlGeral + urlSecundaria);

            Button button = (Button) alocacaoView.findViewById(R.id.btnAlterarAlocacao);

            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    String urlGeral = alocacaoView.getResources().getString(R.string.urlGeralWebService);
                    String urlSecundaria = alocacaoView.getResources().getString(R.string.urlGeralWebServiceAlterarAlocacao);

                    Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde...","Alterando dados da Alocação","alterarDadosAlocacao",itens,getActivity());
                    downloadTask.execute(urlGeral + urlSecundaria + "?idAliquota=" + itens.aliquota.idAliquota + "&idAlocacao=" + idAlocacaoAtualizar);
                    Log.i("linkAlterarAlocacao",urlGeral + urlSecundaria + "?idAliquota=" + itens.aliquota.idAliquota + "&idAlocacao=" + idAlocacaoAtualizar);

                }
            });

            return alocacaoView;
        }

    //verificando se obteve sucesso na atualização da aliquota
    public static void atualizarTelaAlterarAlocacao(Itens itensCarregados, FragmentActivity context){

        if(itensCarregados.isSuccess){
            Util.exibirMensagem("Sucesso","Aliquota Atualizada com Sucesso...", context);

            String urlGeral = alocacaoView.getResources().getString(R.string.urlGeralWebService);
            String urlSecundaria = alocacaoView.getResources().getString(R.string.urlGeralWebServiceConsultarAliquota);

            Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Atualizando dados da Aliquota...","atualizaTelaAlocacaoDados",itens,context);
            downloadTask.execute(urlGeral + urlSecundaria + "?codigoAliquota=" + itens.aliquota.codigo);
            Log.i("link",urlGeral + urlSecundaria + "?codigoAliquota=" + itens.aliquota.codigo);

        }else{
            Util.exibirMensagem("Erro",itensCarregados.errorMessage, context);
        }

    }
    //obteve sucesso na alteração da aliquota e atualiza a tela
    public static void atualizaTelaAlocacaoDados(Itens itensCarregados, FragmentActivity context){

        TextView textFreezer = (TextView) alocacaoView.findViewById(R.id.dadosFreezer);
        TextView textGaveta = (TextView) alocacaoView.findViewById(R.id.dadosGaveta);
        TextView textCaixa = (TextView) alocacaoView.findViewById(R.id.dadosCaixa);
        TextView textPosicao = (TextView) alocacaoView.findViewById(R.id.dadosPosicao);

        textFreezer.setText("Freezer: " +itensCarregados.aliquota.alocacao.caixa.gaveta.freezer.codigo);
        textGaveta.setText(String.valueOf("Gaveta: "+itensCarregados.aliquota.alocacao.caixa.gaveta.idGaveta));
        textCaixa.setText(String.valueOf("Caixa: "+itensCarregados.aliquota.alocacao.caixa.idCaixa));
        textPosicao.setText("Posição: " +itensCarregados.aliquota.alocacao.posicaoY + " - " + itensCarregados.aliquota.alocacao.posicaoX);
    }


    public static void preencherSpinnerFreezer(Itens itensCarregados, final FragmentActivity context){

    try {

        final ArrayList<Freezer> freezerArrayList = new ArrayList<Freezer>();
        final ArrayList<Gaveta> gavetaArrayList = new ArrayList<>();
        final ArrayList<Caixa> caixaArrayList = new ArrayList<>();
        final ArrayList<Alocacao> alocacaoArrayList = new ArrayList<>();

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

                Caixa c = (Caixa) parent.getSelectedItem();

                alocacaoArrayList.clear();

                for(Alocacao a: c.alocacoes){

                    alocacaoArrayList.add(a);
                }

                //Adapter Caixa
                AlocacaoArrayAdapter adapterAlocacao = new AlocacaoArrayAdapter(context, android.R.layout.simple_spinner_item, alocacaoArrayList);

                spinnerAlocacao.setAdapter(adapterAlocacao);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(context, "Selections cleared.", Toast.LENGTH_SHORT).show();
            }
        });


        //Caixa
        spinnerAlocacao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                Alocacao alocacao = (Alocacao) parent.getSelectedItem();
                idAlocacaoAtualizar = String.valueOf(alocacao.idAlocacao);

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