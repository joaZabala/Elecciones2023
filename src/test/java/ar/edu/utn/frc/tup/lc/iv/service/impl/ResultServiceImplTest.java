package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.ResultApiClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static javax.management.Query.eq;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ResultServiceImplTest {

    @MockBean
    private RestTemplate restTemplate;

    @SpyBean
    ResultServiceImpl resultService;

    private String url ="http://api-elecciones:8080";
    @Test
    void getResult() {

        // Datos de prueba
        Long seccionId = 1L;
        Long distritoId = 1L;

        ResultApiClient[] mockResponse = new ResultApiClient[]{
                new ResultApiClient(1L,"Seccion 1",
                        "Union",1L,"La libertad avanza",300,"IMPUTADOS")
        };


        when(restTemplate.getForEntity(url + "/resultados?seccionId="+seccionId,
                ResultApiClient[].class)).thenReturn(ResponseEntity.ok(mockResponse));

        // Llamar al método
        ResultDTO resultDTO = resultService.getResult(seccionId, distritoId);

        // Verificaciones
        assertNotNull(resultDTO);
        assertEquals("Seccion 1", resultDTO.getSeccion());
        assertEquals("Union", resultDTO.getDistrito());
        assertEquals(1, resultDTO.getResultados().size());
        assertEquals("La libertad avanza", resultDTO.getResultados().get(0).getNombre());
        assertEquals(300, resultDTO.getResultados().get(0).getVotos());
    }
}