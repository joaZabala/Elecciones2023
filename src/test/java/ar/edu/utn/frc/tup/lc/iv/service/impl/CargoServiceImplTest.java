package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.ChargeResponseDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@SpringBootTest
class CargoServiceImplTest {

    @MockBean
    RestTemplate restTemplate;

    @MockBean
    DistrictServiceImpl districtService;

    @SpyBean
    CargoServiceImpl cargoService;

    private String url = "http://api-elecciones:8080";

    @Test
    void getCargoBYDistrictId() {

        ChargeResponseDTO[] chargeResponseDTOS = {
          new ChargeResponseDTO(1L, "PRESI",1L)
        };

        DistrictoDTO districtoDTO = new DistrictoDTO(1L , "Cordoba");

        when(restTemplate.getForEntity(url +"/cargos?distritoId="+1L , ChargeResponseDTO[].class))
                .thenReturn(ResponseEntity.ok(chargeResponseDTOS));

        when(districtService.getDistrictById(1L)).thenReturn(List.of(districtoDTO));


        ChargeDTO chargeDTO = cargoService.getCargoBYDistrictId(1L);

        assertNotNull(chargeDTO);
        assertEquals(chargeDTO.getDistrito() , districtoDTO );

    }

    @Test
    void getCargoBYDistrictIdWithException() {


        when(restTemplate.getForEntity(url +"/cargos?distritoId="+1L , ChargeResponseDTO[].class))
                .thenReturn(ResponseEntity.status(400).build());

        assertThrows(ResponseStatusException.class , ()->{
         cargoService.getCargoBYDistrictId(1L);
        });

    }
}