package traveler_service.traveler_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import traveler_service.traveler_service.exceptions.TravelerNotFoundException;
import traveler_service.traveler_service.models.Traveler;
import traveler_service.traveler_service.repositories.TravelerRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
@ExtendWith(MockitoExtension.class)
public class ServiceTestUnitarios {

    @Mock
    private TravelerRepository travelerRepository;

    @InjectMocks
    private TravelerService travelerService;

    @Test
    void testFindTravelerById_ReturnsTraveler() {
        Traveler traveler = new Traveler(6L, "Ana", "ana@gmail.com", 58 , "train1");
        Mockito.when(travelerRepository.findById(6L)).thenReturn(Optional.of(traveler));

        Traveler result = travelerService.findTravelerById(6L);

        assertNotNull(result);
        assertEquals(6L, result.getId());
        assertEquals("Ana", result.getName());
    }

    @Test
    void testFindTravelerById_NotFound() {
        Mockito.when(travelerRepository.findById(6L)).thenReturn(Optional.empty());

        assertThrows(TravelerNotFoundException.class, () -> {
            travelerService.findTravelerById(6L);
        });
    }

    @Test
    void testSaveTraveler() {
        Traveler newTraveler = new Traveler(null, "Ana", "ana@gmail.com", 58 , "train1");
        Traveler savedTraveler = new Traveler(6L, "Ana", "ana@gmail.com", 58 , "train1");

        Mockito.when(travelerRepository.save(newTraveler)).thenReturn(savedTraveler);

        Traveler result = travelerService.saveTraveler(newTraveler);

        assertNotNull(result);
        assertEquals(58, result.getAge());
        assertEquals("Ana", result.getName());
    }

    @Test
    void testDeleteTraveler() {
        Long idToDelete = 6L;
        Traveler testTraveler = new Traveler(idToDelete, "Ana", "ana@gmail.com", 58 , "train1");

        Mockito.when(travelerRepository.findById(idToDelete)).thenReturn(Optional.of(testTraveler));
        Mockito.doNothing().when(travelerRepository).delete(testTraveler);

        assertDoesNotThrow(() -> travelerService.deleteTraveler(idToDelete));
    }

    @Test
    void testDeleteTraveler_NotFound() {
        Mockito.when(travelerRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(TravelerNotFoundException.class, () -> travelerService.deleteTraveler(99L));
    }
}
