package br.com.laboratorio.hemope.Caixa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Gaveta;
import br.com.laboratorio.hemope.R;


public class CaixasAdapter extends ArrayAdapter<Caixa>{

    public CaixasAdapter(Context context, List<Caixa> caixas) {
        super(context, 0, caixas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_caixa, null);
        }

        TextView txtIndice = (TextView)convertView.findViewById(R.id.txtIndiceCaixa);

        Caixa caixa = getItem(position);

        txtIndice.setText(" Caixa: "+caixa.idCaixa);

        return convertView;
    }
}