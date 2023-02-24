package eu.jitpay.test.task.userlocations;

import eu.jitpay.test.task.userlocations.entity.User;
import eu.jitpay.test.task.userlocations.model.ExtendedLocationDto;
import eu.jitpay.test.task.userlocations.model.UserDto;
import eu.jitpay.test.task.userlocations.model.UserLastLocationDto;
import eu.jitpay.test.task.userlocations.model.UserLocationsDto;
import eu.jitpay.test.task.userlocations.repository.LocationRepository;
import eu.jitpay.test.task.userlocations.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Sql(scripts = "classpath:sql/add_users_and_locations.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:sql/clear.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest extends AbstractTest {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void testSaveUser() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDto request = UserDto.builder()
                .userId(userId)
                .email("someemail@email.com")
                .firstName("someName")
                .secondName("someSurname")
                .build();
        mvc.perform(put("/public/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<User> optionalUser = userRepository.findById(userId);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        assertEquals(userId, user.getId());
        assertEquals("someemail@email.com", user.getEmail());
        assertEquals("someName", user.getFirstName());
        assertEquals("someSurname", user.getSecondName());
    }

    @Test
    public void testSaveUser_UserNotValid() throws Exception {
        UserDto request = UserDto.builder()
                .email("someemail@gmail.com")
                .firstName("someName")
                .secondName("someSurname")
                .build();
        mvc.perform(put("/public/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateUser() throws Exception {
        UUID userId = UUID.fromString("0cee74e5-6741-4de7-9b36-4d7c09e333c8");
        UserDto request = UserDto.builder()
                .userId(userId)
                .email("someemail@gmail.com")
                .firstName("someName")
                .secondName("someSurname")
                .build();
        mvc.perform(put("/public/api/v1/users")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Optional<User> optionalUser = userRepository.findById(userId);
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();

        assertEquals(userId, user.getId());
        assertEquals("someemail@gmail.com", user.getEmail());
        assertEquals("someName", user.getFirstName());
        assertEquals("someSurname", user.getSecondName());
    }

    @Test
    public void testGetUserWithLastLocation() throws Exception {
        UUID userId = UUID.fromString("0cee74e5-6741-4de7-9b36-4d7c09e333c8");
        String response = mvc.perform(get("/public/api/v1/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserLastLocationDto userLocation = objectMapper.readValue(response, UserLastLocationDto.class);

        assertNotNull(userLocation);

        assertEquals(userId,
                userLocation.getUserId());
        assertEquals("robert_smith@email.com",
                userLocation.getEmail());
        assertEquals("Robert",
                userLocation.getFirstName());
        assertEquals("Smith",
                userLocation.getSecondName());

        assertEquals(0, new BigDecimal("52.26142342295784").compareTo(userLocation.getLocation().getLatitude()),
                String.format("Actual: %s, Expected: %s", userLocation.getLocation().getLatitude(), new BigDecimal("52.25742342295784")));
        assertEquals(0, new BigDecimal("10.940583401747602").compareTo(userLocation.getLocation().getLongitude()),
                String.format("Actual: %s, Expected: %s", userLocation.getLocation().getLongitude(), new BigDecimal("10.940583401747602")));
    }

    @Test
    public void testGetUserWithLastLocation_UserWithoutLocations() throws Exception {
        UUID userId = UUID.fromString("25530c99-fea6-4cb5-afb5-6586caa304b5");
        String response = mvc.perform(get("/public/api/v1/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserLastLocationDto userLocation = objectMapper.readValue(response, UserLastLocationDto.class);

        assertNotNull(userLocation);

        assertEquals(userId,
                userLocation.getUserId());
        assertEquals("someemail@email.com",
                userLocation.getEmail());
        assertEquals("Somename",
                userLocation.getFirstName());
        assertEquals("Somesurname",
                userLocation.getSecondName());
    }

    @Test
    public void testGetUserWithLocationsInTimeRange() throws Exception {
        UUID userId = UUID.fromString("6fa86495-e5af-4e49-a962-598a4b50e81c");
        String response = mvc.perform(get("/public/api/v1/users/" + userId + "/locations")
                        .param("from", "2022-02-08T12:44:00.524Z")
                        .param("to", "2022-02-08T13:44:00.524Z")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserLocationsDto userLocations = objectMapper.readValue(response, UserLocationsDto.class);

        assertNotNull(userLocations);

        assertEquals(userId,
                userLocations.getUserId());

        assertNotNull(userLocations.getLocations());
        assertEquals(2, userLocations.getLocations().size());

        ExtendedLocationDto location1 = userLocations.getLocations().get(0);
        assertEquals(Instant.parse("2022-02-08T12:44:00.524Z"), location1.getCreatedOn());
        assertEquals(0, new BigDecimal("62.25792342295784").compareTo(location1.getLocation().getLatitude()),
                String.format("Actual: %s, Expected: %s", location1.getLocation().getLatitude(), new BigDecimal("62.25792342295784")));
        assertEquals(0, new BigDecimal("25.640584401747602").compareTo(location1.getLocation().getLongitude()),
                String.format("Actual: %s, Expected: %s", location1.getLocation().getLongitude(), new BigDecimal("25.640584401747602")));

        ExtendedLocationDto location2 = userLocations.getLocations().get(1);
        assertEquals(Instant.parse("2022-02-08T13:44:00.524Z"), location2.getCreatedOn());
        assertEquals(0, new BigDecimal("62.25842342295784").compareTo(location2.getLocation().getLatitude()),
                String.format("Actual: %s, Expected: %s", location2.getLocation().getLatitude(), new BigDecimal("62.25842342295784")));
        assertEquals(0, new BigDecimal("25.740583401747602").compareTo(location2.getLocation().getLongitude()),
                String.format("Actual: %s, Expected: %s", location2.getLocation().getLongitude(), new BigDecimal("25.740583401747602")));
    }

    @Test
    public void testGetUserWithLocationsInTimeRange_UserWithoutLocations() throws Exception {
        UUID userId = UUID.fromString("25530c99-fea6-4cb5-afb5-6586caa304b5");
        String response = mvc.perform(get("/public/api/v1/users/" + userId + "/locations")
                        .param("from", "2022-02-08T12:44:00.524Z")
                        .param("to", "2022-02-08T13:44:00.524Z")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        UserLocationsDto userLocations = objectMapper.readValue(response, UserLocationsDto.class);

        assertNotNull(userLocations);

        assertEquals(userId,
                userLocations.getUserId());
    }

    @Test
    public void testGetUserWithLastLocation_UserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        mvc.perform(get("/public/api/v1/users/" + userId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetUserWithLocationsInTimeRange_UserNotFound() throws Exception {
        UUID userId = UUID.randomUUID();
        mvc.perform(get("/public/api/v1/users/" + userId + "/locations")
                        .param("from", "2022-02-08T12:44:00.524Z")
                        .param("to", "2022-02-08T14:44:00.524Z")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
