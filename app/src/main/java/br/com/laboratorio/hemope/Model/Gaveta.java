package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Gaveta implements Serializable {

   public int idGaveta;
   public int numeroCaixas;
   public boolean status;
   public Freezer freezer;
   public List<Caixa> caixas;
}
