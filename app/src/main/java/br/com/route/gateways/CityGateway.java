/*
 * Copyright (C) 2020 adidas AG.
 */

package br.com.route.gateways;

import br.com.route.domain.City;
import br.com.route.domain.Path;
import reactor.core.publisher.Mono;

/**
 * @author Henrique E. do Amaral
 * @see CityGateway
 * @since 1.0.0.RELEASE
 */
public interface CityGateway {

  Mono<City> findByName(String name);

  Mono<Path> getShortestPath(String from, String to);

  Mono<Path> getShortestPathInTime(String from, String to);

  Mono<City> save(City city);
}
