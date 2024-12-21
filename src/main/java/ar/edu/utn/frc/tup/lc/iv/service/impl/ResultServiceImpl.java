package ar.edu.utn.frc.tup.lc.iv.service.impl;

import ar.edu.utn.frc.tup.lc.iv.clients.ResultApiClient;
import ar.edu.utn.frc.tup.lc.iv.clients.ResultDetails;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.entities.ResultDetailEntity;
import ar.edu.utn.frc.tup.lc.iv.entities.ResultEntity;
import ar.edu.utn.frc.tup.lc.iv.service.ResultService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    ModelMapper modelMapper;

    @Value("${api.url}")
    private String url;

    @Override
    public ResultDTO getResult(Long seccionId, Long distritoId) {

        ResponseEntity<ResultApiClient[]> response = restTemplate.getForEntity(url+"/resultados?seccionId="+seccionId ,
                ResultApiClient[].class);


        if(response.getStatusCode().is2xxSuccessful() && response.getBody() != null){
        ResultDTO resultDTO = new ResultDTO();
        resultDTO.setSeccion(Arrays.stream(response.getBody()).findFirst().get().getSeccionNombre());
        resultDTO.setDistrito(Arrays.stream(response.getBody()).findFirst().get().getDistritoNombre());

        Map<String , Integer> cantidadDeVotosPorAgrupacion = new HashMap<>();

        for (ResultApiClient rs : response.getBody()){

            String nombreAgrupacion = rs.getAgrupacionNombre();

            if(cantidadDeVotosPorAgrupacion.containsKey(nombreAgrupacion)){

                cantidadDeVotosPorAgrupacion.put(nombreAgrupacion,
                        cantidadDeVotosPorAgrupacion.get(nombreAgrupacion)+ rs.getVotosCantidad());
            }else{
                cantidadDeVotosPorAgrupacion.put(rs.getAgrupacionNombre() , rs.getVotosCantidad());
            }

        }

        int totalVotos = cantidadDeVotosPorAgrupacion.values()
                .stream().mapToInt(Integer::intValue).sum();

        List<ResultDetails> resultDetail = cantidadDeVotosPorAgrupacion.entrySet().stream()
                .map(entry ->{
                    ResultDetails resultDetails = new ResultDetails();
                    resultDetails.setNombre(entry.getKey());
                    resultDetails.setVotos(entry.getValue());
                    resultDetails.setPorcentaje((double) entry.getValue() / totalVotos );

                    return resultDetails;
                })
                .sorted(Comparator.comparing(ResultDetails ::getVotos).reversed())
                .collect(Collectors.toList());

        for (int i = 0 ; i < resultDetail.size() ; i++){
            resultDetail.get(i).setOrden(i+1);
        }

        resultDTO.setResultados(resultDetail);
        return resultDTO;

        }else {
            throw new EntityNotFoundException();
        }
    }

}
