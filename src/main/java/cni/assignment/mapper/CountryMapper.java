package cni.assignment.mapper;

import cni.assignment.dto.Country;
import cni.assignment.extcall.CountryDto;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CountryMapper {

    @IterableMapping(elementTargetType = Country.class)
    List<Country> fromDto(List<CountryDto> countryDtoList);

    @Mapping(target = "name", source = "cca3")
    Country fromDto(CountryDto countryDto);
}
