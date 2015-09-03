package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Diagnostico implements Serializable {
    int idDiagnostico;
    String codigo;
    String sigla;
    String nome;
    boolean status;
    Cid idCID;
    List<Amostra> amostras;
}
