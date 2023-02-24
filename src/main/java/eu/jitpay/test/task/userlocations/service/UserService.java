package eu.jitpay.test.task.userlocations.service;

import eu.jitpay.test.task.userlocations.entity.Location;
import eu.jitpay.test.task.userlocations.entity.User;
import eu.jitpay.test.task.userlocations.mapper.UserMapper;
import eu.jitpay.test.task.userlocations.model.UserDto;
import eu.jitpay.test.task.userlocations.model.UserLastLocationDto;
import eu.jitpay.test.task.userlocations.model.UserLocationsDto;
import eu.jitpay.test.task.userlocations.repository.LocationRepository;
import eu.jitpay.test.task.userlocations.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final UserMapper userMapper;

    public void saveOrUpdate(UserDto user) {
        User userEntity = userRepository.findById(user.getUserId())
                .map(existedUser -> existedUser
                        .setEmail(user.getEmail())
                        .setFirstName(user.getFirstName())
                        .setSecondName(user.getSecondName()))
                .orElseGet(() -> userMapper.mapToEntity(user));
        userRepository.save(userEntity);
    }

    public UserLastLocationDto getUserWithLastLocation(UUID userId) {
        Optional<Location> optionalLocation = locationRepository.getTopByUserIdOrderByCreatedAtDesc(userId);
        User user = optionalLocation
                .map(Location::getUser)
                .or(() -> userRepository.findById(userId))
                .orElseThrow(EntityNotFoundException::new);
        return userMapper.mapToDto(user, optionalLocation.orElse(null));
    }

    public UserLocationsDto getUserLocationsInTimeRange(UUID userId, Instant from, Instant to) {
        List<Location> locations = locationRepository
                .getLocationsByUserIdAndCreatedAtBetweenOrderByCreatedAt(userId, from, to);
        User user = locations.stream()
                .findFirst()
                .map(Location::getUser)
                .or(() -> userRepository.findById(userId))
                .orElseThrow(EntityNotFoundException::new);
        return userMapper.mapToDto(user, locations);
    }
}
