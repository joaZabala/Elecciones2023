package ar.edu.utn.frc.tup.lc.iv.clients;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RestClient {

    @Value("${api.url}")
    private String url;

    @Autowired
    RestTemplate restTemplate;
    @CircuitBreaker(name = "cargoService" , fallbackMethod = "fallbackCargos")
    public  ResponseEntity<ChargeResponseDTO[]> getCharges(Long id){

        return restTemplate.getForEntity(url+"/cargos?distritoId="+id , ChargeResponseDTO[].class);
    }

    public ResponseEntity<ChargeResponseDTO[]> fallbackCargos(Long distrito_id , Exception e){
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Ocurrio un error");
    }
}
