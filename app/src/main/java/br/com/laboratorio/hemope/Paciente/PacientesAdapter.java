package br.com.laboratorio.hemope.Paciente;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;


public class PacientesAdapter extends ArrayAdapter<Paciente>{

    public PacientesAdapter(Context context, List<Paciente> pacientes) {
        super(context, 0, pacientes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_paciente, null);
        }

        //ImageView imageView = (ImageView)convertView.findViewById(R.id.imgCapa);
        TextView txtNome = (TextView)convertView.findViewById(R.id.nomePaciente);
        TextView txtNomeMae = (TextView)convertView.findViewById(R.id.nomeMaePaciente);
        TextView txtDataNascimento = (TextView)convertView.findViewById(R.id.dataNascimentoPaciente);


        Paciente paciente = getItem(position);


        //Log.i("qtdPacientes", itensPaciente.paciente.size() + " - Position: " +position);

        txtNome.setText(paciente.nome);
        txtNomeMae.setText(paciente.nomeMae);
        txtDataNascimento.setText(paciente.dataNascimento);

        /*//verifico se a imagem é nula
        if(livro.volumes.urlImagens != null) {
            Picasso.with(getContext())
                    .load(livro.volumes.urlImagens.urlImagem)
                    .into(imageView);
        }else {
            //imagem caso o não encontre o livro
            Picasso.with(getContext())
                    .load("http://rlv.zcache.com.br/ponto_de_interrogacao_dos_desenhos_animados_papel_timbrado-ra082215bdfb44a0d9fc49d7ba691a9df_vg63g_8byvr_512.jpg")
                    .into(imageView);
        }*/

        return convertView;
    }
}
