package br.com.laboratorio.hemope.Amostra;

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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class ListaAmostrasFragment extends Fragment {
    //
    static ListView listView;
    Itens itens;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<Amostra> mListaAmostras = new ArrayList<>();

    public ListaAmostrasFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((AcaoPrincipalActivity) activity).onSectionAttached(
                (5));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        if(savedInstanceState != null){
            mSavedName = savedInstanceState.getString(searchedName);
            mListaAmostras = (ArrayList<Amostra>)savedInstanceState.getSerializable("listaAmostras");

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_principal, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Nome do Amostra...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String pesquisaUsuario) {

                Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Consultando Amostras...","consultarAmostras",itens,getActivity());
                downloadTask.execute("https://www.dropbox.com/s/vx4e2gqclhpeckk/amostraJSON.json?dl=1");
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

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("click", "clicando");
                Amostra amostra = (Amostra) listView.getAdapter().getItem(i);

                if (getActivity() instanceof AoClicarNoItemListener) {
                    ((AoClicarNoItemListener)getActivity()).onClick(amostra);
                }
            }
        });

        //preencherLista();
        //Pesquisar no banco ---

        if (itens == null) {

                Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do amostra.", Toast.LENGTH_LONG).show();
        }

        if (mListaAmostras != null){
            listView.setAdapter(new AmostrasAdapter(getActivity(), mListaAmostras));

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(searchedName, mSavedName);

        outState.putSerializable("listaAmostras", (ArrayList<Amostra>) mListaAmostras);

        super.onSaveInstanceState(outState);

    }



    public static void preencherLista(Itens itens, FragmentActivity context) {

        try {

            if (mListaAmostras != null) {
                for (Amostra amostra : itens.amostras) {

                    mListaAmostras.add(amostra);
                }

            } else {
                Toast.makeText(context, "Não encontramos Resultados", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){

            Util.exibirMensagem("Conexão", "Erro ao tentar se conectar com os Servidores.", context);

        }

        listView.setAdapter(new AmostrasAdapter(context, mListaAmostras));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (context instanceof AoClicarNoItemListener
                && context.getResources().getBoolean(R.bool.isTablet)
                && mListaAmostras.size() > 0){
            ((AoClicarNoItemListener)context).onClick(mListaAmostras.get(0));
        }    }

}
