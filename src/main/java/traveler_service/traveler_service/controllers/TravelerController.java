package traveler_service.traveler_service.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import traveler_service.traveler_service.dtos.TrainDTO;
import traveler_service.traveler_service.dtos.TravelersNameDTO;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.feignclients.TrainFeignClient;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.services.TravelerService;
import org.springframework.validation.BindingResult;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/api/traveler")
public class TravelerController {

    @Autowired
    TravelerService travelerService;

    @Autowired
    TrainFeignClient trainFeignClient;

    @GetMapping("/id/{id}")
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

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TravelersNameDTO>> getTravelersByTrain(@PathVariable("trainId") String trainId){
        List <TravelersNameDTO> travelers = travelerService.findByTrainId(trainId);

        if(travelers.isEmpty()) {
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(travelers, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Traveler> createTraveler (@RequestBody @Valid Traveler traveler) {
        Traveler newTraveler = travelerService.saveTraveler(traveler);
        return new ResponseEntity<>(newTraveler, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTraveler(@PathVariable Long id, @RequestBody @Valid Traveler Traveler) {
        try {
            Traveler updatedTraveler = travelerService.updateTraveler(id, Traveler);
            return new ResponseEntity<>(updatedTraveler, HttpStatus.OK);
        } catch (TravelerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTraveler (@PathVariable Long id) {
        try {
            travelerService.deleteTraveler(id);
            return new ResponseEntity<>("Traveler deleted succesfully", HttpStatus.OK);
        } catch (TravelerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
