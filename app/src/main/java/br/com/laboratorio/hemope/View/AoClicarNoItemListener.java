package br.com.laboratorio.hemope.View;


import br.com.laboratorio.hemope.Model.Aliquota;
import br.com.laboratorio.hemope.Model.Amostra;
import br.com.laboratorio.hemope.Model.Diagnostico;
import br.com.laboratorio.hemope.Model.Paciente;

public interface AoClicarNoItemListener {
    void onClick(Paciente paciente);
    void onClick(Diagnostico diagnostico);
    void onClick(Amostra amostra);
    void onClick(Aliquota aliquota);
}
