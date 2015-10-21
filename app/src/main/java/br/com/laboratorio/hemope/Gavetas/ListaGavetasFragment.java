package br.com.laboratorio.hemope.Gavetas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Gavetas.GavetasAdapter;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.AoClicarNoItemListener;


public class ListaGavetasFragment extends android.support.v4.app.Fragment {
    //
    static ListView listView;
    Itens itens;
    ProgressDialog progressDialog;
    public ArrayList<Gaveta> listaGavetas = new ArrayList<>();

    static String mSavedName;
    private String searchedName;
    static ArrayList<Gaveta> mListaGavetas = new ArrayList<>();
    View view;
    public static ListaGavetasFragment novaInstancia(ArrayList<Gaveta> gavetas) {

        ListaGavetasFragment dpf = new ListaGavetasFragment();
        Bundle args = new Bundle();
        args.putSerializable("gavetas", gavetas);

        mListaGavetas = gavetas;
        dpf.setArguments(args);
        return dpf;
    }
    public ListaGavetasFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        setRetainInstance(true);

        if (mListaGavetas.size() > 0 ) {
            //     mSavedName = savedInstanceState.getString(searchedName);
            //   mListaGavetas = (ArrayList<Gaveta>) savedInstanceState.getSerializable("listaGavetas");

      //      Toast.makeText(getActivity(),""+mListaGavetas.get(0).numeroCaixas, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        listaGavetas = (ArrayList<Gaveta>) getArguments().getSerializable("gavetas");

        view = inflater.inflate(R.layout.fragment_lista_gavetas, container, false);


        listView = (ListView) view.findViewById(R.id.listViewGavetas);
        //listView.setAdapter(new GavetasAdapter(getActivity(),listaGavetas));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("click", "clicando");
                Gaveta gaveta = (Gaveta) listView.getAdapter().getItem(i);

                if (getActivity() instanceof AoClicarNoItemListener) {
                    ((AoClicarNoItemListener) getActivity()).onClick(gaveta);
                }
            }
        });

        //preencherLista();
        //Pesquisar no banco ---


        if (listaGavetas != null) {
            listView.setAdapter(new GavetasAdapter(getActivity(), listaGavetas));

        }

        return view;
    }

    //@Override
    //public void onSaveInstanceState(Bundle outState) {

//        outState.putString(searchedName, mSavedName);

//        outState.putSerializable("listaGavetas", (ArrayList<Gaveta>) mListaGavetas);

  //      super.onSaveInstanceState(outState);

   // }

}