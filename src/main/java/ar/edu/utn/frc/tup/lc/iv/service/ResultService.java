package ar.edu.utn.frc.tup.lc.iv.service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResultDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ResultService {
    ResultDTO getResult(Long seccionId , Long distritoId);
}
