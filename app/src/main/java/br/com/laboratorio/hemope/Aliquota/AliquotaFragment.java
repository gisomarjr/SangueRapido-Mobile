package br.com.laboratorio.hemope.Aliquota;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Aliquota;
import br.com.laboratorio.hemope.Model.Alocacao;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class AliquotaFragment extends Fragment {



        View aliquotaView;
        ViewPager viewPager;
        SlidingTabLayout mSlidingTabLayout;
        DownloadAliquotaTask task;

        Itens itens;
        ProgressDialog progressDialog;

        public AliquotaFragment() {

        }

        //Qr Code
        static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(getActivity(), "Sem Scanner Encontrado!", "Baixar um Scanner agora?", "Sim", "Não").show();
                }
            }

            ((AcaoPrincipalActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }



        private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
            AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
            downloadDialog.setTitle(title);
            downloadDialog.setMessage(message);
            downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        act.startActivity(intent);
                    } catch (ActivityNotFoundException anfe) {

                    }
                }
            });
            downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return downloadDialog.show();
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            aliquotaView = inflater.inflate(R.layout.fragment_aliquota, container, false);
            return aliquotaView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            if (requestCode == 0) {
                if (resultCode == Activity.RESULT_OK) {
                    String idAliquota = intent.getStringExtra("SCAN_RESULT");
                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                    Toast toast = Toast.makeText(getActivity(), "ID Aliquota: " + idAliquota , Toast.LENGTH_LONG);
                    toast.show();
                    consultarAliquota(idAliquota);
                }
            }
        }

        public void consultarAliquota(String idAliquota){


            task = new DownloadAliquotaTask();
            task.execute(idAliquota);
        }


        class DownloadAliquotaTask extends AsyncTask<String, Void, Itens> {

            @Override
            protected Itens doInBackground(String... pesquisa) {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url("https://www.dropbox.com/s/10enzhvm6lnb0j6/aliquota.json?dl=1")
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    String json = response.body().string();

                    Gson gson = new Gson();
                    itens = gson.fromJson(json, Itens.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return itens;
            }

            @Override
            protected void onPreExecute(){
                progressDialog = ProgressDialog.show(getActivity(), "Aguarde...", "Carregando dados da Aliquota...", true);
                progressDialog.setCancelable(false);
            }

            @Override
            protected void onPostExecute(Itens aliquota) {
                super.onPostExecute(aliquota);
                progressDialog.dismiss();
                preencherActivity();
            }
    }


    public void preencherActivity(){

        Paciente paciente = new Paciente();
        Aliquota aliquota = new Aliquota();
        Amostra amostra = new Amostra();
        Alocacao alocacao = new Alocacao();
        Caixa caixa = new Caixa();
        Gaveta gaveta = new Gaveta();
        Freezer freezer = new Freezer();


        aliquota = itens.aliquota;
        amostra  = itens.aliquota.amostra;
        alocacao = itens.aliquota.alocacao;
        paciente = itens.aliquota.amostra.paciente;
        caixa    = itens.aliquota.alocacao.caixa;
        gaveta   = itens.aliquota.alocacao.caixa.gaveta;
        freezer  = itens.aliquota.alocacao.caixa.gaveta.freezer;

        TextView txtNomePaciente = (TextView) aliquotaView.findViewById(R.id.labelNomePaciente);
        TextView txtNomeMae = (TextView) aliquotaView.findViewById(R.id.labelNomeMae);
        TextView txtCPF= (TextView) aliquotaView.findViewById(R.id.labelCPF);
        TextView txtTelefone= (TextView) aliquotaView.findViewById(R.id.labelTelefone);

        TextView txtDataEntrada = (TextView) aliquotaView.findViewById(R.id.labelDataEntrada);
        TextView txtDataDescarte = (TextView) aliquotaView.findViewById(R.id.labelDataDescarte);
        TextView txtCaixa = (TextView) aliquotaView.findViewById(R.id.labelIdCaixa);
        TextView txtGaveta = (TextView) aliquotaView.findViewById(R.id.labelIdGaveta);
        TextView txtFreezer = (TextView) aliquotaView.findViewById(R.id.labelCodigoFreezer);
        TextView txtPosicao = (TextView) aliquotaView.findViewById(R.id.labelPosicao);

        TextView txtVolume = (TextView) aliquotaView.findViewById(R.id.labelVolume);
        TextView txtConcentracao = (TextView) aliquotaView.findViewById(R.id.labelConcentracao);

        txtNomePaciente.setText("Nome do Paciente: "+paciente.nome);
        txtNomeMae.setText("Nome da Mãe: "+paciente.nomeMae);
        txtCPF.setText("CPF: "+paciente.cpf);
        txtTelefone.setText("Telefone: "+paciente.telefone);

        txtDataEntrada.setText("Data de Entrada: "+aliquota.dataEntrada);
        txtDataDescarte.setText("Data de Descarte: "+aliquota.dataDescarte);
        txtPosicao.setText("Coluna: " + alocacao.posicaoX + " Linha: " + alocacao.posicaoY);
        txtCaixa.setText("Caixa: "+caixa.idCaixa);
        txtGaveta.setText("Gaveta: "+gaveta.idGaveta);
        txtFreezer.setText("Código do Freezer: "+freezer.codigo);

        txtVolume.setText("Volume: "+String.valueOf(aliquota.volume));
        txtConcentracao.setText("Concentração: "+String.valueOf(aliquota.concentracao));

        //aliquotaView.findViewById(R.)

    }


}