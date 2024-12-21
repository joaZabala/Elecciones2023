package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.SeccionClient;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import ar.edu.utn.frc.tup.lc.iv.service.SeccionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeccionServiceImpl implements SeccionService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${api.url}")
    private String url;

    @Override
    public List<SeccionDTO> getSecciones(Long distrito_id, Long seccion_id) {

        ResponseEntity<SeccionClient[]>response;

        if(seccion_id != null){
           response = restTemplate.getForEntity(url +"/secciones?seccionId="+seccion_id+"&distritoId="+distrito_id, SeccionClient[].class);
        }else{
          response = restTemplate.getForEntity(url +"/secciones?distritoId="+distrito_id, SeccionClient[].class);
        }

        if(response.getStatusCode().is2xxSuccessful() & response.getBody() != null){

            List<SeccionDTO>seccionDTOList = new ArrayList<>();
            for (SeccionClient sc : response.getBody()){

                seccionDTOList.add(new SeccionDTO(sc.seccionId(),sc.seccionNombre()));
            }

            return seccionDTOList;
        }else{
            throw new EntityNotFoundException("No existen secciones con el id especificado");
        }

    }
}
