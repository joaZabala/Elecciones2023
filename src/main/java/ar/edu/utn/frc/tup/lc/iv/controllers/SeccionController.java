package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.SeccionDTO;
import ar.edu.utn.frc.tup.lc.iv.service.SeccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/secciones")
public class SeccionController {

    @Autowired
    SeccionService seccionService;
    @GetMapping("")
    public ResponseEntity<List<SeccionDTO>>getSecciones(@RequestParam (required = true)long distrito_id,
                                                       @RequestParam (required = false)Long seccion_id) {

        return ResponseEntity.ok(seccionService.getSecciones(distrito_id , seccion_id));

    }
}
