package traveler_service.traveler_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.repositories.TravelerRepository;

@SpringBootTest
public class TravelerTest {

    @Autowired
    TravelerRepository travelerRepository;

    @Test
    public void addTraveler () {
        Traveler testTraveler = new Traveler();
        testTraveler.setName("Maria Escobar");
        testTraveler.setAge(32);
        testTraveler.setEmail("escobmaria@gmail.com");
        testTraveler.setTrainId("A5002");
        travelerRepository.save(testTraveler);
        System.out.println("New traveler info : "+ testTraveler);
        //travelerRepository.delete(testTraveler);

    }
}
