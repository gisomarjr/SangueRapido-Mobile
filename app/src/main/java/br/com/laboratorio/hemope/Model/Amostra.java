package br.com.laboratorio.hemope.Model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 03/09/2015.
 */
public class Amostra implements Serializable {
    int idAmostra;
    String codigo;
    Paciente paciente;
    TipoAmostra tipoAmostra;
    LocalProcedencia localProcedencia;
    Date dataEntrada;
    Diagnostico diagnostico;
    List<Aliquota> aliquotas;
    boolean status;
}
