package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 03/09/2015.
 */
public class Aliquota implements Serializable {

   public int idAliquota;
   public String dataEntrada;
   public String dataDescarte;
   public String situacao;
   public float volume;
   public float concentracao;
   public Alocacao alocacao;
   public boolean status;
   public Amostra amostra;
   public Material material;
}
