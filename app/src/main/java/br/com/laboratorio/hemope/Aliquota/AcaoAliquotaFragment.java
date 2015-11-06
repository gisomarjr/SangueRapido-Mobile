package br.com.laboratorio.hemope.Aliquota;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Alocacao.AlocacaoFragment;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AcaoAliquotaFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AcaoAliquotaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AcaoAliquotaFragment extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    static Itens _itens;
    View aliquotaView;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AcaoAliquotaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AcaoAliquotaFragment newInstance(String param1, String param2) {
        AcaoAliquotaFragment fragment = new AcaoAliquotaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    //Qr Code
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    private static final String ARG_SECTION_NUMBER = "section_number";

    public void lerQrCod(){
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            Util.showDialog(getActivity(), "Sem Scanner Encontrado!", "Baixar um Scanner agora?", "Sim", "Não").show();
        }
    }

    public void lerQrCodeView(View view){
        lerQrCod();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getArguments().getInt(ARG_SECTION_NUMBER) == 3) {


            ((AcaoPrincipalActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                String idAliquota = intent.getStringExtra("SCAN_RESULT");
                String formato = intent.getStringExtra("SCAN_RESULT_FORMAT").trim();

                Log.e("qtdFormato", formato.length() + "");
                Log.e("formato",formato);

                //Verifico se é um QRCODE
                if(formato.equals("QR_CODE")) {

                    try{
                        //Verifico se é um número
                        //if(Integer.parseInt(idAliquota) > 0) {

                        String urlGeral = aliquotaView.getResources().getString(R.string.urlGeralWebService);
                        String urlSecundaria = aliquotaView.getResources().getString(R.string.urlGeralWebServiceConsultarAliquota);

                        Util.DownloadTask downloadTask = new Util.DownloadTask("Aguarde","Carregando dados da Aliquota...","aliquota",_itens,getActivity());
                        downloadTask.execute(urlGeral + urlSecundaria + "?codigoAliquota=" + idAliquota);
                        Log.i("link",urlGeral + urlSecundaria + "?codigoAliquota=" + idAliquota);

                        FragmentTransaction transaction = getFragmentManager()
                                .beginTransaction();
                        Bundle argsaLocacaoFragment = new Bundle();
                        argsaLocacaoFragment.putInt(ARG_SECTION_NUMBER, 3);
                        argsaLocacaoFragment.putSerializable("itens", _itens);
                        android.support.v4.app.Fragment novaAlocacaoFragment = new AliquotaFragment();

                        novaAlocacaoFragment.setArguments(argsaLocacaoFragment);
                        transaction.addToBackStack(null);
                        transaction.replace(R.id.container, novaAlocacaoFragment);
                        transaction.commit();

                    } catch (NumberFormatException e) {
                        Toast.makeText(getActivity(), "QRCODE inválido.", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(getActivity(), "Formato não reconhecido para consultar uma Aliquota :" + formato , Toast.LENGTH_LONG).show();
                }
            }
        }
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

        aliquotaView = inflater.inflate(R.layout.fragment_acao_aliquota, container, false);

        ImageButton imageButton = (ImageButton)aliquotaView.findViewById(R.id.imageButtonQrCode);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lerQrCod();
            }
        });



        return aliquotaView;
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
