package eu.jitpay.test.task.userlocations.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;
import java.util.UUID;

@Value
@Builder(builderClassName = "Builder", toBuilder = true)
@JsonDeserialize(builder = CreateLocationRequestDto.Builder.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateLocationRequestDto {
    @NotNull
    UUID userId;
    @NotNull
    Instant createdOn;
    @Valid
    @NotNull
    LocationDto location;

    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {

    }
}
