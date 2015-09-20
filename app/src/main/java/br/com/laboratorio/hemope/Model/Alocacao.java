package br.com.laboratorio.hemope.Model;

import java.io.Serializable;

/**
 * Created by User on 03/09/2015.
 */
public class Alocacao implements Serializable {

   public int idAlocacao;
   public String posicaoX;
   public String posicaoY;
   public boolean status;
   public Caixa caixa;
   public Aliquota aliquota;
}
