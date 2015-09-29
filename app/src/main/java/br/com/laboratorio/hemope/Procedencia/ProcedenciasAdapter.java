package br.com.laboratorio.hemope.Procedencia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.LocalProcedencia;
import br.com.laboratorio.hemope.R;

/**
 * Created by User on 29/09/2015.
 */
public class ProcedenciasAdapter extends ArrayAdapter<LocalProcedencia> {
    public ProcedenciasAdapter(Context context, List<LocalProcedencia> procedencias) {
        super(context, 0, procedencias);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_procedencia, null);
        }

        TextView txtLocalProcedencia = (TextView)convertView.findViewById(R.id.localProcedencia);
        LocalProcedencia procedencia = getItem(position);

        //Log.i("qtdProcedencias", itensProcedencia.procedencia.size() + " - Position: " +position);

        txtLocalProcedencia.setText(procedencia.nome);


        return convertView;
    }
}
