package traveler_service.traveler_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.repositories.TravelerRepository;

@Service
public class TravelerService {

    @Autowired
    TravelerRepository travelerRepository;

    public Traveler findTravelerById(Long id) throws TravelerNotFoundException {

    }
}
