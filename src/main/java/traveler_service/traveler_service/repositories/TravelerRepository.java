package traveler_service.traveler_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import traveler_service.traveler_service.models.Traveler;

import java.util.List;
import java.util.Optional;

@Repository
public interface TravelerRepository extends JpaRepository<Traveler, Long> {
    List<Traveler> findByTrainId(String trainId);
}
