package br.com.laboratorio.hemope.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.util.Cipher3DES;

public class LoginActivity extends AppCompatActivity{

    ProgressDialog progressDialog;
    static Itens itens;

    private static final int MEU_LOADER = 0;
    static String[] credenciais = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Recuperando o estado da Activity
        if (savedInstanceState != null) {
            itens = (Itens) savedInstanceState.getSerializable("itens");

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Salvando o estado da Activity
        outState.putSerializable("itens", itens);
    }


    public void efetuarLogin(View view) {

        EditText editTextUsuario = (EditText) findViewById(R.id.etUsuario);
        EditText editTextSenha = (EditText) findViewById(R.id.etSenha);


        credenciais[0] = editTextUsuario.getText().toString();

        Cipher3DES c;
        String senha = null;
        try {
            c = new Cipher3DES(Util.KEY_PASSWORD, Util.VECTOR_INI_PASSWORD);
            senha = c.encryptText(editTextSenha.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        credenciais[1] = senha;

        String urlGeral = view.getResources().getString(R.string.urlGeralWebService);
        String urlSecundaria = view.getResources().getString(R.string.urlGeralWebServiceEfetuarLogin);

        if(!editTextSenha.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")) {

            Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde", "Verificando o seu Login...", "verificaLogin", itens, LoginActivity.this);
            String urlAutenticacao = urlGeral + urlSecundaria + "?login=" + credenciais[0] + "&senha=" + credenciais[1];
            Log.d("Login.URL", urlAutenticacao);
            downloadTask.execute(urlAutenticacao);
        }else{
            Util.exibirMensagem("Campos","Favor informar as credenciais de acesso!", this);
        }
    }

   public static void verificaLogin(Itens itens, FragmentActivity context) {

        try {
            if (itens.isSuccess){

                String urlGeral = context.getResources().getString(R.string.urlGeralWebService);
                String urlSecundaria = context.getResources().getString(R.string.urlGeralWebServiceListarTodosOsFreezers);

                Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde...","Carregando dados do Freezer","atualizarCountFreezerAba",itens,context);
                downloadTask.execute(urlGeral + urlSecundaria);


            } else {
                Toast.makeText(context, "Login ou Senha Inválida.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",context);
        }
    }

}