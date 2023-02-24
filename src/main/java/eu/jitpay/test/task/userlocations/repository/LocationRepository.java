package eu.jitpay.test.task.userlocations.repository;

import eu.jitpay.test.task.userlocations.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LocationRepository extends CrudRepository<Location, UUID> {

    Optional<Location> getTopByUserIdOrderByCreatedAtDesc(UUID userId);

    List<Location> getLocationsByUserIdAndCreatedAtBetweenOrderByCreatedAt(UUID userId, Instant from, Instant to);
}
