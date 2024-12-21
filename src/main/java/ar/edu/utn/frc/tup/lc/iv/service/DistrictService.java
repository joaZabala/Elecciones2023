package ar.edu.utn.frc.tup.lc.iv.service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DistrictService {

    List<DistrictoDTO> getDistricts(String distritoName);
    List<DistrictoDTO> getDistrictById(Long id);
}
