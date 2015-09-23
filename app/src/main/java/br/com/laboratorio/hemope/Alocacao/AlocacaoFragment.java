package br.com.laboratorio.hemope.Alocacao;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;

import br.com.laboratorio.hemope.AcaoPrincipalActivity;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.Model.Itens;
import br.com.laboratorio.hemope.R;
import br.com.laboratorio.hemope.Util;
import br.com.laboratorio.hemope.View.SlidingTabLayout;


public class AlocacaoFragment extends Fragment {

        View alocacaoView;
        ViewPager viewPager;
        SlidingTabLayout mSlidingTabLayout;

        Itens itens;
        ProgressDialog progressDialog;

        public AlocacaoFragment() {

        }

        private static final String ARG_SECTION_NUMBER = "section_number";

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            itens = (Itens) getArguments().getSerializable("itens");
            Toast.makeText(getActivity(),itens.aliquota.idAliquota,Toast.LENGTH_LONG).show();

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

            alocacaoView = inflater.inflate(R.layout.fragment_alocacao, container, false);
            TextView textFreezer = (TextView) alocacaoView.findViewById(R.id.dadosFreezer);
            TextView textGaveta = (TextView) alocacaoView.findViewById(R.id.dadosGaveta);
            TextView textCaixa = (TextView) alocacaoView.findViewById(R.id.dadosCaixa);
            TextView textPosicao = (TextView) alocacaoView.findViewById(R.id.dadosPosicao);

            textFreezer.setText("Freezer: " +itens.aliquota.alocacao.caixa.gaveta.freezer.codigo);
            textGaveta.setText(String.valueOf("Gaveta: "+itens.aliquota.alocacao.caixa.gaveta.idGaveta));
            textCaixa.setText(String.valueOf("Caixa: "+itens.aliquota.alocacao.caixa.idCaixa));
            textPosicao.setText("Posição: " +itens.aliquota.alocacao.posicaoY + " - " + itens.aliquota.alocacao.posicaoX);

            final ArrayList<String> freezerArrayList = new ArrayList<>();

            freezerArrayList.add(itens.aliquota.alocacao.caixa.gaveta.freezer.codigo);
            freezerArrayList.add(itens.aliquota.alocacao.caixa.gaveta.freezer.codigo);
            freezerArrayList.add(itens.aliquota.alocacao.caixa.gaveta.freezer.codigo);

            Util.DownloadTask downloadTask = new Util.DownloadTask("Carregando","Aguarde...","freezer",itens,getActivity());
            downloadTask.execute("https://www.dropbox.com/s/z8a4jj9cr9719t1/usualios_login.json?dl=1");

            //Adapter Freezer
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, freezerArrayList);
            final Spinner spinner = (Spinner) alocacaoView.findViewById(R.id.spinnerFreezer);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                    Toast.makeText(getActivity(), ""+freezerArrayList.get(pos), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    Toast.makeText(getActivity(), "Selections cleared.", Toast.LENGTH_SHORT).show();
                }
            });

            return alocacaoView;
        }




    public static void preencherSpinnerFreezer(Itens itensCarregados, FragmentActivity context){

    try {

        Util.exibirMensagem("Sucesso",itensCarregados.usuario.login,context);

        }catch (Exception e){
        Util.exibirMensagem("Conexão","Erro ao tentar se conectar com os Servidores.",context);

        }

    }




}