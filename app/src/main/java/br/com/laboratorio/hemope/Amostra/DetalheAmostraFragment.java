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

       TextView txtNomePaciente = (TextView) view.findViewById(R.id.labelNomePaciente);
        TextView txtCodigoAmostra = (TextView) view.findViewById(R.id.labelCodigoAmostra);
        TextView txtTipoAmostra = (TextView) view.findViewById(R.id.labelTipoAmostra);
        TextView txtLocalProc = (TextView) view.findViewById(R.id.labelLocalProcedencia);
        TextView txtDataEntrada = (TextView) view.findViewById(R.id.labelDataEntrada);
        TextView txtCodigoDiagnostico = (TextView) view.findViewById(R.id.labelCodigoDiagnostico);
        TextView txtSigla = (TextView) view.findViewById(R.id.labelSiglaDiagnostico);
        TextView txtNomeDiagnostico = (TextView) view.findViewById(R.id.labelNomeDiagnostico);
        TextView txtCodigoCid = (TextView) view.findViewById(R.id.labelCodigoCid);
        TextView txtDescricaoCid = (TextView) view.findViewById(R.id.labelDescricaoCid);

        txtCodigoAmostra.setText("Cód. Diagnóstico: "+amostra.codigo);
        txtNomePaciente.setText("Paciente: "+paciente.nome);
        txtTipoAmostra.setText("Tipo da amostra: "+tipoAmostra.nome);
        txtLocalProc.setText("Local Procedência: "+localProcedencia.nome);
        txtDataEntrada.setText("Data de Entrada: "+amostra.dataEntrada);
        txtCodigoDiagnostico.setText("Código diagnóstico: "+diagnostico.codigo);
        txtSigla.setText("Sigla diagnóstico: "+diagnostico.sigla);
        txtNomeDiagnostico.setText("Desc. Diagnóstico: "+diagnostico.nome);
        txtCodigoCid.setText("Cód. CID: "+cid.codigo);
        txtDescricaoCid.setText("Descrição CID: "+cid.descricao);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_amostra, menu);

    }



}
