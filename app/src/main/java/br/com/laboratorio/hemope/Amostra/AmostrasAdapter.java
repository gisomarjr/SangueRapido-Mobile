package br.com.laboratorio.hemope.Amostra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;


public class AmostrasAdapter extends ArrayAdapter<Amostra>{

    public AmostrasAdapter(Context context, List<Amostra> amostras) {
        super(context, 0, amostras);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_amostra, null);
        }

        TextView txtCodigo = (TextView)convertView.findViewById(R.id.codAmostra);
        TextView txtPaciente = (TextView)convertView.findViewById(R.id.nomePaciente);


        Amostra amostra = getItem(position);
        Paciente paciente = new Paciente();
        paciente = amostra.paciente;

        //Log.i("qtdAmostras", itensAmostra.amostra.size() + " - Position: " +position);

        txtCodigo.setText("Código da amostra: "+amostra.codigo);
        txtPaciente.setText("Paciente: "+paciente.nome);


        return convertView;
    }
}
