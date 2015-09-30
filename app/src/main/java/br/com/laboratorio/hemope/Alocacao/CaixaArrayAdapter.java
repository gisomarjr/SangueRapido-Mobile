package br.com.laboratorio.hemope.Alocacao;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.laboratorio.hemope.Model.Caixa;
import br.com.laboratorio.hemope.Model.Freezer;
import br.com.laboratorio.hemope.R;

public class CaixaArrayAdapter extends ArrayAdapter<Caixa> {

private ArrayList<Caixa> objects;
private Context context;


    public CaixaArrayAdapter(Context context, int resource, ArrayList<Caixa> objects) {
        super(context, resource, objects);

        this.objects = objects;
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=(LayoutInflater) context.getSystemService(  Context.LAYOUT_INFLATER_SERVICE );
        View row=inflater.inflate(R.layout.support_simple_spinner_dropdown_item, parent, false);
        TextView label=(TextView)row.findViewById(android.R.id.text1);
        label.setText(String.valueOf(objects.get(position).idCaixa));

        if (position == 0) {//Special style for dropdown header
            //label.setTextColor(context.getResources().getColor(R.color.accent_material_light));
        }

        return row;
    }
}