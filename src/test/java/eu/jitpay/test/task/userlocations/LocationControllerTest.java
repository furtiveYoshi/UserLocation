package eu.jitpay.test.task.userlocations;

import eu.jitpay.test.task.userlocations.entity.Location;
import eu.jitpay.test.task.userlocations.model.CreateLocationRequestDto;
import eu.jitpay.test.task.userlocations.model.LocationDto;
import eu.jitpay.test.task.userlocations.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.ResultMatcher;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:sql/add_users.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LocationControllerTest extends AbstractTest{

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateNewLocation() throws Exception {
        UUID userId = UUID.fromString("0cee74e5-6741-4de7-9b36-4d7c09e333c8");
        Instant createdOn = Instant.parse("2022-02-08T11:44:00.524Z");
        CreateLocationRequestDto request = CreateLocationRequestDto.builder()
                .userId(userId)
                .createdOn(createdOn)
                .location(LocationDto.builder()
                        .latitude(new BigDecimal("52.25742342295784"))
                        .longitude(new BigDecimal("10.540583401747602"))
                        .build())
                .build();
        performCreateLocationRequest(request, status().isOk());

        List<Location> locations = locationRepository.getLocationsByUserIdAndCreatedAtBetweenOrderByCreatedAt(
                userId, Instant.parse("2022-02-08T10:44:00.524Z"),
                Instant.parse("2022-02-08T12:44:00.524Z"));

        assertEquals(1, locations.size());

        checkLocation(userId, createdOn, locations.get(0));
    }

    @Test
    public void testDoubleCreateNewLocation() throws Exception {
        UUID userId = UUID.fromString("0cee74e5-6741-4de7-9b36-4d7c09e333c8");
        Instant createdOn = Instant.parse("2022-02-08T11:44:00.524Z");
        CreateLocationRequestDto request = CreateLocationRequestDto.builder()
                .userId(userId)
                .createdOn(createdOn)
                .location(LocationDto.builder()
                        .latitude(new BigDecimal("52.25742342295784"))
                        .longitude(new BigDecimal("10.540583401747602"))
                        .build())
                .build();
        performCreateLocationRequest(request, status().isOk());
        performCreateLocationRequest(request, status().isOk());

        List<Location> locations = locationRepository.getLocationsByUserIdAndCreatedAtBetweenOrderByCreatedAt(
                userId, Instant.parse("2022-02-08T10:44:00.524Z"),
                Instant.parse("2022-02-08T12:44:00.524Z"));

        assertEquals(2, locations.size());

        for (Location location : locations) {
            checkLocation(userId, createdOn, location);
        }
    }

    @Test
    public void testCreateNewLocation_LocationNotValid() throws Exception {
        UUID userId = UUID.fromString("0cee74e5-6741-4de7-9b36-4d7c09e333c8");
        Instant createdOn = Instant.parse("2022-02-08T11:44:00.524Z");
        CreateLocationRequestDto request = CreateLocationRequestDto.builder()
                .userId(userId)
                .createdOn(createdOn)
                .location(LocationDto.builder()
                        .latitude(new BigDecimal("52.25742342295784"))
                        .build())
                .build();
        performCreateLocationRequest(request, status().isBadRequest());
    }

    @Test
    public void testCreateNewLocation_UserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        CreateLocationRequestDto request = CreateLocationRequestDto.builder()
                .userId(userId)
                .createdOn(Instant.parse("2022-02-08T11:44:00.524Z"))
                .location(LocationDto.builder()
                        .latitude(new BigDecimal("52.25742342295784"))
                        .longitude(new BigDecimal("10.540583401747602"))
                        .build())
                .build();
        performCreateLocationRequest(request, status().isNotFound());
    }

    private void performCreateLocationRequest(CreateLocationRequestDto request, ResultMatcher resultStatus) throws Exception {
        mvc.perform(post("/public/api/v1/locations")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(resultStatus);
    }

    private void checkLocation(UUID userId, Instant createdOn, Location location) {
        assertEquals(userId, location.getUserId());
        assertEquals(createdOn, location.getCreatedAt());
        assertEquals(0, new BigDecimal("52.25742342295784").compareTo(location.getLatitude()),
                String.format("Actual: %s, Expected: %s", location.getLatitude(), new BigDecimal("52.25742342295784")));
        assertEquals(0, new BigDecimal("10.540583401747602").compareTo(location.getLongitude()),
                String.format("Actual: %s, Expected: %s", location.getLatitude(), new BigDecimal("10.540583401747602")));
    }

}
