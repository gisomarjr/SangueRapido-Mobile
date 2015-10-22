package br.com.laboratorio.hemope.Freezer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Gavetas.ListaGavetasActivity;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;


public class ListarFreezerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "aba";
    private static final String ARG_PARAM2 = "itens";

    // TODO: Rename and change types of parameters
    private Integer mParam1;
    private Itens mParam2;
    public final ArrayList<Gaveta> gavetaArrayList = new ArrayList<>();
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param aba Parameter 1.
     * @param  itens Parameter 2.
     * @return A new instance of fragment ListarCaixasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListarFreezerFragment newInstance(Integer aba, Itens itens) {
        ListarFreezerFragment fragment = new ListarFreezerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, aba);
        args.putSerializable(ARG_PARAM2, itens);
        fragment.setArguments(args);
        return fragment;
    }

    public ListarFreezerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = (Itens) getArguments().getSerializable(ARG_PARAM2);

        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_listar_freezer, container, false);

        TextView textView = (TextView)view.findViewById(R.id.textAba);
        TextView txtDescricaoView = (TextView)view.findViewById(R.id.labelDescFreezer);
        TextView txtQtdGavetasView = (TextView)view.findViewById(R.id.labelCapacidade);
        textView.setText("Código: "+mParam2.freezers.get(mParam1).codigo);
        txtDescricaoView.setText("Nome: "+mParam2.freezers.get(mParam1).descricao);
        txtQtdGavetasView.setText("Qtd. Gavetas: "+mParam2.freezers.get(mParam1).qtdGavetas);
        RelativeLayout relativeclic1 =(RelativeLayout)view.findViewById(R.id.relativeFreezer);
        relativeclic1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Freezer f = mParam2.freezers.get(mParam1);
             //   Toast.makeText(getActivity(), ""+f.gavetas.get(0).numeroCaixas, Toast.LENGTH_LONG).show();
                //gavetaArrayList.get(0).numeroCaixas
                gavetaArrayList.clear();

                for(Gaveta g: f.gavetas) {

                    gavetaArrayList.add(g);

                }

               if(gavetaArrayList.size() > 0) {
                   Intent it = new Intent(getActivity(), ListaGavetasActivity.class);
                   it.putExtra("gavetas", gavetaArrayList);
                   startActivity(it);
               }else{
                   Util.exibirMensagem("Gaveta","No Freezer selecionado, não existem gavetas cadastradas.",getActivity());
               }

            }
        });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
