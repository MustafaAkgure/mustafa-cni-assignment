package cni.assignment.mapper;

import cni.assignment.dto.Country;
import cni.assignment.extcall.CountryDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-11T10:37:10+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.8 (Oracle Corporation)"
)
@Component
public class CountryMapperImpl implements CountryMapper {

    @Override
    public List<Country> fromDto(List<CountryDto> countryDtoList) {
        if ( countryDtoList == null ) {
            return null;
        }

        List<Country> list = new ArrayList<Country>( countryDtoList.size() );
        for ( CountryDto countryDto : countryDtoList ) {
            list.add( fromDto( countryDto ) );
        }

        return list;
    }

    @Override
    public Country fromDto(CountryDto countryDto) {
        if ( countryDto == null ) {
            return null;
        }

        String name = null;
        List<String> borders = null;

        name = countryDto.getCca3();
        List<String> list = countryDto.getBorders();
        if ( list != null ) {
            borders = new ArrayList<String>( list );
        }

        Country country = new Country( name, borders );

        return country;
    }
}
