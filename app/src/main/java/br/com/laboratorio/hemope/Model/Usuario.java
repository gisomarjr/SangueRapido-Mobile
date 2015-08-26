package br.com.laboratorio.hemope.Model;

import java.io.Serializable;

/**
 * Created by gisomar on 26/08/15.
 */
public class Usuario extends Pessoa implements Serializable {

   public String login;
   public String status;
   public String perfil;
   public String senha;
}
