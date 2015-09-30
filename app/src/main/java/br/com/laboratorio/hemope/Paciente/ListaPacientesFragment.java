package br.com.laboratorio.hemope.Paciente;

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
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class ListaPacientesFragment extends Fragment {
    //"https://www.dropbox.com/s/0j1hn0785s355td/pessoaJson.json?dl=1"
    static ListView listView;
    Itens itens;
   // DownloadPacienteTask task;
    ProgressDialog progressDialog;

    static String mSavedName;
    private String searchedName;
    static ArrayList<Paciente> mListaPacientes = new ArrayList<>();
    View view;

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

                String urlGeral = view.getResources().getString(R.string.urlGeralWebService);
                String urlSecundaria = view.getResources().getString(R.string.urlGeralWebServiceConsultarPaciente);
                mListaPacientes.clear();

                Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Consultando Pacientes...","consultarPacientes",itens,getActivity());
                downloadTask.execute(urlGeral + urlSecundaria + "?nomePaciente="+pesquisaUsuario);
                Log.i("link",urlGeral + urlSecundaria + "?nomePaciente="+pesquisaUsuario);

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

        view = inflater.inflate(R.layout.fragment_main, container, false);


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

        if (itens == null) {

                Toast.makeText(getActivity(), "Para começar, clique na lupa e digite o nome do paciente.", Toast.LENGTH_LONG).show();

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


    static public void preencherLista(Itens itens, FragmentActivity context) {

       try {

               if (itens.paciente.size() > 0) {
                   for (Paciente paciente : itens.paciente) {

                       mListaPacientes.add(paciente);
                   }

               } else {
                   Toast.makeText(context, "Não encontramos Resultados", Toast.LENGTH_LONG).show();
               }
           }catch (Exception e){

                Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",context);

            }

        listView.setAdapter(new PacientesAdapter(context, mListaPacientes));

        // Se é tablet e existe algum livro na lista, selecione-o
        if (context instanceof AoClicarNoItemListener
                && context.getResources().getBoolean(R.bool.isTablet)
                && mListaPacientes.size() > 0){
            ((AoClicarNoItemListener)context).onClick(mListaPacientes.get(0));
        }
    }
}