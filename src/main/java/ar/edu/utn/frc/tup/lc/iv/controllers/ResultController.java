package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.ResultDTO;
import ar.edu.utn.frc.tup.lc.iv.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/resultados")
public class ResultController {
    @Autowired
    ResultService resultService;

    @GetMapping()
    public ResponseEntity<ResultDTO>getResult(@RequestParam Long distrito_id ,
                                              @RequestParam Long seccion_id){
        return ResponseEntity.ok(resultService.getResult(seccion_id,distrito_id));
    }
}
