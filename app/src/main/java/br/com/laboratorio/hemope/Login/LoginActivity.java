package br.com.laboratorio.hemope.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;

public class LoginActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Itens> {

       ProgressDialog progressDialog;
       static Itens itens;
       DownloadPacienteTask task;
       private static final int MEU_LOADER = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Recuperando o estado da Activity
        if (savedInstanceState != null) {
            itens = (Itens) savedInstanceState.getSerializable("itens");
            atualizaTela();
        }
    }

    public void atualizaTela(){
        // Essa classe gerencia todos os Loaders
        LoaderManager lm = getSupportLoaderManager();

        // Se o JSON ainda não foi baixado
        if (itens == null) {
            // Exibe o dialog
            progressDialog = ProgressDialog.show(
                    this, "Aguarde...", "Verificando Login...");

            // E inicializa o Loader (se ele já foi
            // inicializado, ele apenas continuará)
            lm.initLoader(MEU_LOADER,null,this);
        }else{
            Intent it = new Intent(getApplication(), AcaoPrincipalActivity.class);
            startActivity(it);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Se estiver exibindo o progress, remova-o
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        // Salvando o estado da Activity
        outState.putSerializable("itens", itens);
    }
   // AsyncTaskLoader
    public static class LoginAsyncTask
            extends android.support.v4.content.AsyncTaskLoader<Itens> {

        // O JSON baixado será armazenado aqui para ser
        // repassado para Activity
        Itens data;

        public LoginAsyncTask(Context context) {
            super(context);
        }

        @Override
        public Itens loadInBackground() {
            return LoginActivity.efetuarLoginBackground(null);
        }

        @Override
        protected void onStartLoading() {
            // Se não baixou os dados, faça agora!
            if (data == null) {
                forceLoad();

                // Se já baixou, apenas entregue o resultado
            } else {
                deliverResult(data);
            }
        }
    }

    public static Itens efetuarLoginBackground(String[] pesquisa){
        OkHttpClient client = new OkHttpClient();

        //Log.i("credenciais",pesquisa[0] + pesquisa[1]);

        Request request = new Request.Builder()
                .url("https://www.dropbox.com/s/z8a4jj9cr9719t1/usualios_login.json?dl=1")
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

    public void efetuarLogin(View view){

        EditText editTextUsuario  = (EditText) findViewById(R.id.etUsuario);
        EditText editTextSenha  = (EditText) findViewById(R.id.etSenha);

        String[] credenciais = new String[2];
        credenciais[0] = editTextUsuario.getText().toString();
        credenciais[1] = editTextSenha.getText().toString();

        atualizaTela();

       /* task = new DownloadPacienteTask();

        if(task.getStatus() != AsyncTask.Status.RUNNING || task.getStatus() != AsyncTask.Status.PENDING) {
            task.execute(credenciais);
        }*/


    }

    @Override
    public android.support.v4.content.Loader<Itens> onCreateLoader(int id, Bundle args) {
        return new LoginAsyncTask(this);
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<Itens> loader, Itens data) {
        this.itens = itens;
        try {
            if (itens.usuario.login != null) {
                Intent it = new Intent(getApplication(), AcaoPrincipalActivity.class);
                startActivity(it);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Login ou Senha Inválida.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
        progressDialog.dismiss();

    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<Itens> loader) {

    }

    class DownloadPacienteTask extends AsyncTask<String, Void, Itens>{

        @Override
        protected Itens doInBackground(String... pesquisa) {
            OkHttpClient client = new OkHttpClient();

            Log.i("credenciais",pesquisa[0] + pesquisa[1]);

            Request request = new Request.Builder()
                    .url("https://www.dropbox.com/s/z8a4jj9cr9719t1/usualios_login.json?dl=1")
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
            progressDialog = ProgressDialog.show(LoginActivity.this, "Aguarde...", "Verificando Login...", true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Itens itens) {
            super.onPostExecute(itens);

         try {
             if (itens.usuario.login != null) {
                 Intent it = new Intent(getApplication(), AcaoPrincipalActivity.class);
                 startActivity(it);
                 finish();
             } else {
                 Toast.makeText(getApplicationContext(), "Login ou Senha Inválida.", Toast.LENGTH_LONG).show();
             }
         }catch (Exception e){
             AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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
            progressDialog.dismiss();

        }
    }
}
