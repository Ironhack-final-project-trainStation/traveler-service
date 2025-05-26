package traveler_service.traveler_service.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import traveler_service.traveler_service.models.Traveler;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TravelerServiceTest {

    @Autowired
    TravelerService travelerService;

    @Test
    @DisplayName("The traveler we receive is correct")
    public void getTravelerById() {
        Traveler foundTraveler = travelerService.findTravelerById(2L);

        assertNotNull(foundTraveler);
    }
}
