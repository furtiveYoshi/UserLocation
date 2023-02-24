package eu.jitpay.test.task.userlocations.service;

import eu.jitpay.test.task.userlocations.mapper.LocationMapper;
import eu.jitpay.test.task.userlocations.model.CreateLocationRequestDto;
import eu.jitpay.test.task.userlocations.repository.LocationRepository;
import eu.jitpay.test.task.userlocations.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final UserRepository userRepository;
    private final LocationMapper locationMapper;

    public void create(CreateLocationRequestDto userLocation){
        if (userRepository.existsById(userLocation.getUserId())) {
            locationRepository.save(locationMapper.mapToEntity(userLocation));
        } else {
            throw new EntityNotFoundException();
        }
    }

}
