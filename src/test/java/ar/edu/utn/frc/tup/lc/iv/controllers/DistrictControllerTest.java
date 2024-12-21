package ar.edu.utn.frc.tup.lc.iv.controllers;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.DistrictoDTO;
import ar.edu.utn.frc.tup.lc.iv.service.impl.DistrictServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DistrictController.class)
class DistrictControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DistrictServiceImpl districtService;

    @Test
    void getDistricts() throws Exception {
        DistrictoDTO districtoDTO = new DistrictoDTO(1L , "Carlos paz");
        List<DistrictoDTO> list = new ArrayList<>();

        list.add(districtoDTO);

        when(districtService.getDistricts(null)).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders.get("/distritos")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].nombre").value("Carlos paz"));

    }
}