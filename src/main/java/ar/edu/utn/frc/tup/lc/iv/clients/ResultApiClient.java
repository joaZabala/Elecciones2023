package ar.edu.utn.frc.tup.lc.iv.clients;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultApiClient {
    private Long seccionId;
    private String seccionNombre;
    private String distritoNombre;
    private Long agrupacionId;
    private String agrupacionNombre;
    private Integer votosCantidad;
    private String votosTipo;
}

