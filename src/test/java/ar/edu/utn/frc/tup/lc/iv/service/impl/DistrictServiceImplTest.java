package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.District;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class DistrictServiceImplTest {

    @MockBean
    RestTemplate restTemplate;

    @SpyBean
    DistrictServiceImpl districtService;

    private String url = "http://api-elecciones:8080";

    @Test
    void getDistricts() {

        District[] districts ={
                new District(1L , "Union"),
                new District(2L , "Carlos Paz"),
        };

        when(restTemplate.getForEntity(url +"/distritos", District[].class))
                .thenReturn(ResponseEntity.ok(districts));

        List<DistrictoDTO> districtoDTOList = districtService.getDistricts(null);
        assertEquals(districtoDTOList.size() , 2);
    }

    @Test
    void getDistrictsByName() {

        District[] districts ={
                new District(1L , "Union")
        };

        when(restTemplate.getForEntity(url +"/distritos?distritoNombre=Union", District[].class))
                .thenReturn(ResponseEntity.ok(districts));

        List<DistrictoDTO> districtoDTOList = districtService.getDistricts("Union");
        assertEquals(districtoDTOList.get(0).getNombre() ,"Union" );
    }


    @Test
    void getDistrictsException() {

        District[] districts ={
                new District(1L , "Union")
        };

        when(restTemplate.getForEntity(url +"/distritos", District[].class))
                .thenReturn(ResponseEntity.status(404).build());

        assertThrows( EntityNotFoundException.class , ()->{
           districtService.getDistricts(null);
        });
    }

    @Test
    void getDistrictById() {
        District[] districts ={
                new District(1L , "Union")
        };

        when(restTemplate.getForEntity(url +"/distritos?distritoId="+1L, District[].class))
                .thenReturn(ResponseEntity.ok(districts));

        List<DistrictoDTO> districtoDTOList = districtService.getDistrictById(1L);
        assertEquals(districtoDTOList.get(0).getNombre() ,"Union" );

    }


    @Test
    void getDistrictByIdThrows() {


        when(restTemplate.getForEntity(url +"/distritos?distritoId="+1L, District[].class))
                .thenReturn(ResponseEntity.status(404).build());

        assertThrows(EntityNotFoundException.class , ()->{
            districtService.getDistrictById(1L);
        });

    }
}