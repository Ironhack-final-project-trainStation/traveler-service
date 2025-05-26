package traveler_service.traveler_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.repositories.TravelerRepository;

import java.util.Optional;

@Service
public class TravelerService {

    @Autowired
    TravelerRepository travelerRepository;

    public Traveler findTravelerById(Long id) throws TravelerNotFoundException {
        Optional<Traveler> foundTraveler = travelerRepository.findById(id);

        if (foundTraveler.isPresent()) {
            return foundTraveler.get();
        } else {
            throw new TravelerNotFoundException("Traveler not found");
        }
    }
}
