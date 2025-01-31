package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.configs.RestTemplateConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import ar.edu.utn.frc.tup.lc.iv.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping("")
    public ResponseEntity<ChargeDTO>getCargos(@RequestParam long distrito_id) {

        return ResponseEntity.ok(cargoService.getCargoBYDistrictId(distrito_id));
    }
}
