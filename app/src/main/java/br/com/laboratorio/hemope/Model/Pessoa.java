package br.com.laboratorio.hemope.Model;

import java.io.Serializable;

/**
 * Created by gisomar on 26/08/15.
 */
public class Pessoa implements Serializable {

  public int idPessoa;
  public String cpf;
  public String telefone;
  public String email;
  public String dataNascimento;
  public String nome;
  public Endereco endereco;
}
