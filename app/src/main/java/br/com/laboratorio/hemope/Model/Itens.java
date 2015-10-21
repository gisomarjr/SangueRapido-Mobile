package br.com.laboratorio.hemope.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gisomar on 28/08/15.
 */
public class Itens implements Serializable {
    @SerializedName("localProcedencia")
    public LocalProcedencia localProcedencia;
    public Aliquota aliquota;
    List<Aliquota> aliquotas;
    List<Alocacao> alocacoes;
    public List<Amostra> amostras;
    public List<Caixa> caixas;
    List<Cid> cids;
    @SerializedName("diagnosticos")
    public List<Diagnostico> diagnosticos;
    List<Endereco> enderecos;
    public List<Freezer> freezers;
    public List<Gaveta> gavetas;
    public List<Laboratorio> laboratorios;

    public List<LocalProcedencia> localProcedencias;
    public List<Material> materiais;
    public List<TipoAmostra> tipoAmostras;
    List<Usuario> usuarios;
    public Usuario usuario;
    @SerializedName("pacientes")
    public List<Paciente> paciente;
    public boolean isSuccess;
    public boolean errorCode;
    public String errorMessage;


}
