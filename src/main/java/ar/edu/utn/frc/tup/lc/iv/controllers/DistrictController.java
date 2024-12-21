package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import ar.edu.utn.frc.tup.lc.iv.service.DistrictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/distritos")
public class DistrictController {

    @Autowired
    private DistrictService districtService;
    @GetMapping("")
    public ResponseEntity<List<DistrictoDTO>> getDistricts(@RequestParam (required = false) String distrito_nombre) {
        return ResponseEntity.ok(districtService.getDistricts(distrito_nombre));
    }
}
