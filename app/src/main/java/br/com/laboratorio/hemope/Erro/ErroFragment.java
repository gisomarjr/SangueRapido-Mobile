package br.com.laboratorio.hemope.Erro;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.laboratorio.hemope.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ErroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ErroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ErroFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "erro";
    private static final String ARG_PARAM2 = "mensagem";

    // TODO: Rename and change types of parameters
    private static String mParam1;
    private static String mParam2;

    View view;
    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ErroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ErroFragment newInstance(String param1, String param2) {
        ErroFragment fragment = new ErroFragment();
        Bundle args = new Bundle();
        mParam1 = param1;
        mParam2 = param2;
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ErroFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       view = inflater.inflate(R.layout.fragment_erro, container, false);

        TextView msgErro = (TextView) view.findViewById(R.id.textDescricaoErro);
        ImageView imagemErro = (ImageView) view.findViewById(R.id.imageErro);

        Log.e("param",mParam1 + " - " + mParam2);

        if(mParam1 == "erro"){
            imagemErro.setImageDrawable(getResources().getDrawable(R.drawable.erro));
        }else{
            imagemErro.setImageDrawable(getResources().getDrawable(R.drawable.atencao));
        }

        msgErro.setText(mParam2);

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
