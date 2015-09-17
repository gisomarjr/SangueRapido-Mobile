package br.com.laboratorio.hemope.Amostra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class ListaAmostrasFragment extends Fragment {

    ListView listView;
    Itens itensAmostra;
    DownloadAmostraTask task;
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
                (4));
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

                    task = new DownloadAmostraTask();
                    task.execute(pesquisaUsuario);


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

        if (itensAmostra == null) {
            if (task == null) {
                Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do amostra.", Toast.LENGTH_LONG).show();
            }
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

    class DownloadAmostraTask extends AsyncTask<String, Void, Itens>{

        @Override
        protected Itens doInBackground(String... pesquisa) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.dropbox.com/s/vx4e2gqclhpeckk/amostraJSON.json?dl=1")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                Gson gson = new Gson();
                itensAmostra = gson.fromJson(json, Itens.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return itensAmostra;
        }

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(), "Aguarde...", "Carregando Amostras...", true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Itens amostra) {
            super.onPostExecute(amostra);
            progressDialog.dismiss();
            preencherLista();
        }
    }

    private void preencherLista() {

        List<Amostra> amostras = new ArrayList<>();
        try{
        if(amostras != null) {
            for (Amostra amostra : itensAmostra.amostras) {
                amostras.add(amostra);
                mListaAmostras.add(amostra);
            }

        }else {
            Toast.makeText(getActivity(), "Não encontramos Resultados", Toast.LENGTH_LONG).show();
        }}catch (Exception e) {
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
        listView.setAdapter(new AmostrasAdapter(getActivity(), amostras));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (getActivity() instanceof AoClicarNoItemListener
                && getResources().getBoolean(R.bool.isTablet)
                && amostras.size() > 0){
            ((AoClicarNoItemListener)getActivity()).onClick(amostras.get(0));
        }
    }
}
