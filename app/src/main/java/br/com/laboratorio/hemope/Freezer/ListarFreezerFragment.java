package br.com.laboratorio.hemope.Freezer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;


public class ListarFreezerFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "aba";
    private static final String ARG_PARAM2 = "itens";

    // TODO: Rename and change types of parameters
    private Integer mParam1;
    private Itens mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param aba Parameter 1.
     * @param  itens Parameter 2.
     * @return A new instance of fragment ListarFreezerFragment.
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
        textView.setText(mParam1+" - " + mParam2.freezers.get(mParam1).codigo);
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
