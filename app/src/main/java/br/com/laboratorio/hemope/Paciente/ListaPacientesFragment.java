package br.com.laboratorio.hemope.Paciente;

import android.app.ProgressDialog;
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

import br.com.laboratorio.hemope.Model.ItensPaciente;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;


public class ListaPacientesFragment extends Fragment {

    ListView listView;
    ItensPaciente itensPaciente;
    DownloadPacienteTask task;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<Paciente> mListaPacientes = new ArrayList<>();

    public ListaPacientesFragment() {

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

                if (getActivity() instanceof AoClicarNoPacienteListener) {
                    ((AoClicarNoPacienteListener)getActivity()).onClick(paciente);
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

    class DownloadPacienteTask extends AsyncTask<String, Void, ItensPaciente>{

        @Override
        protected ItensPaciente doInBackground(String... pesquisa) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://www.dropbox.com/s/tz74rpdf9yvppt4/paciente.json?dl=1")
                    .build();

            try {
                Response response = client.newCall(request).execute();
                String json = response.body().string();

                Gson gson = new Gson();
                itensPaciente = gson.fromJson(json, ItensPaciente.class);

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
        protected void onPostExecute(ItensPaciente paciente) {
            super.onPostExecute(paciente);
            progressDialog.dismiss();
            preencherLista();
        }
    }

    private void preencherLista() {

        List<Paciente> pacientes = new ArrayList<>();

        if(pacientes != null) {
            for (Paciente paciente : itensPaciente.paciente) {
                pacientes.add(paciente);
                mListaPacientes.add(paciente);
            }

        }else{
            Toast.makeText(getActivity(),"Não encontramos Resultados",Toast.LENGTH_LONG).show();
        }

        listView.setAdapter(new PacientesAdapter(getActivity(), pacientes));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (getActivity() instanceof AoClicarNoPacienteListener
                && getResources().getBoolean(R.bool.isTablet)
                && pacientes.size() > 0){
            ((AoClicarNoPacienteListener)getActivity()).onClick(pacientes.get(0));
        }
    }
}
