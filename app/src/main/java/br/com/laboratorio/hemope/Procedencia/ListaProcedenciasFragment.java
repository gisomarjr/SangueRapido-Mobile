package br.com.laboratorio.hemope.Procedencia;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.LocalProcedencia;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;

/**
 * Created by User on 29/09/2015.
 */
public class ListaProcedenciasFragment extends Fragment{
    //static ListView listView;
    static Itens _itens;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<LocalProcedencia> mListaProcedencias = new ArrayList<>();
    static View view;

    public ListaProcedenciasFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((AcaoPrincipalActivity) activity).onSectionAttached(
                (6));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        if(savedInstanceState != null){
            mSavedName = savedInstanceState.getString(searchedName);
            mListaProcedencias = (ArrayList<LocalProcedencia>)savedInstanceState.getSerializable("listaProcedencias");

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_principal, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Nome da Procedencia...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String pesquisaUsuario) {

                String urlGeral = view.getResources().getString(R.string.urlGeralWebService);
                String urlSecundaria = view.getResources().getString(R.string.urlGeralWebServiceConsultarProcedencia);

                Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Consultando Procedencias...","consultarProcedencias",_itens,getActivity());
                downloadTask.execute(urlGeral + urlSecundaria + "?codigoAmostra="+pesquisaUsuario);
                Log.i("urlProcedencia", urlGeral + urlSecundaria + "?codigoAmostra=" + pesquisaUsuario);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                // Do something when collapsed

                return true;  // Return true to collapse action view
            }

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                // Do something when expanded

                return true;  // Return true to expand action view
            }
        });

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.item_procedencia, container, false);



        if (_itens == null) {

            Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do procedencia.", Toast.LENGTH_LONG).show();
        }

        if (mListaProcedencias != null){


        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(searchedName, mSavedName);

        outState.putSerializable("listaProcedencias", (ArrayList<LocalProcedencia>) mListaProcedencias);

        super.onSaveInstanceState(outState);

    }



    public static void preencherLista(Itens itens, FragmentActivity context) {
        LocalProcedencia localProcedencia = new LocalProcedencia();
        try {
            _itens = itens;
            if (itens!=null) {
                localProcedencia = itens.localProcedencia;
                TextView txtLocalProcedencia = (TextView) view.findViewById(R.id.localProcedencia);
                txtLocalProcedencia.setText(localProcedencia.nome);

            } else {
                Toast.makeText(context, "Não encontramos Resultados", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

            Util.exibirMensagem("Conexão", "Erro ao tentar se conectar com os Servidores.", context);

        }

        //listView.setAdapter(new ProcedenciasAdapter(context, mListaProcedencias));
  }

}
