package eu.jitpay.test.task.userlocations.controller;

import eu.jitpay.test.task.userlocations.model.CreateLocationRequestDto;
import eu.jitpay.test.task.userlocations.service.LocationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/public/api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createUserLocation(@Valid @RequestBody CreateLocationRequestDto userLocation){
        locationService.create(userLocation);
    }
}
