package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Amostra implements Serializable {
    public int idAmostra;
    public String codigo;
    public Paciente paciente;
    public TipoAmostra tipoAmostra;
    public LocalProcedencia localProcedencia;
    public String dataEntrada;
    public Diagnostico diagnostico;
    public List<Aliquota> aliquotas;
    boolean status;
}
