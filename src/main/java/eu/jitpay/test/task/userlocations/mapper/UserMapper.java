package eu.jitpay.test.task.userlocations.mapper;

import eu.jitpay.test.task.userlocations.entity.Location;
import eu.jitpay.test.task.userlocations.entity.User;
import eu.jitpay.test.task.userlocations.model.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "userId", target = "id")
    User mapToEntity(UserDto dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "location.latitude", target = "location.latitude")
    @Mapping(source = "location.longitude", target = "location.longitude")
    UserLastLocationDto mapToDto(User user, Location location);

    @Mapping(source = "user.id", target = "userId")
    UserLocationsDto mapToDto(User user, List<Location> locations);

    @Mapping(source = "createdAt", target = "createdOn")
    @Mapping(source = "latitude", target = "location.latitude")
    @Mapping(source = "longitude", target = "location.longitude")
    ExtendedLocationDto mapToDto(Location location);
}
