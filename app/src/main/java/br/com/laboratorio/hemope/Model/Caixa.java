package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Caixa implements Serializable{

   public int idCaixa;
   public int qtdX;
   public int qtdY;
   public boolean status;
   public Gaveta gaveta;
   public ArrayList<Alocacao> alocacoes;
}
