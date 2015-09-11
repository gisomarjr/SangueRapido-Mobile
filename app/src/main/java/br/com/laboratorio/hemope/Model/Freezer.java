package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Freezer implements Serializable {
    public int idFreezer;
    public String codigo;
    public String descricao;
    public String qtdGavetas;
    public boolean status;
    public Laboratorio laboratorio;
    public List<Gaveta> gavetas;
}
