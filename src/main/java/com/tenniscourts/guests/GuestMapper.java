package com.tenniscourts.guests;
import com.tenniscourts.schedules.Schedule;
import com.tenniscourts.schedules.ScheduleDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

        Guest map(GuestDTO source);

        @InheritInverseConfiguration
        GuestDTO map(Guest source);

        @Mapping(target = "name", source = "name")
        Guest map(CreateGuestRequestDTO source);

        List<GuestDTO> map(List<Guest> source);
    }

