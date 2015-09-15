package br.com.laboratorio.hemope.Diagnostico;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Cid;
import br.com.laboratorio.hemope.Model.Endereco;
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;


public class DetalheDiagnosticoFragment extends Fragment {

    private static final String LOG_TAG = DetalheDiagnosticoFragment.class.getSimpleName();


    private MenuItem menuItemFavorito;


    public static DetalheDiagnosticoFragment novaInstancia(Diagnostico diagnostico) {

        DetalheDiagnosticoFragment dpf = new DetalheDiagnosticoFragment();
        Bundle args = new Bundle();
        args.putSerializable("diagnostico", diagnostico);
        dpf.setArguments(args);
        return dpf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Amostra amostra = new Amostra();
        Diagnostico diagnostico = new Diagnostico();
        Cid cid = new Cid();
        Paciente paciente = new Paciente();


        diagnostico = (Diagnostico) getArguments().getSerializable("diagnostico");

        View view = inflater.inflate(R.layout.fragment_detalhe_diagnostico, container, false);

        //ImageView imgCapa = (ImageView) view.findViewById(R.id.imgCapa);

       // TextView txtNomePaciente = (TextView) view.findViewById(R.id.labelNomePaciente);
        //TextView txtCpf = (TextView) view.findViewById(R.id.labelCpf);
       // TextView txtCodigoAmostra = (TextView) view.findViewById(R.id.labelCodAmostra);
        TextView txtCodigoDiagnostico = (TextView) view.findViewById(R.id.labelCodigoDiagnostico);
        TextView txtSigla = (TextView) view.findViewById(R.id.labelSigla);
        TextView txtNome = (TextView) view.findViewById(R.id.labelNomeDiagnostico);
       // TextView txtCodigoCid = (TextView) view.findViewById(R.id.labelCodigoCid);
       // TextView txtDescricaoCid = (TextView) view.findViewById(R.id.labelDescricaoCid);



        /*txtNomePaciente.setText("Nome do Paciente: "+paciente.nome);
        txtCpf.setText("CPF: "+paciente.cpf);
        txtCodigoAmostra.setText("Cód. Amostra: "+amostra.codigo);*/
        txtCodigoDiagnostico.setText("Cód. Diagnóstico: "+diagnostico.codigo);
        txtSigla.setText("Sigla Diagnóstico: "+diagnostico.sigla);
        txtNome.setText("Descrição Diagnóstico: "+diagnostico.nome);
        /*txtCodigoCid.setText("Código CID: "+diagnostico.cid.codigo);
        txtDescricaoCid.setText("Descrição CID: "+diagnostico.cid.descricao);*/


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_diagnostico, menu);

    }



}
