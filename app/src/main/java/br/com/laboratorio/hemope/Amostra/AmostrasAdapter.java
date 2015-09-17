package br.com.laboratorio.hemope.Amostra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.laboratorio.hemope.Model.Amostra;
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

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imgCapa);
        TextView txtCodigo = (TextView)convertView.findViewById(R.id.codigo);
        TextView txtSigla = (TextView)convertView.findViewById(R.id.sigla);


        Amostra amostra = getItem(position);


        //Log.i("qtdAmostras", itensAmostra.amostra.size() + " - Position: " +position);

        txtCodigo.setText(amostra.codigo);
     //   txtSigla.setText(amostra.sigla);

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
