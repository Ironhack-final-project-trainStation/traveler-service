package traveler_service.traveler_service.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import traveler_service.traveler_service.services.TravelerService;

@RestController
@RequestMapping("/api/traveler")
public class TravelerController {

    @Autowired
    TravelerService travelerService;

    @Autowired

}
