package br.com.laboratorio.hemope.Aliquota;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Alocacao.AlocacaoFragment;
import br.com.laboratorio.hemope.Model.Aliquota;
import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Cid;
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.LocalProcedencia;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.Model.TipoAmostra;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class AliquotaFragment extends Fragment {

        static View aliquotaView;
        ViewPager viewPager;
        SlidingTabLayout mSlidingTabLayout;


        static Itens _itens;
        ProgressDialog progressDialog;

        public AliquotaFragment() {

        }

        public void lerQrCod(){
            try {
                Intent intent = new Intent(ACTION_SCAN);
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                startActivityForResult(intent, 0);
            } catch (ActivityNotFoundException anfe) {
                Util.showDialog(getActivity(), "Sem Scanner Encontrado!", "Baixar um Scanner agora?", "Sim", "Não").show();
            }
        }

        //Qr Code
        static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            if(!getArguments().containsKey("idAliquota")) {

                if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {
                    lerQrCod();
                }
            }else{
                String urlGeral = aliquotaView.getResources().getString(R.string.urlGeralWebService);
                String urlSecundaria = aliquotaView.getResources().getString(R.string.urlGeralWebServiceConsultarAliquota);

                Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Carregando dados da Aliquota...","aliquota",_itens,getActivity());
                downloadTask.execute(urlGeral + urlSecundaria + "?codigoAliquota=" + getArguments().getString("idAliquota"));
                Log.i("link",urlGeral + urlSecundaria + "?codigoAliquota=" + getArguments().getString("idAliquota"));
            }

            ((AcaoPrincipalActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            setHasOptionsMenu(true);

            aliquotaView = inflater.inflate(R.layout.fragment_aliquota, container, false);

            return aliquotaView;
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // TODO Add your menu entries here
            inflater.inflate(R.menu.menu_aliquota, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case R.id.novaConsultaAliquota:
                    lerQrCod();
                    break;

                case R.id.novaAlocacaoAliquota:

                    try {
                        if(_itens.aliquota.idAliquota != null) {
                            FragmentTransaction transaction = getFragmentManager()
                                    .beginTransaction();
                            Bundle argsaLocacaoFragment = new Bundle();
                            argsaLocacaoFragment.putInt(ARG_SECTION_NUMBER, 5);
                            argsaLocacaoFragment.putSerializable("itens", _itens);
                            Fragment novaAlocacaoFragment = new AlocacaoFragment();

                            novaAlocacaoFragment.setArguments(argsaLocacaoFragment);
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.container, novaAlocacaoFragment);
                            transaction.commit();
                        }
                    }catch (java.lang.NullPointerException objetoNull){
                        Toast.makeText(getActivity(), "É necessário consultar uma Aliquota para realizar a alocação.", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
            return true;

        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            if (requestCode == 0) {
                if (resultCode == Activity.RESULT_OK) {
                    String idAliquota = intent.getStringExtra("SCAN_RESULT");
                    String formato = intent.getStringExtra("SCAN_RESULT_FORMAT").trim();

                    Log.e("qtdFormato",formato.length()+"");
                    Log.e("formato",formato);

                    //Verifico se é um QRCODE
                    if(formato.equals("QR_CODE")) {

                         try{
                             //Verifico se é um número
                             //if(Integer.parseInt(idAliquota) > 0) {

                                 String urlGeral = aliquotaView.getResources().getString(R.string.urlGeralWebService);
                                 String urlSecundaria = aliquotaView.getResources().getString(R.string.urlGeralWebServiceConsultarAliquota);

                                 Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Carregando dados da Aliquota...","aliquota",_itens,getActivity());
                                 downloadTask.execute(urlGeral + urlSecundaria + "?codigoAliquota=" + idAliquota);
                                 Log.i("link",urlGeral + urlSecundaria + "?codigoAliquota=" + idAliquota);

                            /* }else{
                                 Toast.makeText(getActivity(), "ID da Aliquota Inválido", Toast.LENGTH_LONG).show();
                             }*/

                         } catch (NumberFormatException e) {
                             Toast.makeText(getActivity(), "QRCODE inválido.", Toast.LENGTH_LONG).show();
                         }

                       }else{
                        Toast.makeText(getActivity(), "Formato não reconhecido para consultar uma Aliquota :" + formato , Toast.LENGTH_LONG).show();
                    }
                }
            }
        }




    public static void preencherActivityAliquota(Itens itens, FragmentActivity context){

        Paciente paciente = new Paciente();
        Aliquota aliquota = new Aliquota();
        Amostra amostra = new Amostra();
        Alocacao alocacao = new Alocacao();
        Caixa caixa = new Caixa();
        Gaveta gaveta = new Gaveta();
        Freezer freezer = new Freezer();
        Diagnostico diagnostico = new Diagnostico();
        Cid cid = new Cid();
        TipoAmostra tipoAmostra = new TipoAmostra();
        LocalProcedencia localProcedencia = new LocalProcedencia();

    try {
             _itens = itens;
          if(_itens.aliquota != null) {
            aliquota = itens.aliquota;
            amostra = itens.aliquota.amostra;
            alocacao = itens.aliquota.alocacao;
            paciente = itens.aliquota.amostra.paciente;
            caixa = itens.aliquota.alocacao.caixa;
            gaveta = itens.aliquota.alocacao.caixa.gaveta;
            freezer = itens.aliquota.alocacao.caixa.gaveta.freezer;
            localProcedencia = itens.aliquota.amostra.localProcedencia;
            tipoAmostra = itens.aliquota.amostra.tipoAmostra;


            TextView txtNomePaciente = (TextView) aliquotaView.findViewById(R.id.labelNomePaciente);
            TextView txtNomeMae = (TextView) aliquotaView.findViewById(R.id.labelNomeMae);
            TextView txtCPF = (TextView) aliquotaView.findViewById(R.id.labelCPF);
            TextView txtTelefone = (TextView) aliquotaView.findViewById(R.id.labelTelefone);


            TextView txtDataEntrada = (TextView) aliquotaView.findViewById(R.id.labelDataEntrada);
            TextView txtDataDescarte = (TextView) aliquotaView.findViewById(R.id.labelDataDescarte);
            TextView txtCaixa = (TextView) aliquotaView.findViewById(R.id.labelIdCaixa);
            TextView txtGaveta = (TextView) aliquotaView.findViewById(R.id.labelIdGaveta);
            TextView txtFreezer = (TextView) aliquotaView.findViewById(R.id.labelCodigoFreezer);
            TextView txtPosicao = (TextView) aliquotaView.findViewById(R.id.labelPosicao);

            TextView txtVolume = (TextView) aliquotaView.findViewById(R.id.labelVolume);
            TextView txtConcentracao = (TextView) aliquotaView.findViewById(R.id.labelConcentracao);
            TextView txtCodAmostra = (TextView) aliquotaView.findViewById(R.id.labelCodigoAmostra);
            TextView txtDataEntradaAmostra = (TextView) aliquotaView.findViewById(R.id.labelDataEntradaAmostra);
            TextView txtTipoAmostra = (TextView) aliquotaView.findViewById(R.id.labelTipoAmostra);
            TextView txtLocalProcedencia = (TextView) aliquotaView.findViewById(R.id.labelLocalProcedencia);
            TextView txtCodDiagnostico = (TextView) aliquotaView.findViewById(R.id.labelCodDiagnostico);
            TextView txtSiglaDiagnostico = (TextView) aliquotaView.findViewById(R.id.labelSiglaDiagnostico);
            TextView txtCodCid = (TextView) aliquotaView.findViewById(R.id.labelCodCid);
            TextView txtDescricaoCid = (TextView) aliquotaView.findViewById(R.id.labelDescricaoCid);

              if(itens.diagnosticos != null) {
                  diagnostico = itens.aliquota.amostra.diagnostico;
                  cid = itens.aliquota.amostra.diagnostico.cid;
                  txtCodDiagnostico.setText("Cod. Diagnóstico: " + diagnostico.codigo);
                  txtSiglaDiagnostico.setText("Sigla Diagnóstico: " + diagnostico.sigla);
                  txtCodCid.setText("Cod. CID: " + cid.codigo);
                  txtDescricaoCid.setText("Sigla CID: " + cid.descricao);
              }else{
                  txtCodDiagnostico.setText("Cod. Diagnóstico: -");
                  txtSiglaDiagnostico.setText("Sigla Diagnóstico: -");
                  txtCodCid.setText("Cod. CID: -");
                  txtDescricaoCid.setText("Sigla CID: -");
              }

            txtNomePaciente.setText("Nome do Paciente: " + paciente.nome);
            txtNomeMae.setText("Nome da Mãe: " + paciente.nomeMae);
            txtCPF.setText("CPF: " + paciente.cpf);
            txtTelefone.setText("Telefone: " + paciente.telefone);

            txtDataEntrada.setText("Data de Entrada: " + aliquota.dataEntrada);
            txtDataDescarte.setText("Data de Descarte: " + aliquota.dataDescarte);
            txtPosicao.setText("Coluna: " + alocacao.posicaoX + " Linha: " + alocacao.posicaoY);
            txtCaixa.setText("Caixa: " + caixa.idCaixa);
            txtGaveta.setText("Gaveta: " + gaveta.idGaveta);
            txtFreezer.setText("Código do Freezer: " + freezer.codigo);

            txtVolume.setText("Volume: " + String.valueOf(aliquota.volume));
            txtConcentracao.setText("Concentração: " + String.valueOf(aliquota.concentracao));
            txtCodAmostra.setText("Cod. Amostra: " + amostra.codigo);
            txtDataEntradaAmostra.setText("Data de Entrada: " + amostra.dataEntrada);
            txtTipoAmostra.setText("Tipo da Amostra: " + amostra.tipoAmostra.nome);
            txtLocalProcedencia.setText("local Procedência: " + amostra.localProcedencia.nome);

            Toast.makeText(context, "Carregamento Concluído.", Toast.LENGTH_SHORT).show();
        }else{
            Util.exibirMensagem("Aliquota","Nenhuma Aliquota Encontrada com o QR Code Informado.",context);
        }
   }catch (Exception e){

        Util.exibirMensagem("conexão","Erro ao tentar se conectar com os Servidores.",context);

    }

    }


}