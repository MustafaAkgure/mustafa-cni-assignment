package cni.assignment.service;

import cni.assignment.dto.Country;

import java.util.List;

public interface DataLoader {
    List<Country> loadCountries();
}
