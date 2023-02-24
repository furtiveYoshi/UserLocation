package eu.jitpay.test.task.userlocations.controller;

import eu.jitpay.test.task.userlocations.model.UserDto;
import eu.jitpay.test.task.userlocations.model.UserLastLocationDto;
import eu.jitpay.test.task.userlocations.model.UserLocationsDto;
import eu.jitpay.test.task.userlocations.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.UUID;

@RestController
@RequestMapping(path = "/public/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void saveOrUpdateUser(@Valid @RequestBody UserDto user) {
        userService.saveOrUpdate(user);
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLastLocationDto getUserWithLastLocation(@PathVariable UUID userId) {
        return userService.getUserWithLastLocation(userId);
    }

    @GetMapping(value = "/{userId}/locations", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserLocationsDto getUserWithLocationRange(@PathVariable UUID userId,
                                                     @RequestParam Instant from,
                                                     @RequestParam Instant to) {
        return userService.getUserLocationsInTimeRange(userId, from, to);
    }
}
