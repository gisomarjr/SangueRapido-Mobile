package br.com.laboratorio.hemope.Alocacao;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class AlocacaoFragment extends Fragment {

        View alocacaoView;
        ViewPager viewPager;
        SlidingTabLayout mSlidingTabLayout;
        DownloadAliquotaTask task;

        Itens itens;
        ProgressDialog progressDialog;

        public AlocacaoFragment() {

        }

        //Qr Code
        static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 5){
               /* try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(getActivity(), "Sem Scanner Encontrado!", "Baixar um Scanner agora?", "Sim", "Não").show();
                }*/
            }


            Toast.makeText(getActivity(),getArguments().getString("idAliquota"),Toast.LENGTH_LONG).show();
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

            alocacaoView = inflater.inflate(R.layout.fragment_alocacao, container, false);
            return alocacaoView;
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
                             if(Integer.parseInt(idAliquota) > 0) {
                                    consultarAliquota(idAliquota);
                             }else{
                                 Toast.makeText(getActivity(), "ID da Aliquota Inválido", Toast.LENGTH_LONG).show();
                             }

                         } catch (NumberFormatException e) {
                             Toast.makeText(getActivity(), "QRCODE inválido.", Toast.LENGTH_LONG).show();
                         }

                       }else{
                        Toast.makeText(getActivity(), "Formato não reconhecido para consultar uma Aliquota :" + formato , Toast.LENGTH_LONG).show();
                    }
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
                progressDialog = ProgressDialog.show(getActivity(), "Aguarde...", "Carregando dados da Alocação...", true);
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

    try {


        }catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Conexão")
                    .setMessage("Erro ao tentar se conectar com os Servidores.")
                    .setCancelable(false)
                    .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }

    }


}