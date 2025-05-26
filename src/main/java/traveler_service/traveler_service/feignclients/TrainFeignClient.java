package traveler_service.traveler_service.feignclients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import traveler_service.traveler_service.dtos.TrainDTO;

@FeignClient(name = "train-service")
public interface TrainFeignClient {

    @GetMapping("/api/train/{id}")
    TrainDTO getTrainById(@PathVariable String id);
}
