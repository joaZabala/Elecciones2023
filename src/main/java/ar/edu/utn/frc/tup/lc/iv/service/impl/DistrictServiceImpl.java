package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.District;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import ar.edu.utn.frc.tup.lc.iv.service.DistrictService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class DistrictServiceImpl implements DistrictService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Value("${api.url}")
    private String url;

    @Override
    public List<DistrictoDTO> getDistricts(String distritoName) {
        log.info("Iniciando obtención de cargos para el distrito con ID: {}", distritoName);

        ResponseEntity<District[]> response;
        if(distritoName != null) {
            response = restTemplate.getForEntity(url +"/distritos?distritoNombre="+distritoName, District[].class);
        }else{
        response = restTemplate.getForEntity(url +"/distritos", District[].class);
        }

        if(response.getStatusCode().is2xxSuccessful()){
            List<DistrictoDTO> distritosList = new ArrayList<>();
            
            for (District district : Objects.requireNonNull(response.getBody())) {
                
                DistrictoDTO districtoDTO =new DistrictoDTO(district.distritoId() , district.distritoNombre());
                distritosList.add(districtoDTO);
            }

            return distritosList;
        }else{
            throw new EntityNotFoundException("No se encontraron distritos");
        }
    }

    @Override
    public List<DistrictoDTO> getDistrictById(Long id) {

        ResponseEntity<District[]> response = restTemplate.getForEntity(url +"/distritos?distritoId="+id, District[].class);

        if(response.getStatusCode().is2xxSuccessful()){

            List<DistrictoDTO> distritosList = new ArrayList<>();

            for (District district : Objects.requireNonNull(response.getBody())) {

                DistrictoDTO districtoDTO =new DistrictoDTO(district.distritoId() , district.distritoNombre());
                distritosList.add(districtoDTO);
            }

            return distritosList;
        }else{
            throw new EntityNotFoundException("No se encontro el distrito");
        }
    }
}
