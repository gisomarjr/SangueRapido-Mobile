package br.com.laboratorio.hemope.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gisomar on 28/08/15.
 */
public class ItensPaciente implements Serializable {

    @SerializedName("ItensPaciente")
    public List<Paciente> paciente;

}
