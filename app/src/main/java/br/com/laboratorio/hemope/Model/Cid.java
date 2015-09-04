package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Cid implements Serializable {
    int idCID;
    String codigo;
    String descricao;
    boolean status;
    List<Diagnostico> diagnosticos;
}
