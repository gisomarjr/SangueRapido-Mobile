package br.com.laboratorio.hemope.Amostra;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Cid;
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.LocalProcedencia;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.Model.TipoAmostra;
import br.com.laboratorio.hemope.R;


public class DetalheAmostraFragment extends Fragment {

    private static final String LOG_TAG = DetalheAmostraFragment.class.getSimpleName();


    private MenuItem menuItemFavorito;


    public static DetalheAmostraFragment novaInstancia(Amostra amostra) {

        DetalheAmostraFragment dpf = new DetalheAmostraFragment();
        Bundle args = new Bundle();
        args.putSerializable("amostra", amostra);
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
        TipoAmostra tipoAmostra = new TipoAmostra();
        LocalProcedencia localProcedencia = new LocalProcedencia();
        diagnostico = amostra.diagnostico;
        cid = diagnostico.cid;
        paciente = amostra.paciente;
        tipoAmostra = amostra.tipoAmostra;
        localProcedencia = amostra.localProcedencia;

        amostra = (Amostra) getArguments().getSerializable("amostra");

        View view = inflater.inflate(R.layout.fragment_detalhe_amostra, container, false);

        //ImageView imgCapa = (ImageView) view.findViewById(R.id.imgCapa);

       // TextView txtNomePaciente = (TextView) view.findViewById(R.id.labelNomePaciente);
        //TextView txtCpf = (TextView) view.findViewById(R.id.labelCpf);
       // TextView txtCodigoAmostra = (TextView) view.findViewById(R.id.labelCodAmostra);
        TextView txtCodigoAmostra = (TextView) view.findViewById(R.id.labelCodigoAmostra);
        TextView txtSigla = (TextView) view.findViewById(R.id.labelSigla);
       // TextView txtNome = (TextView) view.findViewById(R.id.labelNomeAmostra);
       // TextView txtCodigoCid = (TextView) view.findViewById(R.id.labelCodigoCid);
       // TextView txtDescricaoCid = (TextView) view.findViewById(R.id.labelDescricaoCid);



        /*txtNomePaciente.setText("Nome do Paciente: "+paciente.nome);
        txtCpf.setText("CPF: "+paciente.cpf);
        txtCodigoAmostra.setText("Cód. Amostra: "+amostra.codigo);*/
        txtCodigoAmostra.setText("Cód. Diagnóstico: "+amostra.codigo);
       // txtSigla.setText("Sigla Diagnóstico: "+amostra.sigla);
        //txtNome.setText("Descrição Diagnóstico: "+amostra.nome);
        /*txtCodigoCid.setText("Código CID: "+amostra.cid.codigo);
        txtDescricaoCid.setText("Descrição CID: "+amostra.cid.descricao);*/


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_amostra, menu);

    }



}
