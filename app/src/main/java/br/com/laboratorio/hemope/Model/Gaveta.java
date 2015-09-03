package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Gaveta implements Serializable {
    int idGaveta;
    int numeroCaixas;
    boolean status;
    Freezer freezer;
    List<Caixa> caixas;
}
