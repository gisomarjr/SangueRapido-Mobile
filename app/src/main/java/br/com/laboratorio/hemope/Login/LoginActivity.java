package br.com.laboratorio.hemope.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;

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
        credenciais[1] = editTextSenha.getText().toString();

        String urlGeral = view.getResources().getString(R.string.urlGeralWebService);
        String urlSecundaria = view.getResources().getString(R.string.urlGeralWebServiceEfetuarLogin);

        if(!editTextSenha.getText().toString().equals("") && !editTextSenha.getText().toString().equals("")) {

            Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde", "Verificando o seu Login...", "verificaLogin", itens, LoginActivity.this);
            downloadTask.execute(urlGeral + urlSecundaria + "?login=" + credenciais[0] + "&senha=" + credenciais[1]);
        }else{
            Util.exibirMensagem("Campos","Favor informar as credenciais de acesso!", this);
        }
    }

   public static void verificaLogin(Itens itens, FragmentActivity context) {

        try {
            if (itens.isSuccess){
                Intent it = new Intent(context, AcaoPrincipalActivity.class);
                context.startActivity(it);
                context.finish();
            } else {
                Toast.makeText(context, "Login ou Senha Inválida.", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
            Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",context);
        }
    }

}