package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by gisomar on 26/08/15.
 */
public class Paciente extends Pessoa implements Serializable {

   public String fichaClinica;
   public String nomeMae;
   List<Amostra> amostras;
}
