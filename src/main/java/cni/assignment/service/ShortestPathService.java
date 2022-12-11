package cni.assignment.service;

import cni.assignment.dto.Route;

public interface ShortestPathService {
    Route findRoute(final String origin, final String destination);
}
