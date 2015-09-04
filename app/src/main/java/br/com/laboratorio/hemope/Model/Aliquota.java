package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by User on 03/09/2015.
 */
public class Aliquota implements Serializable {
    int idAliquota;
    Date dataEntrada;
    Date dataDescarte;
    String situacao;
    float volume;
    float concentracao;
    Alocacao alocacao;
    boolean status;
    Amostra amostra;
    Material material;
}
