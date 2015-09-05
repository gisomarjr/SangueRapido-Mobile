package br.com.laboratorio.hemope.Aliquota;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.R;


    public class AliquotaFragment extends Fragment {

        public AliquotaFragment() {

        }

        View aliquotaView;

        //Qr Code
        static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

        private static final String ARG_SECTION_NUMBER = "section_number";

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


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            aliquotaView = inflater.inflate(R.layout.fragment_aliquota, container, false);

            return aliquotaView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent intent) {

            Log.e("onActivityResult",requestCode +"" );
            if (requestCode == 0) {
                if (resultCode == Activity.RESULT_OK) {
                    String idAliquota = intent.getStringExtra("SCAN_RESULT");
                    String format = intent.getStringExtra("SCAN_RESULT_FORMAT");

                    Toast toast = Toast.makeText(getActivity(), "ID Aliquota: " + idAliquota , Toast.LENGTH_LONG);
                    toast.show();
                    consultarAliquota(idAliquota);
                }
            }
        }

        public void consultarAliquota(String idAliquota){

           // idAliquota = getActivity().getIntent().getExtra("idAliquota", 0);
            TextView txtIdAliquota = (TextView) aliquotaView.findViewById(R.id.idAliquota);
            txtIdAliquota.setText(idAliquota);

        }

    }