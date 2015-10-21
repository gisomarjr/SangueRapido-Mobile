package br.com.laboratorio.hemope.Gavetas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.R;


public class GavetasAdapter extends ArrayAdapter<Gaveta>{

    public GavetasAdapter(Context context, List<Gaveta> gavetas) {
        super(context, 0, gavetas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_gaveta, null);
        }

        TextView txtCodigo = (TextView)convertView.findViewById(R.id.txtCodigoGaveta);
        TextView txtCodigoFreezer = (TextView)convertView.findViewById(R.id.txtCodigoFreezer);
        TextView txtQtdCaixas = (TextView)convertView.findViewById(R.id.txtQtdCaixas);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageViewGaveta);

        Gaveta gaveta = getItem(position);
      //  Freezer freezer = new Freezer();
   //     freezer = gaveta.freezer;

        //Log.i("qtdGavetas", itensGaveta.gaveta.size() + " - Position: " +position);



        txtCodigo.setText("Código da Gaveta: "+gaveta.idGaveta);
        //txtCodigoFreezer.setText("Quantidade de Caixas: "+gaveta.);
        txtQtdCaixas.setText("Quantidade de caixas: "+gaveta.numeroCaixas);


        return convertView;
    }
}
