package ar.edu.utn.frc.tup.lc.iv.service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import org.springframework.stereotype.Service;

@Service
public interface CargoService {

    ChargeDTO getCargoBYDistrictId(Long distrito_id);
}
