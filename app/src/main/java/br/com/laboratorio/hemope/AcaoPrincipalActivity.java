package br.com.laboratorio.hemope;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.laboratorio.hemope.Diagnostico.ListaDiagnosticosFragment;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.Paciente.AoClicarNoPacienteListener;
import br.com.laboratorio.hemope.Paciente.DetalhePacienteActivity;
import br.com.laboratorio.hemope.Paciente.DetalhePacienteFragment;
import br.com.laboratorio.hemope.Paciente.ListaPacientesFragment;

public class AcaoPrincipalActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, AoClicarNoPacienteListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    public CharSequence mTitle;

    //Qr Code
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

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

    @Override
    public void onNavigationDrawerItemSelected(int position) {
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
            getMenuInflater().inflate(R.menu.acao_principal, menu);
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


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";



        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static Fragment newInstance(int sectionNumber) {

            switch (sectionNumber) {

                case 2:
                    ListaPacientesFragment pacientesFragment = new ListaPacientesFragment();
                    Bundle argspacientesFragment = new Bundle();
                    argspacientesFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    pacientesFragment.setArguments(argspacientesFragment);
                    return pacientesFragment;

                case 3:

                case 4:
                    ListaDiagnosticosFragment diagnosticosFragment = new ListaDiagnosticosFragment();
                    Bundle argsdiagnosticosFragment = new Bundle();
                    argsdiagnosticosFragment.putInt(ARG_SECTION_NUMBER, sectionNumber);
                    diagnosticosFragment.setArguments(argsdiagnosticosFragment);
                    return diagnosticosFragment;

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
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            if(getArguments().getInt(ARG_SECTION_NUMBER) == 3){
                try {
                    Intent intent = new Intent(ACTION_SCAN);
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException anfe) {
                    showDialog(getActivity(), "Sem Scanner Encontrado!", "Baixar um Scanner agora?", "Sim", "Não").show();
                }
            }

            ((AcaoPrincipalActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {
            if (requestCode == 0) {
                if (resultCode == RESULT_OK) {
                    String contents = intent.getStringExtra("SCAN_RESULT");
                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                    Toast toast = Toast.makeText(getActivity(), "ID Aliquota: " + contents , Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }

        private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
            AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
            downloadDialog.setTitle(title);
            downloadDialog.setMessage(message);
            downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    try {
                        act.startActivity(intent);
                    } catch (ActivityNotFoundException anfe) {

                    }
                }
            });
            downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            return downloadDialog.show();
        }
    }

}
