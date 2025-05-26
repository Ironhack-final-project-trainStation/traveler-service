package traveler_service.traveler_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import traveler_service.traveler_service.dtos.TrainDTO;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.feignclients.TrainFeignClient;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.services.TravelerService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/traveler")
public class TravelerController {

    @Autowired
    TravelerService travelerService;

    @Autowired
    TrainFeignClient trainFeignClient;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTravelerById(@PathVariable Long id) {

        try {
            Traveler foundTraveler = travelerService.findTravelerById(id);

            TrainDTO foundTrain = trainFeignClient.getTrainById(foundTraveler.getTrainId());
            System.out.println(foundTraveler);
            System.out.println(foundTrain);

            Map<String, Object> response = new HashMap<>();
            response.put("Traveler", foundTraveler);
            response.put("Train", foundTrain);

            return  new ResponseEntity<>(response, HttpStatus.OK);
        } catch (TravelerNotFoundException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
