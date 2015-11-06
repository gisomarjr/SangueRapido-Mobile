package br.com.laboratorio.hemope;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import br.com.laboratorio.hemope.Aliquota.AcaoAliquotaFragment;
import br.com.laboratorio.hemope.Aliquota.AliquotaFragment;
import br.com.laboratorio.hemope.Amostra.DetalheAmostraActivity;
import br.com.laboratorio.hemope.Amostra.DetalheAmostraFragment;
import br.com.laboratorio.hemope.Amostra.ListaAmostrasFragment;
import br.com.laboratorio.hemope.Diagnostico.DetalheDiagnosticoActivity;
import br.com.laboratorio.hemope.Diagnostico.DetalheDiagnosticoFragment;
import br.com.laboratorio.hemope.Diagnostico.ListaDiagnosticosFragment;
import br.com.laboratorio.hemope.Model.Aliquota;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.Paciente.DetalhePacienteActivity;
import br.com.laboratorio.hemope.Paciente.DetalhePacienteFragment;
import br.com.laboratorio.hemope.Paciente.ListaPacientesFragment;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class AcaoPrincipalActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AoClicarNoItemListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence mTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acao_principal);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));


    }

    public void listarFreezer(View view){


        String urlGeral = getResources().getString(R.string.urlGeralWebService);
        String urlSecundaria = getResources().getString(R.string.urlGeralWebServiceListarTodosOsFreezers);

        Itens itens = new Itens();

        Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde...","Carregando dados do Freezer","atualizarCountFreezerAba",itens,this);
        downloadTask.execute(urlGeral + urlSecundaria);


    }

    public void listarGavetas(View view){


        String urlGeral = getResources().getString(R.string.urlGeralWebService);
        String urlSecundaria = getResources().getString(R.string.urlGeralWebServiceListarFreezer);

        Itens itens = new Itens();

        Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde...","Carregando dados do Freezer","atualizarCountFreezerAba",itens,this);
        downloadTask.execute(urlGeral + urlSecundaria);


    }


    @Override
    public void onNavigationDrawerItemSelected(int position){
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        switch (number) {
            case 1:
                mTitle = getString(R.string.titulo_sessao_freezer);
                break;
            case 2:
                mTitle = getString(R.string.titulo_sessao_paciente);
                break;
            case 3:
                mTitle = getString(R.string.titulo_sessao_aliquota);
                break;
            case 4:
                mTitle = getString(R.string.titulo_sessao_diagnostico);
                break;
            case 5:
                mTitle = getString(R.string.titulo_sessao_amostras);
                break;



            case 33:
                mTitle = getString(R.string.titulo_sessao_alocacao);
                break;

        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.acao_principal, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(Paciente paciente) {

        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalhePacienteActivity.class);
            it.putExtra("paciente", paciente);
            startActivity(it);

        } else {
            DetalhePacienteFragment detalhePacienteFragment =
                    DetalhePacienteFragment.novaInstancia(paciente);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalhePacienteFragment, "detalhe")
                    .commit();
        }

    }
    @Override
    public void onClick(Gaveta gaveta) {




    }
    @Override
    public void onClick(Diagnostico diagnostico) {

        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalheDiagnosticoActivity.class);
            it.putExtra("diagnostico", diagnostico);
            startActivity(it);

        } else {

            DetalheDiagnosticoFragment detalheDiagnosticoFragment =
                    DetalheDiagnosticoFragment.novaInstancia(diagnostico);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheDiagnosticoFragment, "detalhe")
                    .commit();
        }
    }

    @Override
    public void onClick(Amostra amostra) {
        if (getResources().getBoolean(R.bool.isPhone)) {
            Intent it = new Intent(this, DetalheAmostraActivity.class);
            it.putExtra("amostra", amostra);
            startActivity(it);

        } else {

            DetalheAmostraFragment detalheAmostraFragment =
                    DetalheAmostraFragment.novaInstancia(amostra);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, detalheAmostraFragment, "detalhe")
                    .commit();
        }
    }


    @Override
    public void onClick(Aliquota aliquota) {

    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends android.support.v4.app.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public static android.support.v4.app.Fragment newInstance(int sectionNumber,String idAliquota) {

            AliquotaFragment aliquotaFragment = new AliquotaFragment();
            Bundle argsaliquotaFragment = new Bundle();
            argsaliquotaFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
            argsaliquotaFragment.putString("idAliquota", idAliquota);
            aliquotaFragment.setArguments(argsaliquotaFragment);
            return aliquotaFragment;

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static android.support.v4.app.Fragment newInstance(int sectionNumber) {

            switch(sectionNumber){

                case 2:
                    ListaPacientesFragment pacientesFragment = new ListaPacientesFragment();
                    Bundle argspacientesFragment = new Bundle();
                    argspacientesFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    pacientesFragment.setArguments(argspacientesFragment);
                    return pacientesFragment;

                case 3:
                    AcaoAliquotaFragment aliquotaFragment = new AcaoAliquotaFragment();
                    Bundle argsaliquotaFragment = new Bundle();
                    argsaliquotaFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    aliquotaFragment.setArguments(argsaliquotaFragment);
                    return aliquotaFragment;

                case 4:
                    ListaDiagnosticosFragment diagnosticosFragment = new ListaDiagnosticosFragment();
                    Bundle argsdiagnosticoFragment = new Bundle();
                    argsdiagnosticoFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    diagnosticosFragment.setArguments(argsdiagnosticoFragment);
                    return diagnosticosFragment;

                case 5:
                    ListaAmostrasFragment amostrasFragment = new ListaAmostrasFragment();
                    Bundle argsamostraFragment = new Bundle();
                    argsamostraFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    amostrasFragment.setArguments(argsamostraFragment);
                    return amostrasFragment;

                default:
                    PlaceholderFragment fragment = new PlaceholderFragment();
                    Bundle argsfragment = new Bundle();
                    argsfragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    fragment.setArguments(argsfragment);
                    return fragment;

            }


        }

        public PlaceholderFragment() {

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_acao_principal, container, false);
            return rootView;
        }

        @Override
        public void onAttach(Activity activity){
            super.onAttach(activity);
            ((AcaoPrincipalActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }



    }

}