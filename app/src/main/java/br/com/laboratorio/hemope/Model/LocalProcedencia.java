package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class LocalProcedencia implements Serializable {
    int idLocalProcedencia;
    String nome;
    List<Amostra> amostras;
}
