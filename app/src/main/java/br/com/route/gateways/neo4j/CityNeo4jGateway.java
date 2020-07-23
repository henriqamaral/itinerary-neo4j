/*
 * Copyright (C) 2020 adidas AG.
 */

package br.com.route.gateways.neo4j;

import br.com.route.domain.City;
import br.com.route.domain.Path;
import br.com.route.gateways.CityGateway;
import br.com.route.gateways.neo4j.repositories.CityRepository;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.internal.value.PathValue;
import org.neo4j.driver.types.Relationship;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Henrique E. do Amaral
 * @see CityNeo4jGateway
 * @since 1.0.0.RELEASE
 */
@Component
@RequiredArgsConstructor
public class CityNeo4jGateway implements CityGateway {

  private final CityRepository cityRepository;


  @Override
  public Mono<City> findByName(String name) {
    return cityRepository.findByName(name);
  }

  @Override
  public Mono<Path> getShortestPath(String from, String to) {

    final Flux<PathValue> rows = cityRepository.shortestPath(from, to);
    return rows
        .map(it -> this.convert(it.asPath()))
        .take(1)
        .next()
        .switchIfEmpty(Mono.empty());

  }

  @Override
  public Mono<Path> getShortestPathInTime(String from, String to) {

    final Flux<PathValue> rows = cityRepository.shortestPathInTime(from, to);
    return rows
        .map(it -> this.convertTimePath(it.asPath()))
        .take(1)
        .next()
        .switchIfEmpty(Mono.empty());

  }

  @Override
  public Mono<City> save(City city) {
    return cityRepository.save(city);
  }

  private Path convert(org.neo4j.driver.types.Path connection) {

    String departureCity = connection.start().get("name").asString();
    String arriveCity = connection.end().get("name").asString();
    int length = connection.length();

    return Path.createConnectionPath(departureCity, arriveCity, length);
  }

  private Path convertTimePath(org.neo4j.driver.types.Path connection) {

    String departureCity = connection.start().get("name").asString();
    String arriveCity = connection.end().get("name").asString();
    Stream<Relationship> targetStream = StreamSupport.stream(connection.relationships().spliterator(), false);
    int totalInTime = targetStream.mapToInt(it -> it.get("duration").asInt()).sum();

    return Path.createTimePath(departureCity, arriveCity, totalInTime);
  }
}
