package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Laboratorio implements Serializable {
    int idLaboratorio;
    String nome;
    int andar;
    boolean status;
    List<Freezer> freezers;
}
