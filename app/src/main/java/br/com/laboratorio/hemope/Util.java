package br.com.laboratorio.hemope;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.laboratorio.hemope.Aliquota.AliquotaFragment;
import br.com.laboratorio.hemope.Alocacao.AlocacaoFragment;
import br.com.laboratorio.hemope.Amostra.ListaAmostrasFragment;
import br.com.laboratorio.hemope.Diagnostico.ListaDiagnosticosFragment;
import br.com.laboratorio.hemope.Login.LoginActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.LocalProcedencia;
import br.com.laboratorio.hemope.Paciente.ListaPacientesFragment;
import br.com.laboratorio.hemope.Procedencia.ListaProcedenciasFragment;

/**
 * Created by gisomar on 22/09/15.
 */
public class Util {

    /**
     * Criando uma classe Generica para requisição
     *
     * @author Gisomar Junior
     * @since 22/09/2015
     */
    public static class DownloadTask extends AsyncTask<String, Void, Itens> {

        ProgressDialog _progressDialog;
        String _tituloMSG;
        String _textoMensagem;
        String _acaoMetodo;
        Itens _itens;
        FragmentActivity _context;
        LocalProcedencia _localProcedencia;

        /**
         * Construtor da Classe parametros obrigatorios
         * @param tituloMSG
         * @param textoMensagem
         * @param acaoMetodo
         * @param itens
         * @param context
         */
        public DownloadTask(String tituloMSG, String textoMensagem,String acaoMetodo,
                            Itens itens, FragmentActivity context){
            this._tituloMSG = tituloMSG;
            this._textoMensagem = textoMensagem;
            this._acaoMetodo = acaoMetodo;
            this._context = context;
            this._itens = itens;
        }

        @Override
        protected Itens doInBackground(String... arrayParametros) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(arrayParametros[0])
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                Gson gson = new Gson();
                _itens = gson.fromJson(json, Itens.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return _itens;
        }

        @Override
        protected void onPreExecute(){
            _progressDialog = ProgressDialog.show(_context, _tituloMSG, _textoMensagem, true);
            _progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Itens itensRetorno) {
            super.onPostExecute(itensRetorno);
            _progressDialog.dismiss();
            switch (_acaoMetodo){
                case "freezer":
                    //preencherSpinnerFreezer();
                    AlocacaoFragment.preencherSpinnerFreezer(itensRetorno,_context);
                    break;
                case "gaveta":
                    break;
                case "caixa":
                    break;
                case  "aliquota":
                    AliquotaFragment.preencherActivityAliquota(_itens,_context);
                    break;
                case "verificaLogin":
                    LoginActivity.verificaLogin(_itens,_context);
                    break;
                case "consultarPacientes":
                    ListaPacientesFragment.preencherLista(_itens, _context);
                    break;
                case "consultarDiagnosticos":
                    ListaDiagnosticosFragment.preencherLista(_itens,_context);
                    break;
                case "consultarAmostras":
                    ListaAmostrasFragment.preencherLista(_itens,_context);
                    break;
                case "consultarProcedencias":
                    ListaProcedenciasFragment.preencherLista(_itens, _context);
                    break;
            }

        }
    }

    /**
     * Mensagem Generica
     * @param titulo
     * @param mensagem
     * @param context
     *
     * @author Gisomar Junior
     * @since 22/09/2015
     */
    public static void exibirMensagem(String titulo, String mensagem, FragmentActivity context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titulo)
                .setMessage(mensagem)
                .setCancelable(false)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
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

}
