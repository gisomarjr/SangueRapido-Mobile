package br.com.laboratorio.hemope.Diagnostico;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
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
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;


public class ListaDiagnosticosFragment extends Fragment {

    ListView listView;
    Itens itensDiagnostico;
    DownloadDiagnosticoTask task;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<Diagnostico> mListaDiagnosticos = new ArrayList<>();

    public ListaDiagnosticosFragment() {

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
            mListaDiagnosticos = (ArrayList<Diagnostico>)savedInstanceState.getSerializable("listaDiagnosticos");

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_principal, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Nome do Diagnostico...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String pesquisaUsuario) {

                    task = new DownloadDiagnosticoTask();
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

        View view = inflater.inflate(R.layout.fragment_lista_diagnostico, container, false);


        listView = (ListView)view.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Diagnostico diagnostico = (Diagnostico) listView.getAdapter().getItem(i);

                if (getActivity() instanceof AoClicarNoDiagnosticoListener) {
                    ((AoClicarNoDiagnosticoListener)getActivity()).onClick(diagnostico);
                }
            }
        });

        //preencherLista();
        //Pesquisar no banco ---

        if (itensDiagnostico == null) {
            if (task == null) {
                Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do diagnostico.", Toast.LENGTH_LONG).show();
            }
        }

        if (mListaDiagnosticos != null){
            listView.setAdapter(new DiagnosticosAdapter(getActivity(), mListaDiagnosticos));

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(searchedName, mSavedName);

        outState.putSerializable("listaDiagnosticos", (ArrayList<Diagnostico>) mListaDiagnosticos);

        super.onSaveInstanceState(outState);

    }

    class DownloadDiagnosticoTask extends AsyncTask<String, Void, Itens>{

        @Override
        protected Itens doInBackground(String... pesquisa) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.dropbox.com/s/vx4e2gqclhpeckk/diagnosticoJSON.json?dl=0")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                Gson gson = new Gson();
                itensDiagnostico = gson.fromJson(json, Itens.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return itensDiagnostico;
        }

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(), "Aguarde...", "Carregando Diagnosticos...", true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Itens diagnostico) {
            super.onPostExecute(diagnostico);
            progressDialog.dismiss();
            preencherLista();
        }
    }

    private void preencherLista() {

        List<Diagnostico> diagnosticos = new ArrayList<>();
        try{
        if(diagnosticos != null) {
            for (Diagnostico diagnostico : itensDiagnostico.diagnosticos) {
                diagnosticos.add(diagnostico);
                mListaDiagnosticos.add(diagnostico);
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
        listView.setAdapter(new DiagnosticosAdapter(getActivity(), diagnosticos));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (getActivity() instanceof AoClicarNoDiagnosticoListener
                && getResources().getBoolean(R.bool.isTablet)
                && diagnosticos.size() > 0){
            ((AoClicarNoDiagnosticoListener)getActivity()).onClick(diagnosticos.get(0));
        }
    }
}
