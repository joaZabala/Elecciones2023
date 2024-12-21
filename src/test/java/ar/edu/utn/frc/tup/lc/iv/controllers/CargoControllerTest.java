package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.Cargo;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.ChargeDTO;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import ar.edu.utn.frc.tup.lc.iv.service.CargoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CargoController.class)
class CargoControllerTest {

    @MockBean
    private CargoService cargoService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    void getCargos() throws Exception {
        Cargo cargo = new Cargo(2L , "Presidente");

        ChargeDTO chargeDTO = new ChargeDTO();
        chargeDTO.setDistrito(new DistrictoDTO(1L , "Cordoba"));
        chargeDTO.setCargos(List.of(cargo));

        when(cargoService.getCargoBYDistrictId(1L)).thenReturn(chargeDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/cargos")
                        .param("distrito_id","1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.distrito.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.distrito.nombre").value("Cordoba"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargos").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargos[0].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.cargos[0].nombre").value("Presidente"));

    }
}