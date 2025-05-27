package traveler_service.traveler_service.controllers;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import traveler_service.traveler_service.dtos.TrainDTO;
import traveler_service.traveler_service.dtos.TravelersNameDTO;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.feignclients.TrainFeignClient;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.services.TravelerService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@WebMvcTest(TravelerController.class)
public class TravelerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrainFeignClient trainFeignClient;

    @MockBean
    private TravelerService travelerService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetTravelerById_ReturnsAlsoTrain() throws Exception {
        Traveler mockTraveler = new Traveler(6L, "Ana", "ana@gmail.com", 58 , "train1");
        TrainDTO mockTrain = new TrainDTO("train1", "Cordoba");

        Mockito.when(travelerService.findTravelerById(6L)).thenReturn(mockTraveler);
        Mockito.when(trainFeignClient.getTrainById("train1")).thenReturn(mockTrain);

        mockMvc.perform(get("/api/traveler/6"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Train.destination").value("Cordoba"))
                .andExpect(jsonPath("$.Traveler.name").value("Ana"));


    }

    @Test
    void testGetTravelerById_NotFound() throws Exception {
        Mockito.when(travelerService.findTravelerById(6L))
                .thenThrow(new TravelerNotFoundException("Traveler not found"));

        mockMvc.perform(get("/api/traveler/id/6"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Traveler not found"));
    }

    @Test
    void testGetTravelersByTrainId() throws Exception {
        List<TravelersNameDTO> mockTravelers = List.of(
                new TravelersNameDTO("Ana"),
                new TravelersNameDTO("Lucas")
        );
        Mockito.when(travelerService.findByTrainId("train1")).thenReturn(mockTravelers);

        mockMvc.perform(get("/api/traveler/train/train1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Ana"))
                .andExpect(jsonPath("$[1].name").value("Lucas"));
    }

    @Test
    void testGetTravelersByTrainId_NotFound() throws Exception {
        Mockito.when(travelerService.findByTrainId("train1")).thenReturn(List.of());

        mockMvc.perform(get("/api/traveler/train/train1"))
                .andExpect(status().isNotFound());

    }
    @Test
    void testCreateTraveler() throws Exception {
        Traveler traveler = new Traveler(6L, "Ana", "ana@gmail.com", 58 , "train1");

        Mockito.when(travelerService.saveTraveler(Mockito.any())).thenReturn(traveler);

        mockMvc.perform(post("/api/traveler")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(traveler)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Ana"))
                .andExpect(jsonPath("$.trainId").value("train1"));

    }

    @Test
    void testUpdateTraveler() throws Exception {
        Traveler updated = new Traveler(6L, "Ana", "ana@gmail.com", 58 , "train1");

        Mockito.when(travelerService.updateTraveler(Mockito.eq(6L), Mockito.any())).thenReturn(updated);

        mockMvc.perform(put("/api/traveler/6")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana"));

    }

    @Test
    void testDeleteTraveler() throws Exception {
        Mockito.doNothing().when(travelerService).deleteTraveler(6L);
        mockMvc.perform(delete("/api/traveler/6"))
                .andExpect(status().isOk())
                .andExpect(content().string("Traveler deleted succesfully"));
    }
}
