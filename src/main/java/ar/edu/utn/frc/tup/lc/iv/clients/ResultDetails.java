package ar.edu.utn.frc.tup.lc.iv.clients;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultDetails {

    private int orden;
    private String nombre;
    private int votos ;
    private double porcentaje;
}
