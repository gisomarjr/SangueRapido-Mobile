package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Caixa implements Serializable{
    int idCaixa;
    int qtdX;
    int qtdY;
    boolean status;
    Gaveta gaveta;
    List<Alocacao> alocacoes;
}
