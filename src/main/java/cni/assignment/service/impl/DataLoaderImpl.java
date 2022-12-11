package cni.assignment.service.impl;

import cni.assignment.dto.Country;
import cni.assignment.extcall.CountryClient;
import cni.assignment.mapper.CountryMapper;
import lombok.AllArgsConstructor;
import cni.assignment.service.DataLoader;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("unused")
@Component
@AllArgsConstructor
public class DataLoaderImpl implements DataLoader {

    private CountryClient countryClient;
    private CountryMapper countryMapper;

    @Override
    public List<Country> loadCountries() {
        var countryDtoList = countryClient.countries();
        return countryMapper.fromDto(countryDtoList);
    }
}
