package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Diagnostico implements Serializable {
    public int idDiagnostico;
    public String codigo;
    public String sigla;
    public String nome;
    boolean status;
    public Cid cid;
   // public List<Amostra> amostras;
}
