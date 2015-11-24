package br.com.laboratorio.hemope.Caixa;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.R;


public class ListarCaixaFragment extends Fragment {

    public static ArrayList<Caixa> _caixas = new ArrayList<>();
    static ArrayList<Caixa> mListaCaixas = new ArrayList<>();

    View view;
    static ListView listView;

    public static ListarCaixaFragment novaInstancia(ArrayList<Caixa> caixas) {

        ListarCaixaFragment listarCaixaFragment = new ListarCaixaFragment();
        Bundle args = new Bundle();
        args.putSerializable("caixas", caixas);

        mListaCaixas = caixas;
        listarCaixaFragment.setArguments(args);
        return listarCaixaFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        _caixas = (ArrayList<Caixa>) getArguments().getSerializable("caixas");

        view = inflater.inflate(R.layout.fragment_listar_caixa, container, false);


        listView = (ListView) view.findViewById(R.id.listViewCaixas);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("click", "clicando");

                Caixa caixa = (Caixa) listView.getAdapter().getItem(i);



                Intent it = new Intent(getActivity(),CaixaItemActivity.class);
                it.putExtra("x",caixa.qtdX);
                it.putExtra("y",caixa.qtdY);
                it.putExtra("alocacao", caixa.alocacoes);
                if (caixa.alocacoes.size()>0){
                   // Toast.makeText(getActivity(), "" + caixa.alocacoes.get(0).idAlocacao, Toast.LENGTH_LONG).show();
                }

                startActivity(it);
                //ArrayList<Caixa> caixas = gaveta.caixas;

               /* Intent it = new Intent(getActivity(), ListarCaixaActivity.class);
                it.putExtra("caixas", caixas);
                startActivity(it);

                if (getActivity() instanceof AoClicarNoItemListener) {
                    ((AoClicarNoItemListener) getActivity()).onClick(gaveta);
                }*/
            }
        });

        if(_caixas !=null)
        {
            listView.setAdapter(new CaixasAdapter(getActivity(), _caixas));

        }

        return view;
    }


}