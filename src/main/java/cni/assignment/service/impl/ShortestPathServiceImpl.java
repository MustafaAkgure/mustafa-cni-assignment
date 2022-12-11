package cni.assignment.service.impl;

import cni.assignment.dto.Country;
import cni.assignment.dto.Route;
import cni.assignment.service.ShortestPathService;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ShortestPathServiceImpl implements ShortestPathService {

    @Autowired
    private DataLoaderImpl dataLoader;

    private Map<String, Country> countryMap;
    private DijkstraShortestPath<Country, DefaultEdge> dijkstraShortestPath;

    @Override
    public Route findRoute(final String origin, final String destination) {

        final Country originCountry = Optional.ofNullable(countryMap.get(origin.toUpperCase()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Origin country " + origin + " not found"));
        final Country destinationCountry = Optional.ofNullable(countryMap.get(destination.toUpperCase()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination country " + destination + " not found"));

        final GraphPath<Country, DefaultEdge> path = dijkstraShortestPath.getPath(originCountry, destinationCountry);
        if (path == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No routes found");
        }

        final List<String> routeCodes = path.getVertexList().stream()
                .map(Country::getName)
                .collect(Collectors.toList());
        return new Route(routeCodes);
    }

    @PostConstruct
    protected void init() {
        List<Country> countryList = dataLoader.loadCountries();
        countryMap = countryList.stream()
                .collect(Collectors.toMap(Country::getName, c -> c));

        final Graph<Country, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);

        try {
            countryMap.values().forEach(graph::addVertex);
            countryMap.values().stream()
                    .map(this::getEdges)
                    .flatMap(Collection::stream)
                    .forEach(edge -> graph.addEdge(edge.getFirst(), edge.getSecond()));
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "unable to construct a graph from countries", e);
        }

        dijkstraShortestPath = new DijkstraShortestPath<>(graph);
    }


    private List<Pair<Country, Country>> getEdges(final Country country) {
        return country.getBorders().stream()
                .map(countryMap::get)
                .map(neighbour -> new Pair<>(country, neighbour))
                .collect(Collectors.toList());
    }
}
