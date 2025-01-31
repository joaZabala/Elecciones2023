package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.SeccionClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SeccionServiceImplTest {

    @MockBean
    RestTemplate restTemplate;

    @SpyBean
    SeccionServiceImpl seccionService;

    @Value("${api.url}")
    private String url;
    @Test
    void getSecciones() {
        SeccionClient[] seccionClient ={
                new SeccionClient(1L , "joaquin",20L)
        };

        SeccionDTO seccionDTO = new SeccionDTO(1 , "joaquin");

        when(restTemplate.getForEntity(url+"/secciones?distritoId="+1L , SeccionClient[].class))
                .thenReturn(ResponseEntity.ok(seccionClient));

        List<SeccionDTO> response = seccionService.getSecciones(1L , null);

        assertEquals(response.get(0) , seccionDTO);

    }


    @Test
    void getSeccionesBySeccionId() {
        SeccionClient[] seccionClient ={
                new SeccionClient(1L , "joaquin",20L)
        };

        SeccionDTO seccionDTO = new SeccionDTO(1 , "joaquin");

        when(restTemplate.getForEntity(url+"/secciones?seccionId="+20L+"&distritoId="+1L , SeccionClient[].class))
                .thenReturn(ResponseEntity.ok(seccionClient));

        List<SeccionDTO> response = seccionService.getSecciones(1L , 20L);

        assertEquals(response.get(0) , seccionDTO);

    }

    @Test
    void getSeccionesBySeccionIdThrows() {

        when(restTemplate.getForEntity(url+"/secciones?seccionId="+20L+"&distritoId="+1L , SeccionClient[].class))
                .thenReturn(ResponseEntity.status(404).build());

        assertThrows(EntityNotFoundException.class , ()->{
            seccionService.getSecciones(1L , 20L);
        });

    }
}