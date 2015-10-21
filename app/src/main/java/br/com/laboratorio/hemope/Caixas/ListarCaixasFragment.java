package br.com.laboratorio.hemope.Caixas;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.Model.Itens;


public class ListarCaixasFragment extends Fragment {
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
    public static ListarCaixasFragment newInstance(Integer aba, Itens itens) {
        ListarCaixasFragment fragment = new ListarCaixasFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, aba);
        args.putSerializable(ARG_PARAM2, itens);
        fragment.setArguments(args);
        return fragment;
    }

    public ListarCaixasFragment() {
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
    //    View view = inflater.inflate(R.layout.fragment_listar_caixa, container, false);

//        TextView textView = (TextView)view.findViewById(R.id.textAba);
  //      TextView txtDescricaoView = (TextView)view.findViewById(R.id.labelDescCaixa);
    //    TextView txtQtdGavetasView = (TextView)view.findViewById(R.id.labelCapacidade);
      //  textView.setText(mParam2.caixas.get(mParam1).codigo);
       // txtDescricaoView.setText(mParam2.caixas.get(mParam1).descricao);
       // txtQtdGavetasView.setText(mParam2.caixas.get(mParam1).qtdGavetas);
       // RelativeLayout relativeclic1 =(RelativeLayout)view.findViewById(R.id.relativeCaixa);
       // relativeclic1.setOnClickListener(new View.OnClickListener(){
         //   @Override
           // public void onClick(View v){
         //       Caixa f = mParam2.caixas.get(mParam1);
             //   Toast.makeText(getActivity(), ""+f.gavetas.get(0).numeroCaixas, Toast.LENGTH_LONG).show();
                //gavetaArrayList.get(0).numeroCaixas
           //     gavetaArrayList.clear();
               // int i = 0;
             //   for(Gaveta g: f.gavetas) {

             //       gavetaArrayList.add(g);

               //     i++;
               // }

               // Toast.makeText(getActivity(), ""+gavetaArrayList.size(), Toast.LENGTH_LONG).show();
               //Intent it = new Intent(getActivity(), ListaGavetasActivity.class);

               // it.putExtra("gavetas", gavetaArrayList);
               // startActivity(it);

           // }
        //});
        return null;
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
