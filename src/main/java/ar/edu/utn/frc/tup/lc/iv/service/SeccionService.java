package ar.edu.utn.frc.tup.lc.iv.service;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeccionService {

    List<SeccionDTO> getSecciones(Long distrito_id , Long seccion_id);
}
