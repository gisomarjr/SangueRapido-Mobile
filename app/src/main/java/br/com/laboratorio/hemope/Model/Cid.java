package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Cid implements Serializable {
    public int idCID;
    public String codigo;
    public String descricao;
    public boolean status;
    public List<Diagnostico> diagnosticos;
}
