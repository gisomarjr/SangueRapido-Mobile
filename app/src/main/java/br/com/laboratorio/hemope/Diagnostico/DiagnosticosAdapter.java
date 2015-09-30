package br.com.laboratorio.hemope.Diagnostico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.R;


public class DiagnosticosAdapter extends ArrayAdapter<Diagnostico>{

    public DiagnosticosAdapter(Context context, List<Diagnostico> diagnosticos) {
        super(context, 0, diagnosticos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_diagnostico, null);
        }

        TextView txtCodigo = (TextView)convertView.findViewById(R.id.codigo);
        TextView txtSigla = (TextView)convertView.findViewById(R.id.sigla);


        Diagnostico diagnostico = getItem(position);


        //Log.i("qtdDiagnosticos", itensDiagnostico.diagnostico.size() + " - Position: " +position);

        txtCodigo.setText("Código do diagnóstico: "+diagnostico.codigo);
        txtSigla.setText("Sigla diagnóstico: "+diagnostico.sigla);


        return convertView;
    }
}
