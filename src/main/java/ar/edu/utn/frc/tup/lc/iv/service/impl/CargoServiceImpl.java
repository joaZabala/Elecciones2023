package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.ChargeResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.clients.RestClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.Cargo;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import ar.edu.utn.frc.tup.lc.iv.service.CargoService;
import ar.edu.utn.frc.tup.lc.iv.service.DistrictService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class CargoServiceImpl implements CargoService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.url}")
    private String url;

    @Autowired
    private DistrictService districtService;

    @Autowired
    RestClient restClient;
    @Override
    @CircuitBreaker(name = "cargoService" , fallbackMethod = "fallbackCargos")
    public ChargeDTO getCargoBYDistrictId(Long distrito_id) {
        log.info("PASO");

        ResponseEntity<ChargeResponseDTO[]> response = restClient.getCharges(distrito_id);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {

            List<DistrictoDTO> districtoDTO = districtService.getDistrictById(distrito_id);

            ChargeResponseDTO[] chargeResponseDTO = response.getBody();

            ChargeDTO chargeDTO = new ChargeDTO();
            chargeDTO.setDistrito(districtoDTO.get(0));

            List<Cargo> cargoList = new ArrayList<>();
            for (ChargeResponseDTO cargo : chargeResponseDTO ) {

                cargoList.add(new Cargo(cargo.cargoId(), cargo.cargoNombre()));
            }
            chargeDTO.setCargos(cargoList);

            return chargeDTO;
        }else{
            throw new EntityNotFoundException("No se encontraron cargos");
        }
    }

    public ChargeDTO fallbackCargos(Long distrito_id , Exception e){
        throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"Ocurrio un error");
    }
}
