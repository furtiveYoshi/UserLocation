package eu.jitpay.test.task.userlocations.mapper;

import eu.jitpay.test.task.userlocations.entity.Location;
import eu.jitpay.test.task.userlocations.model.CreateLocationRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    @Mapping(source = "location.latitude", target = "latitude")
    @Mapping(source = "location.longitude", target = "longitude")
    @Mapping(source = "createdOn", target = "createdAt")
    Location mapToEntity(CreateLocationRequestDto dto);
}
