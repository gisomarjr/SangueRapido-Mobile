package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Freezer implements Serializable {
    int idFreezer;
    String codigo;
    String descricao;
    String qtdGavetas;
    boolean status;
    Laboratorio laboratorio;
    List<Gaveta> gavetas;
}
