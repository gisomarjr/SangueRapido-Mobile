package br.com.laboratorio.hemope.Paciente;

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
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class ListaPacientesFragment extends Fragment {

    ListView listView;
    Itens itensPaciente;
    DownloadPacienteTask task;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<Paciente> mListaPacientes = new ArrayList<>();

    public ListaPacientesFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((AcaoPrincipalActivity) activity).onSectionAttached(
                (2));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);
        if(savedInstanceState != null){
            mSavedName = savedInstanceState.getString(searchedName);
            mListaPacientes = (ArrayList<Paciente>)savedInstanceState.getSerializable("listaPacientes");

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_principal, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint("Nome do Paciente...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String pesquisaUsuario) {

                task = new DownloadPacienteTask();
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

                Paciente paciente = (Paciente) listView.getAdapter().getItem(i);

                if (getActivity() instanceof AoClicarNoItemListener) {
                    ((AoClicarNoItemListener)getActivity()).onClick(paciente);
                }
            }
        });

        //preencherLista();
        //Pesquisar no banco ---

        if (itensPaciente == null) {
            if (task == null) {
                Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do paciente.", Toast.LENGTH_LONG).show();
            }
        }

        if (mListaPacientes != null){
            listView.setAdapter(new PacientesAdapter(getActivity(), mListaPacientes));

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(searchedName, mSavedName);

        outState.putSerializable("listaPacientes", (ArrayList<Paciente>) mListaPacientes);

        super.onSaveInstanceState(outState);

    }

    class DownloadPacienteTask extends AsyncTask<String, Void, Itens>{

        @Override
        protected Itens doInBackground(String... pesquisa) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.dropbox.com/s/0j1hn0785s355td/pessoaJson.json?dl=1")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                Gson gson = new Gson();
                itensPaciente = gson.fromJson(json, Itens.class);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return itensPaciente;
        }

        @Override
        protected void onPreExecute(){
            progressDialog = ProgressDialog.show(getActivity(), "Aguarde...", "Carregando Pacientes...", true);
            progressDialog.setCancelable(false);
        }

        @Override
        protected void onPostExecute(Itens paciente) {
            super.onPostExecute(paciente);
            progressDialog.dismiss();
            preencherLista();
        }
    }

    private void preencherLista() {




       try {
               if (mListaPacientes != null) {
                   for (Paciente paciente : itensPaciente.paciente) {

                       mListaPacientes.add(paciente);
                   }

               } else {
                   Toast.makeText(getActivity(), "Não encontramos Resultados", Toast.LENGTH_LONG).show();
               }
           }catch (Exception e){

                Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",getActivity());

            }

        listView.setAdapter(new PacientesAdapter(getActivity(), mListaPacientes));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (getActivity() instanceof AoClicarNoItemListener
                && getResources().getBoolean(R.bool.isTablet)
                && mListaPacientes.size() > 0){
            ((AoClicarNoItemListener)getActivity()).onClick(mListaPacientes.get(0));
        }
    }
}