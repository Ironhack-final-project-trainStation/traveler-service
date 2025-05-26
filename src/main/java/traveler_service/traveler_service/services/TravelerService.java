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

    public Traveler saveTraveler(Traveler traveler) {
        return travelerRepository.save(traveler);
    }

    public Traveler updateTraveler(Long id, Traveler updated) {
        Traveler traveler = findTravelerById(id);
        traveler.setName(updated.getName());
        traveler.setAge(updated.getAge());
        traveler.setEmail(updated.getEmail());
        traveler.setTrainId(updated.getTrainId());
        return travelerRepository.save(traveler);
    }

    public void deleteTraveler (Long id) {
        Traveler traveler = findTravelerById(id);
        travelerRepository.delete(traveler);
    }
}
