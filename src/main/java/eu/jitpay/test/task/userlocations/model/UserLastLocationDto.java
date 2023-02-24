package eu.jitpay.test.task.userlocations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = UserLastLocationDto.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserLastLocationDto {
    UUID userId;
    String email;
    String firstName;
    String secondName;
    LocationDto location;
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

    }
}
