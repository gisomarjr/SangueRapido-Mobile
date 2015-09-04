package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Material implements Serializable {
    int idMaterial;
    String nome;
    List<Aliquota> aliquotas;
}
