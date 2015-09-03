package br.com.laboratorio.hemope.Model;

import java.io.Serializable;

/**
 * Created by User on 03/09/2015.
 */
public class Alocacao implements Serializable {
    int idAlocacao;
    int posicaoX;
    int posicaoY;
    boolean status;
    Caixa caixa;
    Aliquota aliquota;
}
