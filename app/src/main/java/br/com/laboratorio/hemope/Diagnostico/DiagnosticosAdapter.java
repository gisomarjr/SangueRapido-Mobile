package br.com.laboratorio.hemope.Diagnostico;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imgCapa);
        TextView txtNome = (TextView)convertView.findViewById(R.id.nomeDiagnostico);
        TextView txtNomeMae = (TextView)convertView.findViewById(R.id.nomeMaeDiagnostico);
        TextView txtDataNascimento = (TextView)convertView.findViewById(R.id.dataNascimentoDiagnostico);


        Diagnostico diagnostico = getItem(position);


        //Log.i("qtdDiagnosticos", itensDiagnostico.diagnostico.size() + " - Position: " +position);

        txtNome.setText(diagnostico.nome);
        txtNomeMae.setText(diagnostico.nomeMae);
        txtDataNascimento.setText(diagnostico.dataNascimento);

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
