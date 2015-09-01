package br.com.laboratorio.hemope.Paciente;

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

import br.com.laboratorio.hemope.Model.Endereco;
import br.com.laboratorio.hemope.Model.Paciente;
import br.com.laboratorio.hemope.R;



public class DetalhePacienteFragment extends Fragment {



    private static final String LIVROS_SHARE_HASHTAG = "#WorldsBooks ";
    private static String SHARE_DEFAULT_TEXT = "";
    private static final String LOG_TAG = DetalhePacienteFragment.class.getSimpleName();

    private Paciente paciente;

    private MenuItem menuItemFavorito;


    public static DetalhePacienteFragment novaInstancia(Paciente paciente) {

        DetalhePacienteFragment dpf = new DetalhePacienteFragment();
        Bundle args = new Bundle();
        args.putSerializable("paciente", paciente);
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

        this.paciente = (Paciente) getArguments().getSerializable("paciente");

        View view = inflater.inflate(R.layout.fragment_detalhe_paciente, container, false);

        ImageView imgCapa = (ImageView) view.findViewById(R.id.imgCapa);
        TextView txtNome = (TextView) view.findViewById(R.id.txtNomePaciente);
        TextView txtMae = (TextView) view.findViewById(R.id.txtNomeMae);
        TextView txtNascimento = (TextView) view.findViewById(R.id.txtDataNascimentoPaciente);
        TextView txtCpf = (TextView) view.findViewById(R.id.txtCpf);
        TextView txtEmail = (TextView) view.findViewById(R.id.email);
        TextView txtTelefone = (TextView) view.findViewById(R.id.txtTelefone);
        TextView txtFicha = (TextView) view.findViewById(R.id.fichaClinica);

        TextView txtRua = (TextView) view.findViewById(R.id.rua);
        TextView txtComplemento = (TextView) view.findViewById(R.id.complemento);
        TextView txtBairro = (TextView) view.findViewById(R.id.bairro);
        TextView txtCidade = (TextView) view.findViewById(R.id.cidade);
        TextView txtEstado = (TextView) view.findViewById(R.id.estado);
        TextView txtNumero = (TextView) view.findViewById(R.id.numero);
        TextView txtPais = (TextView) view.findViewById(R.id.pais);
        TextView txtCep = (TextView) view.findViewById(R.id.cep);

        Endereco endereco = new Endereco();
        endereco = paciente.endereco;

        //Picasso.with(getActivity()).load(livro.capa).into(imgCapa);
        txtNome.setText(paciente.nome);
        txtMae.setText(paciente.nomeMae);
        txtNascimento.setText(paciente.dataNascimento);
        txtCpf.setText(paciente.cpf);
        txtEmail.setText(paciente.email);
        txtTelefone.setText(paciente.telefone);
        txtFicha.setText(paciente.fichaClinica);

        txtBairro.setText(endereco.bairro);
        txtCep.setText(endereco.cep);
        txtCidade.setText(endereco.cidade);
        txtComplemento.setText(endereco.complemento);
        txtEstado.setText(endereco.estado);
        txtPais.setText(endereco.pais);
        txtRua.setText(endereco.rua);
        txtNumero.setText(endereco.numero);
       /* if(livro.volumes.urlImagens != null) {
            Picasso.with(getActivity())
                    .load(livro.volumes.urlImagens.urlImagem)
                    .into(imgCapa);
        }else{
            //imagem caso o não encontre o livro
            Picasso.with(getActivity())
                    .load("http://rlv.zcache.com.br/ponto_de_interrogacao_dos_desenhos_animados_papel_timbrado-ra082215bdfb44a0d9fc49d7ba691a9df_vg63g_8byvr_512.jpg")
                    .into(imgCapa);
        }*/
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detalhe_paciente, menu);
        //MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
       /* ShareActionProvider mShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Attach an intent to this ShareActionProvider.  You can update this at any time,
        // like when the user selects a new piece of data they might like to share.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(createShareForecastIntent());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null?");
        }

        //ITEM DO FAVORITO
        menuItemFavorito = menu.findItem(R.id.action_favorito);

        if (isFavorito(this.livro)) {
            menuItemFavorito.setIcon(R.drawable.ic_action_add_favorite);
        } else {
            menuItemFavorito.setIcon(R.drawable.ic_action_remove_favorito);
        }

        //ITEM DA VENDA DO LIVRO
        menuItemVenda = menu.findItem(R.id.action_venda);
        String compraDisponivel = getResources().getString(R.string.compra_disponivel);

        switch (livro.venda.status) {

            case "FOR_SALE":
                menuItemVenda.setIcon(R.drawable.carrinho_disponivel);

                menuItemVenda.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent = null;
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(livro.venda.linkVenda));

                        startActivity(intent);
                        return false;
                    }
                });
                break;
            case "FREE":
                menuItemVenda.setIcon(R.drawable.carrinho_disponivel);

                menuItemVenda.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        Intent intent = null;
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse(livro.venda.linkVenda));

                        startActivity(intent);
                        return false;
                    }
                });
                break;
            case "NOT_FOR_SALE":
                menuItemVenda.setIcon(R.drawable.carrinho_indisponivel);

                menuItemVenda.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        Toast.makeText(getActivity(),R.string.compra_indisponivel,Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
                break;

            default:
                menuItemVenda.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        Toast.makeText(getActivity(),R.string.compra_indisponivel,Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
                break;

        }*/
    }

    /*private Intent createShareForecastIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT,
                SHARE_DEFAULT_TEXT + " \r\n " + livro.volumes.titulo + " \r\n "
                +livro.volumes.informacaoLink + " \r\n \r\n" + LIVROS_SHARE_HASHTAG );
        return shareIntent;
    }*/





/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorito) {

            ContentValues values = new ContentValues();

            values.put(LivrosDbHelper.CAMPO_TITULO, livro.volumes.titulo);
            values.put(LivrosDbHelper.CAMPO_DATA_PUBLICACAO, livro.volumes.dataPublicacao);
            values.put(LivrosDbHelper.CAMPO_CAPA, livro.volumes.urlImagens.urlImagem);
            values.put(LivrosDbHelper.CAMPO_DESCRICAO, livro.volumes.descricao);
            values.put(LivrosDbHelper.CAMPO_URL_LIVRO, livro.volumes.informacaoLink);
            //SALVAR INFORMAÇÕES TAMBÉM DE VENDA DO LIVRO
            values.put(LivrosDbHelper.CAMPO_VENDA_LINK, livro.venda.linkVenda);
            values.put(LivrosDbHelper.CAMPO_VENDA_STATUS, livro.venda.status);
            if(livro.venda.preco != null) {
                values.put(LivrosDbHelper.CAMPO_VALOR_LIVRO, livro.venda.preco.valor);
            }

            if (isFavorito(this.livro)) {
                getActivity().getContentResolver().delete(
                        Uri.parse(LivrosDbHelper.ENDERECO_PROVIDER),
                        LivrosDbHelper.CAMPO_TITULO +" = ?",
                        new String[]{ String.valueOf(livro.volumes.titulo) }
                );
            } else {
                getActivity().getContentResolver().insert(
                        Uri.parse(LivrosDbHelper.ENDERECO_PROVIDER), values
                );
            }
            getActivity().invalidateOptionsMenu();
            return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private boolean isFavorito(Livro livro) {
        Cursor cursor = getActivity().getContentResolver().query(
                Uri.parse(LivrosDbHelper.ENDERECO_PROVIDER),
                        new String[]{ LivrosDbHelper.CAMPO_ID },

                        LivrosDbHelper.CAMPO_TITULO +" = ?",

                        new String[]{ livro.volumes.titulo},
                        null);
        boolean existe = cursor.moveToNext();
        cursor.close();
        return existe;

    }*/


}
