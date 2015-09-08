package br.com.laboratorio.hemope.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gisomar on 28/08/15.
 */
public class Itens implements Serializable {

    public Aliquota aliquota;
    List<Aliquota> aliquotas;
    List<Alocacao> alocacoes;
    List<Amostra> amostras;
    List<Caixa> caixas;
    List<Cid> cids;
    List<Diagnostico> diagnosticos;
    List<Endereco> enderecos;
    List<Freezer> freezers;
    List<Gaveta> gavetas;
    List<Laboratorio> laboratorios;
    List<LocalProcedencia> localProcedencias;
    List<Material> materiais;
    List<TipoAmostra> tipoAmostras;
    List<Usuario> usuarios;

    @SerializedName("pacientes")
    public List<Paciente> paciente;

}
